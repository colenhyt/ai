package box.site.classify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import box.site.model.TopItem;
import box.site.model.WebUrl;
import box.site.parser.sites.BaseTopItemParser;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.PrintInputAndTarget;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.pipe.iterator.UnlabeledFileIterator;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import cc.mallet.types.Labeling;

import com.alibaba.fastjson.JSON;
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
	private String trainingPath ="data/training";
	private Set<String>  peopleSet = new HashSet<String>();
	private Set<String>  comSet = new HashSet<String>();
	public Map<String,List<String>> catKeywords = new HashMap<String,List<String>>();
	public Map<String,Integer> catIds = new HashMap<String,Integer>();
	public Map<Integer,String> catStrs = new HashMap<Integer,String>();
	private Map<Integer,Classifier>	classifyMap = new HashMap<Integer,Classifier>();
	JiebaSegmenter segmenter = new JiebaSegmenter();
	private Classifier classifier;
	
	public NewsClassifier(){
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
		
		String keyContent = FileUtil.readFile("data/catkeys.txt");
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
		
		File classifyFile = new File(trainingPath+"news.classifier");
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
	
	public void trainingClassifiers(){
		//搬移已分类page到training path:
		String path = "data/pages/";
//		List<File> files = FileUtil.getFiles(path);
//		for (File f:files){
//			if (f.getName().indexOf(".json")<0)continue;
//			String sitekey = f.getName().substring(0,f.getName().indexOf("_urls.json"));
//			String urlsContent = FileUtil.readFile(f);
//			if (urlsContent!=null&&urlsContent.trim().length()>0){
//				log.warn("push training data: "+sitekey);
//				Map<String,JSONObject> urls = JSON.parseObject(urlsContent,HashMap.class);
//				for (String url:urls.keySet()){
//					JSONObject json = urls.get(url);
//					WebUrl item = JSON.parseObject(json.toJSONString(),WebUrl.class);
//					if (item.getCat()<=0) continue;
//					String fileName = item.getUrl().hashCode()+".html";
//					String fileP = path+sitekey+"/"+fileName;
//					File ff = new File(fileP);
//					if (!ff.exists()) continue;
//					String pageContent = FileUtil.readFile(fileP);
//					List<String> cc = contentGetter.getHtmlContent(item.getUrl(), pageContent);
//					if (cc==null) {
//						log.warn("could not find html context for: "+item.getUrl());
//						continue;
//					}
//					String pureContext = cc.get(0);
//					FileUtil.writeFile(trainingPath+"/"+item.getCat()+"/"+item.getUrl().hashCode()+".data",pureContext);
//				}
//			}
//		}
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
			String directories = trainingPath+"/"+catid;
			ilist.addThruPipe (new FileIterator (directories, FileIterator.STARTING_DIRECTORIES));
	
//			InstanceList[] ilists = ilist.split (new double[] {.8, .2});
			// Create a classifier trainer, and use it to create a classifier
		}
		ClassifierTrainer naiveBayesTrainer = new NaiveBayesTrainer ();	
		Classifier classifier = naiveBayesTrainer.train (ilist);
		TopItem item = new TopItem();
		item.setContent("李彦宏马云");
		
		Iterator<Instance> i2 = new StringIterator (item.getContent(), null);
		Iterator<Instance> iterator0 = 
				classifier.getInstancePipe().newIteratorFrom(i2);
		
//		InstanceList ilist2 = initPipe(21,item);
//		ilist2.
//		double acc1 = classifier.getAccuracy(ilist2);
		
		while (iterator0.hasNext()) {
			Instance instance = iterator0.next();
			Labeling labeling = 
					classifier.classify(instance).getLabeling();
			log.warn("class:"+1);
		}
		
//		log.warn("classify:"+ acc1);
		
		
		File[] directories = new File[1];
		List<File> files = FileUtil.getFolders("C:\\boxsite\\data\\training\\2");
//		for (int i = 0; i < files.size(); i++) {
			directories[0] = new File("C:\\boxsite\\data\\2");
//		}
		Iterator<Instance> fileIterator = new UnlabeledFileIterator (directories);
		Iterator<Instance> iterator = 
			classifier.getInstancePipe().newIteratorFrom(fileIterator);
		
		// Write classifications to the output file
		PrintStream out = null;

			out = System.out;

		// gdruck@cs.umass.edu
		// Stop growth on the alphabets. If this is not done and new
		// features are added, the feature and classifier parameter
		// indices will not match.  
		classifier.getInstancePipe().getDataAlphabet().stopGrowth();
		classifier.getInstancePipe().getTargetAlphabet().stopGrowth();
		
		while (iterator.hasNext()) {
			Instance instance = iterator.next();
			
			Labeling labeling = 
				classifier.classify(instance).getLabeling();

			StringBuilder output = new StringBuilder();
			output.append(instance.getName());

			for (int location = 0; location < labeling.numLocations(); location++) {
				output.append("\t" + labeling.labelAtLocation(location));
				output.append("\t" + labeling.valueAtLocation(location));
			}

			out.println(output);
		}
		
//		initPipe(2,item)
//		double acc2 = classifier.getAccuracy(ilists[1]);
//		log.warn("classify:"+ acc2);
		String filename = trainingPath+"/news.classifier";
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
		try {
			Iterator<Instance> i2 = new StringIterator (item.getContent(), null);
			Iterator<Instance> iterator0 = 
					classifier.getInstancePipe().newIteratorFrom(i2);
			
			if (iterator0.hasNext()) {
				Instance instance = iterator0.next();
				Labeling labeling = 
						classifier.classify(instance).getLabeling();
				
				Label catLabel = null;
				double catValue = 0;
				for (int location = 0; location < labeling.numLocations(); location++) {
					Label label = labeling.labelAtLocation(location);
					double value = labeling.valueAtLocation(location);
					if (catValue<=0||value>catValue){
						catLabel = label;
					}
				}				
				if (catLabel!=null){
					String strLabel = catLabel.toString();
					String[] strs = strLabel.split("/");
					String post = strs[strs.length-1];	
					if (post.matches("[0-9]+"))
						testCatid = Integer.valueOf(post);
				}
			}
			
		}catch (Exception e){
			log.warn("item classify failed:"+e.getMessage());
			e.printStackTrace();
		}
		return testCatid;
	}
	
	public static void main(String[] args) {
		NewsClassifier classifier = new NewsClassifier();
		BaseTopItemParser parser = new BaseTopItemParser("data/dna/");
		String sitekey = "sina.com.cn";
		WebUrl item  = new WebUrl();
		item.setUrl("http://tech.sina.com.cn/i/2016-06-21/doc-ifxtfrrf0763600.shtml");
		String path = "data/pages/"+sitekey+"/"+item.getUrl().hashCode()+".html";
		String pageContent = FileUtil.readFile(path);
		TopItem topitem = parser.parse(item.getUrl(), pageContent);		
//		classifier.trainingClassifiers();
	}

}
