package box.site.classify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import box.site.model.TopItem;
import box.site.model.WebUrl;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
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
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

public class NewsClassifier {
	public final static int CAT_HOT = 1;
	public final static int CAT_MARKET = 2;
	public final static int CAT_PRODUCT = 3;
	public final static int CAT_COMPANY = 4;
	public final static int CAT_PEOPLE = 5;
	public final static int CAT_STARTUP = 6;	
	private String trainingPath ="data/training";
	private Set<String>  peopleSet = new HashSet<String>();
	private Set<String>  comSet = new HashSet<String>();
	private Map<Integer,Classifier>	classifyMap = new HashMap<Integer,Classifier>();
	JiebaSegmenter segmenter = new JiebaSegmenter();
	
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
		
		List<File> classifierFiles = FileUtil.getFiles(trainingPath);
		try {
		for (File f:classifierFiles){
			int endindex = f.getName().indexOf(".classifier");
			if (endindex<0) continue;
			String fn = f.getName().substring(0,endindex);
			int catid = Integer.valueOf(fn);
			FileInputStream fis = new FileInputStream(f);
           ObjectInputStream ois = new ObjectInputStream(fis);  
            Classifier classifier = (Classifier) ois.readObject(); 
            classifyMap.put(catid, classifier);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public void trainingClassifiers(){
		//搬移已分类page到training path:
		String path = "data/pages/";
		List<File> files = FileUtil.getFiles(path);
		for (File f:files){
			if (f.getName().indexOf(".json")<0)continue;
			String sitekey = f.getName().substring(0,f.getName().indexOf("_urls.json"));
			String content = FileUtil.readFile(f);
			if (content!=null&&content.trim().length()>0){
				Map<String,JSONObject> urls = JSON.parseObject(content,HashMap.class);
				for (String url:urls.keySet()){
					JSONObject json = urls.get(url);
					WebUrl item = JSON.parseObject(json.toJSONString(),WebUrl.class);
					if (item.getCat()<=0) continue;
					String fileName = item.getUrl().hashCode()+".html";
					String fileP = path+sitekey+"/"+fileName;
					File ff = new File(fileP);
					if (!ff.exists()) continue;
					String pageC = FileUtil.readFile(fileP);
					FileUtil.writeFile(trainingPath+"/"+item.getCat()+"/"+fileName,pageC);
				}
			}
		}
		
		List<File> folders = FileUtil.getFolders(trainingPath);
		for (File f:folders){
			int catid = Integer.valueOf(f.getName());
			
			Pipe instancePipe = new SerialPipes (new Pipe[] {
					new Target2Label (),							  // Target String -> class label
					new Input2CharSequence (),				  // Data File -> String containing contents
					//new CharSubsequence (CharSubsequence.SKIP_HEADER), // Remove UseNet or email header
					new CharSequence2GBKTokenSequence (),  // Data String -> TokenSequence
					new TokenSequenceLowercase (),		  // TokenSequence words lowercased
					new TokenSequenceRemoveStopwords (),// Remove stopwords from sequence
					new TokenSequence2FeatureSequence(),// Replace each Token with a feature index
					new FeatureSequence2WeightFeatureVector(catid),// Collapse word order into a "weight feature vector"
//					new PrintInputAndTarget(),
				});
			
			// Create an empty list of the training instances
			InstanceList ilist = new InstanceList (instancePipe);
			String directories = trainingPath+"/"+catid;
			ilist.addThruPipe (new FileIterator (directories, FileIterator.STARTING_DIRECTORIES));
	
			// Create a classifier trainer, and use it to create a classifier
			ClassifierTrainer naiveBayesTrainer = new NaiveBayesTrainer ();	
			Classifier classifier = naiveBayesTrainer.train (ilist);
			String filename = trainingPath+"/"+catid+".classifier";
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
	}
	
	private InstanceList initPipe(TopItem item){
		Pipe instancePipe = new SerialPipes (new Pipe[] {
				new Target2Label (),							  // Target String -> class label
				new CharSequence2GBKTokenSequence (),  // Data String -> TokenSequence
				new TokenSequenceRemoveStopwords (),// Remove stopwords from sequence
				new TokenSequence2FeatureSequence(),// Replace each Token with a feature index
				new FeatureSequence2FeatureVector(),// Collapse word order into a "feature vector"
				new PrintInputAndTarget(),
			});		
		
		StringBuffer sb = new StringBuffer (item.getContent().length());
		sb.append(item.getContent());
		Instance inst = new Instance(sb,item.getCat(),item.getId(),item.getContent());
		// Create an empty list of the training instances
		InstanceList ilist = new InstanceList (instancePipe);
		ilist.add(inst);
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
	
	public int testClassify(TopItem item){
		double accur = 0;
		int testCatid = -1;
		for (int catid:classifyMap.keySet()){
			Classifier classifier = classifyMap.get(catid);
			double acc1 = classifier.getAccuracy(initPipe(item));
			acc1 += getTitleAccura(item,catid);
			if (acc1>0.8&&acc1>accur){
				accur = acc1;
				testCatid = catid;
			}
		}
		return testCatid;
	}
	
	public static void main(String[] args) {
		NewsClassifier classifier = new NewsClassifier();
		classifier.trainingClassifiers();
	}

}
