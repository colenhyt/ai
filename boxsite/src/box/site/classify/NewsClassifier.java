package box.site.classify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	public Map<String,Integer> catIds = new HashMap<String,Integer>();
	public Map<Integer,String> catStrs = new HashMap<Integer,String>();
	public Map<String,String> catKeyStrMap = new HashMap<String,String>();
	public Map<String,Integer> catIdMap = new HashMap<String,Integer>();
	private Map<Integer,Classifier>	classifyMap = new HashMap<Integer,Classifier>();
	JiebaSegmenter segmenter = new JiebaSegmenter();
	private Classifier classifier;
	private Map<String,Set<String>> catWordMaps = new HashMap<String,Set<String>>();
	
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
		
		List<File> wordFiles = FileUtil.getFiles(trainingPath,"txt");
		for (File f:wordFiles){
			String mapstr = FileUtil.readFile(f);
			Set<String> wordset = new HashSet<String>();
			if (mapstr.trim().length()>0){
				String[] words = mapstr.split(",");
				for (String word:words){
					if (word.trim().length()<=0) continue;
					wordset.add(word.trim());
				}
			}	
			String name = f.getName().substring(0,f.getName().indexOf(".txt"));
			catWordMaps.put(name, wordset);
		}
		
		String catkeysContent = FileUtil.readFile("data/catkeys.txt");
		String[] catkeyline = catkeysContent.split("\n");
		for (String str:catkeyline){
		 String[] ss = str.split(":");
		 String[] words = ss[1].split(",");
		 for (String word:words){
			 catKeyStrMap.put(word, ss[0]);
		 }
		}
		String catcontent = FileUtil.readFile("data/cats.txt");
		catIdMap = (Map<String,Integer>)JSON.parseObject(catcontent,HashMap.class);
		
		int  t= 10;
		//File classifyFile = new File(trainingPath+"news_dt.classifier");
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
	
	public void findSogouPageTerms(){
		String path = "C:\\boxlib\\SogouCS.reduced";
		String writePath = "data/pages2/";
		List<File> files = FileUtil.getFiles(path);
			for (File f:files){
	          //读取prop.xml资源
				String text = FileUtil.readFile(f,"gbk");
				String[] docs = text.split("</doc>");
				String titles = "";
				for (String doc:docs){
					if (doc.indexOf("<url>")<0||doc.indexOf("<contenttitle>")<0)continue;
					if (doc.indexOf("<content>")<0||doc.indexOf("<docno>")<0)continue;
		        	  String url = doc.substring(doc.indexOf("<url>")+"<url>".length(),doc.indexOf("</url>"));
		        	  if (url.indexOf("it.sohu.com")<0) continue;
		        	  String contenttitle =doc.substring(doc.indexOf("<contenttitle>")+"<contenttitle>".length(),doc.indexOf("</contenttitle>"));
		        	  String content = doc.substring(doc.indexOf("<content>")+"<content>".length(),doc.indexOf("</content>"));
		        	  String docno = doc.substring(doc.indexOf("<docno>")+"<docno>".length(),doc.indexOf("</docno>"));
		        	  if (contenttitle.trim().length()>0&&content.trim().length()>0){
		        		  titles += contenttitle+"\n";
		        		  String context = contenttitle+"||"+content;
			        	  log.warn(url);					
		        	  }
				}
				String name = f.getName().substring(0,f.getName().indexOf(".txt"));
      		  FileUtil.writeFile("data/sogou/"+name+".titles", titles);
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
	
	public String getTitleKey(String title){
		
		for (String key:catWordMaps.keySet()){
			Set<String> wordset = catWordMaps.get(key);
			for (String word:wordset){
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
	
	public void testTitlesCat(){
		Map<String,Set<String>> catTitles = new HashMap<String,Set<String>>();
		List<File> files = FileUtil.getFiles("data/pages2", "titles");
		int total = 0;
		int catCount = 0;
		for (File f:files){
			if (f.getName().indexOf("iheima")<0)continue;
			String content = FileUtil.readFile(f);
			String[] titles = content.split("\n");
			for (String title:titles){
				if (title.equals("微软将发布Ｈｙｐｅｒ－Ｖ虚拟软件　比原定８月提前２月"))
					log.warn(""+title);
				String catkey = this.getTitleKey(title);
				total++;
				if (catkey==null) {
					catkey = "综合";
					continue;
				}
				catCount++;
				Set<String> tt = catTitles.get(catkey);
				if (tt==null){
					tt = new HashSet<String>();
					catTitles.put(catkey, tt);
				}
				tt.add(title);
			}
		}
		for (String key:catTitles.keySet()){
			Set<String> titles = catTitles.get(key);
			String cc = "";
			log.warn("key:"+key);
			for (String t:titles){
				log.warn(t);
			}
//			FileUtil.writeFile("data/sogou/cat/"+key+".titles", cc);
		}
		log.warn("total "+total+":cat:"+catCount);	
	}
	
	public int testClassifyByTitle(TopItem item){
		if (item.getUrl().equals("http://tech.sina.com.cn/it/2016-06-24/doc-ifxtmweh2490652.shtml")){
			int a = 10;
			a++;
		}
		
		String word = this.getTitleKey(item.getCtitle());
		String catKey = catKeyStrMap.get(word);
		Integer catid = catIdMap.get(catKey);
		int cat = -1;
		if (catid==null){
			cat = catIdMap.get("综合");			//无法分类，归为综合类
		}else if (!catKey.equals("科技")){		//除科技外，其他都归类
			cat = catid;
		}
		return cat;
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
	public static void main(String[] args) {
		String str = "http://tech.sina.com.cn/i/2016-06-29/doc-ifxtsatm0995788.shtml";
		int a = str.hashCode();
		//-892462432
		NewsClassifier classifier = new NewsClassifier();
		classifier.testTitlesCat();
//		String text = FileUtil.readFile("C:\\boxlib\\news_tensite_xml.dat");
//		text = "<root>"+text+"</root>";
//		try {
//	       Document   doc = DocumentHelper.parseText(text);
//        
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
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
