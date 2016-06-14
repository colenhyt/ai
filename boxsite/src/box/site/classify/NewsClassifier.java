package box.site.classify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.site.model.TopItem;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.pipe.CharSequence2GBKTokenSequence;
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

public class NewsClassifier {
	private String trainingPath ="data/training";
	private Map<Integer,Classifier>	classifyMap = new HashMap<Integer,Classifier>();
	
	public NewsClassifier(){
		List<File> classifierFiles = FileUtil.getFiles(trainingPath);
		try {
		for (File f:classifierFiles){
			int endindex = f.getName().indexOf(".classifier");
			if (endindex<0) continue;
			String fn = f.getName().substring(0,endindex);
			int catid = Integer.valueOf(fn);
			FileInputStream fis = new FileInputStream(f);
           ObjectInputStream ois = new ObjectInputStream(fis);  
//            Classifier classifier = (Classifier) ois.readObject(); 
//            classifyMap.put(catid, classifier);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public void trainingClassifiers(){
		// Create the pipeline that will take as input {data = File, target = String for classname}
		// and turn them into {data = FeatureVector, target = Label}
		Pipe instancePipe = new SerialPipes (new Pipe[] {
			new Target2Label (),							  // Target String -> class label
			new Input2CharSequence ("gb2312"),				  // Data File -> String containing contents
			//new CharSubsequence (CharSubsequence.SKIP_HEADER), // Remove UseNet or email header
			new CharSequence2GBKTokenSequence (),  // Data String -> TokenSequence
			new TokenSequenceLowercase (),		  // TokenSequence words lowercased
			new TokenSequenceRemoveStopwords (),// Remove stopwords from sequence
			new TokenSequence2FeatureSequence(),// Replace each Token with a feature index
			new FeatureSequence2WeightFeatureVector(),// Collapse word order into a "weight feature vector"
			new PrintInputAndTarget(),
		});

		// Create an empty list of the training instances
		InstanceList ilist = new InstanceList (instancePipe);

		List<File> folders = FileUtil.getFolders(trainingPath);
		for (File f:folders){
			ilist = new InstanceList (instancePipe);
			int catid = Integer.valueOf(f.getName());
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
	
	public int testClassify(TopItem item){
		double accur = 0;
		int testCatid = 1;
		for (int catid:classifyMap.keySet()){
			Classifier classifier = classifyMap.get(catid);
			double acc1 = classifier.getAccuracy(initPipe(item));
			if (acc1>accur){
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
