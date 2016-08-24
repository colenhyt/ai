package box.site.classify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import box.mgr.PageManager;
import box.site.model.TopItem;
import box.site.model.WebUrl;
import box.site.parser.sites.BaseTopItemParser;
import box.site.processor.SiteTermProcessor;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.DecisionTreeTrainer;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.PrintInputAndTarget;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import cc.mallet.types.Labeling;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import es.util.FileUtil;
import es.util.tfidf.FeatureSelect;
import es.util.tfidf.WordCount;
import es.util.tfidf.WordDocMatrix;

public class NewsClassifier {
	protected Logger  log = Logger.getLogger(getClass()); 
	public final static int CAT_MARKET = 2;
	public final static int CAT_PRODUCT = 3;
	public final static int CAT_COMPANY = 4;
	public final static int CAT_PEOPLE = 5;
	public final static int CAT_STARTUP = 6;	
	private String trainingPath ="data/training/";
	private Set<String>  peopleSet = new HashSet<String>();
	private Set<String>  comSet = new HashSet<String>();
	public Map<String,List<String>> catKeywords = new HashMap<String,List<String>>();
	public Map<String,Integer> catIds = new HashMap<String,Integer>();
	public Map<Integer,String> catStrs = new HashMap<Integer,String>();
	private Map<Integer,Classifier>	classifyMap = new HashMap<Integer,Classifier>();
	JiebaSegmenter segmenter = new JiebaSegmenter();
	private Classifier classifier;
	
	public NewsClassifier(){
		trainingPath = PageManager.getInstance().traniningpath;
		String rootPath = PageManager.getInstance().rootPath;
		String content = FileUtil.readFile(trainingPath+"people.json");
		if (content.trim().length()>0){
			List<String> peopleList = (List<String>)JSON.parse(content);
			peopleSet.addAll(peopleList);
		}
		content = FileUtil.readFile(trainingPath+"company.json");
		if (content.trim().length()>0){
			List<String> companyList = (List<String>)JSON.parse(content);
			comSet.addAll(companyList);
		}
		
		String keyContent = FileUtil.readFile(rootPath+"catkeys.txt");
		String[] keyStrs = keyContent.split("\n");
		int catid = 1;
		for (String keyStr:keyStrs){
			String[] strs = keyStr.split(":");
			List<String> words = new ArrayList<String>();
			catIds.put(strs[0], catid);
			catStrs.put(catid, strs[0]);
			catid++;
			if (strs.length<2) continue;
			String[] aa = strs[1].split(",");
			for (String a:aa){
				words.add(a);
			}
			if (words.size()>0)
				catKeywords.put(strs[0], words);
		}
		
		File classifyFile = new File(trainingPath+"news_dt.classifier");
//		try {
//			FileInputStream fis = new FileInputStream(classifyFile);
//           ObjectInputStream ois = new ObjectInputStream(fis);  
//           classifier = (Classifier) ois.readObject(); 
//           ois.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
	}
	
	public void moveTrainingPages(){
		String path = "data/pages/";
		String writePath = "data/pages2/";
		BaseTopItemParser parser = new BaseTopItemParser("data/dna/");
		List<File> files = FileUtil.getFiles(path);
		for (File f:files){
			if (f.getName().indexOf(".json")<0)continue;
			String sitekey = f.getName().substring(0,f.getName().indexOf("_urls.json"));
			if (!sitekey.equals("sina.com.cn")) continue;
			String urlsContent = FileUtil.readFile(f);
			if (urlsContent!=null&&urlsContent.trim().length()>0){
				log.warn("push training data: "+sitekey);
				Map<String,JSONObject> urls = JSON.parseObject(urlsContent,HashMap.class);
				for (String url:urls.keySet()){
					JSONObject json = urls.get(url);
					WebUrl item = JSON.parseObject(json.toJSONString(),WebUrl.class);
					String fileName = item.getUrl().hashCode()+".data";
					String fileP = "data/pages2/"+sitekey+"/"+fileName;
					File ff = new File(fileP);
					if (!ff.exists()) continue;
					String pureContext = FileUtil.readFile(fileP);
					FileUtil.writeFile("data/training/"+getCatStr(item.getCat())+"/"+item.getUrl().hashCode()+".data",pureContext);
				}
			}
		}		
	}
	
	public void mregeTrainingTerms(){
		//搬移已分类page到training path:
		List<File> folders = FileUtil.getFolders("data/training/");
		for (File f:folders){
			List<File> files = FileUtil.getFiles(f.getAbsolutePath());
			log.warn("push training data: "+f.getName());
			Map<String,Integer> termsMap = new HashMap<String,Integer>();
			List<Map.Entry<String,Integer>> mappingList = null;
			for (File ff:files){
					String pageContent = FileUtil.readFile(ff.getAbsoluteFile());
					JSONArray ss = JSON.parseArray(pageContent);
					for (int i=0;i<ss.size();i++){
						JSONObject obj = (JSONObject)ss.get(i);
						String key = obj.getString("key");
						int count = obj.getInteger("value");
    					if (termsMap.containsKey(key))
    						termsMap.put(key, termsMap.get(key)+count);
    					else
    						termsMap.put(key, count);
					}
					mappingList = new ArrayList<Map.Entry<String,Integer>>(termsMap.entrySet()); 
					  Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){ 
					   public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){ 
						   return mapping2.getValue().compareTo(mapping1.getValue()); 
					   } 
					  }); 					
			}
			String mapStr = "";
			for (Map.Entry<String,Integer> ss:mappingList){
				mapStr += ss.getKey()+":"+ss.getValue()+"\n";
			}
			FileUtil.writeFile(trainingPath+"/"+f.getName()+".data",mapStr);
		}		
	}
	
	public void trainingClassifiers(){
		Pipe instancePipe = new SerialPipes (new Pipe[] {
				new Target2Label (),							  // Target String -> class label
				new Input2CharSequence (),				  // Data File -> String containing contents
				//new CharSubsequence (CharSubsequence.SKIP_HEADER), // Remove UseNet or email header
				new CharSequence2GBKTokenSequence (),  // Data String -> TokenSequence
				new TokenSequenceLowercase (),		  // TokenSequence words lowercased
				new TokenSequenceRemoveStopwords (),// Remove stopwords from sequence
				new TokenSequence2FeatureSequence(),// Replace each Token with a feature index
				new FeatureSequence2WeightFeatureVector(11),// Collapse word order into a "weight feature vector"
//				new PrintInputAndTarget(),
			});
		
		List<File> folders = FileUtil.getFolders(trainingPath);
		InstanceList ilist = new InstanceList (instancePipe);
		for (File f:folders){
			int catid = Integer.valueOf(f.getName());
			
			// Create an empty list of the training instances
			String directories = trainingPath+catid;
			ilist.addThruPipe (new FileIterator (directories, FileIterator.STARTING_DIRECTORIES));
		}
		ClassifierTrainer naiveBayesTrainer = new DecisionTreeTrainer ();	
		Classifier classifier = naiveBayesTrainer.train (ilist);
		TopItem item = new TopItem();
		item.setContent("李彦宏马云");

		String filename = trainingPath+"/news_dt.classifier";
		log.warn("build classfier:"+ filename);
		//保存:
		try {
			ObjectOutputStream oos = new ObjectOutputStream
				(new FileOutputStream (filename));
			oos.writeObject (classifier);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException ("Couldn't write classifier to filename "+
												filename);
		}		
	}
	
	public String getCatStr(int catid){
		return catStrs.get(catid);
	}
	
	public String getTitleKey(String title){
		
		for (int i=1;i<=catIds.size();i++){
			String key = catStrs.get(i);
			if (!catKeywords.containsKey(key))continue;
			List<String> words = catKeywords.get(key);
			for (String word:words){
				if (title.toLowerCase().indexOf(word.toLowerCase())>=0)
					return key;
			}
		}
		return null;
	}
	
	private InstanceList initPipe(int catid,TopItem item){
		Pipe instancePipe = new SerialPipes (new Pipe[] {
				new Target2Label (),							  // Target String -> class label
				new CharSequence2GBKTokenSequence (),  // Data String -> TokenSequence
				new TokenSequenceRemoveStopwords (),// Remove stopwords from sequence
				new TokenSequence2FeatureSequence(),// Replace each Token with a feature index
				new FeatureSequence2WeightFeatureVector(catid),// Collapse word order into a "feature vector"
				new PrintInputAndTarget(),
			});		
		
		// Create an empty list of the training instances
		InstanceList ilist = new InstanceList (instancePipe);
		ilist.addThruPipe (new StringIterator (item.getContent(), null));
//		ilist.addThruPipe (new FileIterator (directories, FileIterator.STARTING_DIRECTORIES));
		
		return ilist;
	}
	
	private double getTitleAccura(TopItem item,int catid){
		int peopleCount = 0;
		int comCount = 0;
		List<SegToken> segToken = segmenter.process(item.getCtitle(), SegMode.INDEX);
		for (SegToken token:segToken){
			String w = token.word;
			if (peopleSet.contains(w)){
				peopleCount++;
			}else if (comSet.contains(w))
				comCount++;
		}
		
		double titleAccur = 0;
		//人名:
		if (catid==CAT_PEOPLE){
			if (peopleCount==1)
				titleAccur += 0.5;
		}else if (catid==CAT_COMPANY){
			if (comCount==1)
				titleAccur += 0.5;
		}
		return titleAccur;
	}
	
	public int testClassify2(TopItem item){
		if (item.getUrl().equals("http://tech.sina.com.cn/it/2016-06-24/doc-ifxtmweh2490652.shtml")){
			int a = 10;
			a++;
		}
		String key = this.getTitleKey(item.getCtitle());
		if (key!=null&&catIds.containsKey(key)){
			//log.warn(item.getCtitle()+" 类别 :"+key+","+catIds.get(key));
			return catIds.get(key);
		}
//		log.warn("未找到分类:"+item.getCtitle()+","+item.getUrl());
		return -1;
	}
	
	public int testClassify(TopItem item){
		double accur = 0;
		int testCatid = -1;
		double catValue = 0;
		try {
			Iterator<Instance> i2 = new StringIterator (item.getContent(), null);
			Iterator<Instance> iterator0 = 
					classifier.getInstancePipe().newIteratorFrom(i2);
			
			Pipe pp = classifier.getInstancePipe();
			
			while (iterator0.hasNext()) {
				Instance instance = iterator0.next();
				Labeling labeling = 
						classifier.classify(instance).getLabeling();
				
				Label catLabel = null;
				for (int location = 0; location < labeling.numLocations(); location++) {
					Label label = labeling.labelAtLocation(location);
					double value = labeling.valueAtLocation(location);
					float dd = Double.valueOf(value).floatValue();
//					log.warn(label.toString()+":"+value);
//					System.out.printf(label.toString()+":%.30f \n",value);
					if (label.toString().indexOf("16")>0){
						int tt = 10;
					}
					if (catValue<=0||value>catValue){
//						System.out.printf("%.30f, %.30f \n",catValue,value);
						catLabel = label;
						catValue = value;
					}
				}				
				if (catLabel!=null){
					String strLabel = catLabel.toString();
					int startIndex = strLabel.lastIndexOf("/");
					if (strLabel.lastIndexOf("\\")>0)
						startIndex = strLabel.lastIndexOf("\\");
					String post = strLabel.substring(startIndex+1);
					if (post.matches("[0-9]+"))
						testCatid = Integer.valueOf(post);
				}
			}
			
		}catch (Exception e){
			log.warn("item classify failed:"+e.getMessage());
			e.printStackTrace();
		}
		log.warn("def cat:"+this.getCatStr(testCatid));
		System.out.printf("%.20f \n", catValue);
		log.warn(item.getCtitle());
//		log.warn(item.getUrl());
//		log.warn(item.getUrl().hashCode());
		return testCatid;
	}
	
	public void nameCatWithSVM(){
		String path = "C:\\boxlib\\document-processor-master\\test\\";
		String labelstr = FileUtil.readFile(path+"labels.txt");
		Map<Integer,String> labelmap = new HashMap<Integer,String>();
		String[] labels = labelstr.split("\n");
		for (String label:labels){
			String[] ll = label.split("\\s+");
			labelmap.put(Integer.valueOf(ll[0]), ll[1]);
		}
		String namestr = FileUtil.readFile(path+"names.list");
		String predictstr = FileUtil.readFile(path+"predict.txt");
		String[] pres = predictstr.split("\n");
		String[] pres2 = new String[pres.length];
		for (int i=0;i<pres.length;i++){
			String ss = pres[i];
			int cat = Double.valueOf(ss).intValue();
			String str = labelmap.get(cat);
			pres2[i] = str;
		}
		
	}
	public void moveTrainingWords(){
		//搬移已分类page到training path:
		List<File> folders = FileUtil.getFolders("data/training/");
		SiteTermProcessor processor = new SiteTermProcessor("http://www.sohu.com",10);
		for (File f:folders){
			String catStr = f.getName();
			List<File> files = FileUtil.getFiles(f.getAbsolutePath());
			Map<String,Integer> termsMap = new HashMap<String,Integer>();
			for (File file:files){
				String content = FileUtil.readFile(file);
				processor.getWordTerms(content, termsMap,2);
			}
			  //通过比较器实现比较排序 
				List<Map.Entry<String,Integer>> mappingList = new ArrayList<Map.Entry<String,Integer>>(termsMap.entrySet()); 
			  Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){ 
			   public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){ 
				   return mapping2.getValue().compareTo(mapping1.getValue()); 
			   } 
			  }); 			
			String wordContent = "";
			int i = 0;
			for (Map.Entry<String,Integer> item:mappingList){
				wordContent += item.getKey()+",";
				i++;
//				if (i%10==9)
//					wordContent += "\n";
			}
			if (wordContent.length()>0){
				FileUtil.writeFile("data/training/"+catStr+".data",wordContent);
			}
		}
				
	}

	public void moveTrainingTerms(){
		//搬移已分类page到training path:
		List<File> files = FileUtil.getFiles("data/pages/");
		for (File f:files){
			if (f.getName().indexOf(".json")<0)continue;
			String sitekey = f.getName().substring(0,f.getName().indexOf("_urls.json"));
			String urlsContent = FileUtil.readFile(f);
			if (urlsContent!=null&&urlsContent.trim().length()>0){
				log.warn("push training data: "+sitekey);
				Map<String,JSONObject> urls = JSON.parseObject(urlsContent,HashMap.class);
				for (String url:urls.keySet()){
					JSONObject json = urls.get(url);
					WebUrl item = JSON.parseObject(json.toJSONString(),WebUrl.class);
					if (item.getCat()<=0) continue;
					String fileName = item.getUrl().hashCode()+".terms";
					String fileP = "data/terms/"+sitekey+"/"+fileName;
					File ff = new File(fileP);
					if (!ff.exists()) continue;
					String pageContent = FileUtil.readFile(fileP);
					JSONArray ss = JSON.parseArray(pageContent);
					String termStr = "";
					int count2 = 30;	//取前30个关键词
					if (ss!=null){
					int count = ss.size()<count2?ss.size():count2;
					for (int i=0;i<count;i++){
						JSONObject obj = (JSONObject)ss.get(i);
						termStr += obj.getString("key")+",";
					}	
					}
					String pureContext = pageContent;
					FileUtil.writeFile(trainingPath+"/"+item.getCat()+"/"+item.getUrl().hashCode()+".data",pureContext);
				}
			}
		}		
	}

	public static void main(String[] args) {
		String str = "http://tech.sina.com.cn/i/2016-06-29/doc-ifxtsatm0995788.shtml";
		int a = str.hashCode();
		//-892462432
		NewsClassifier classifier = new NewsClassifier();
		classifier.moveTrainingWords();
		
//		JiebaSegmenter segmenter = new JiebaSegmenter();
//		String sentence = FileUtil.readFile("data/pages2/163.com/2477114.data");
//		List<SegToken> segToken = segmenter.process(sentence, SegMode.INDEX);
//		List<String> strs = new ArrayList<String>();
//		for (SegToken s:segToken){
//			strs.add(s.word);
//		}
//		List<String> tokens = segmenter.sentenceProcess(sentence);
//		System.out.println(JSON.toJSONString(tokens.size()));
//		System.out.println(JSON.toJSONString(strs.size()));
//	      WordCount wc0=new WordCount();
//	        wc0.wordCount("data/news/files","data/news/frequency");
////	        //create matrix
//	        WordDocMatrix wm=new WordDocMatrix();
//	        wm.createMatrix("data/news/frequency", "data/news/matrix");
////	        create features
//		FeatureSelect fs = new FeatureSelect("data/news/db/");
////	        FS fs=new FS();
//	        fs.createFeatures("data/news/frequency", "data/news/matrix","data/news/features");
	        
//	     	
//	        //create vector space with features:
//	        TVSM sm0 = new TVSM();
//	        File feaFile = new File("data/news/features");
//	        sm0.initFeatures(feaFile);
//	        // inst.printFeature();
//	        File freqFile = new File("data/news/frequency");
//	        
//	        if (!freqFile.exists()) {
//	            System.out.println("文件不存在，程序退出.");
//	            System.exit(2);
//	        }
//	        sm0.buildDVM(freqFile);
//	        sm0.unionVector();	        
	 		
//		classifier.moveTrainingWords();
//		classifier.moveTrainingPages();
//		classifier.trainingClassifiers();
//		classifier.moveTrainingTerms();
//		classifier.mregeTrainingTerms();
//		BaseTopItemParser parser = new BaseTopItemParser("data/dna/");
//		String sitekey = "sina.com.cn";
//		WebUrl item  = new WebUrl();
//		item.setUrl("http://tech.sina.com.cn/i/2016-06-29/doc-ifxtsatm0995788.shtml");
//		String path = "data/pages/"+sitekey+"/"+item.getUrl().hashCode()+".html";
//		String pageContent = FileUtil.readFile(path);
//		TopItem topitem = parser.parse(item.getUrl(), pageContent);		
	}

}
