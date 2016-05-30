package cn.hd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.classify.Trial;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVector;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.Labeling;
import cc.mallet.util.Randoms;

public class Maxent implements Serializable{
    
    //Train a classifier
    public static Classifier trainClassifier(InstanceList trainingInstances) {
        // Here we use a maximum entropy (ie polytomous logistic regression) classifier.                                                 
        ClassifierTrainer trainer = new MaxEntTrainer();
        return trainer.train(trainingInstances);
    }
    
    //save a trained classifier/write a trained classifier to disk
    public void saveClassifier(Classifier classifier,String savePath) throws IOException{
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(savePath));
        oos.writeObject(classifier);
        oos.flush();
        oos.close();        
    }
    
    //restore a saved classifier
    public Classifier loadClassifier(String savedPath) throws FileNotFoundException, IOException, ClassNotFoundException{                                              
        // Here we load a serialized classifier from a file.
        Classifier classifier;
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (new File(savedPath)));
        classifier = (Classifier) ois.readObject();
        ois.close();
        return classifier;
    }
    
    //predict & evaluate
    public String predict(Classifier classifier,Instance testInstance){
        Labeling labeling = classifier.classify(testInstance).getLabeling();
        Label label = labeling.getBestLabel();
        return (String)label.getEntry();
    }
    
    public void evaluate(Classifier classifier, String testFilePath) throws IOException {
        InstanceList testInstances = new InstanceList(classifier.getInstancePipe());                                                                                                                                                                
        
        //format of input data:[name] [label] [data ... ]                                                                    
        CsvIterator reader = new CsvIterator(new FileReader(new File(testFilePath)),"(\\w+)\\s+(\\w+)\\s+(.*)",3, 2, 1);  // (data, label, name) field indices               

        // Add all instances loaded by the iterator to our instance list
        testInstances.addThruPipe(reader);
        Trial trial = new Trial(classifier, testInstances);

        //evaluation metrics.precision, recall, and F1
        System.out.println("Accuracy: " + trial.getAccuracy());                                                      
        System.out.println("F1 for class 'good': " + trial.getF1("good"));
        System.out.println("Precision for class '" +
                           classifier.getLabelAlphabet().lookupLabel(1) + "': " +
                           trial.getPrecision(1));
    }

    //perform n-fold cross validation
     public static Trial testTrainSplit(MaxEntTrainer trainer, InstanceList instances) {
         int TRAINING = 0;
         int TESTING = 1;
         int VALIDATION = 2;
     
         // Split the input list into training (90%) and testing (10%) lists.
         InstanceList[] instanceLists = instances.split(new Randoms(), new double[] {0.9, 0.1, 0.0});
         Classifier classifier = trainClassifier(instanceLists[TRAINING]);
         return new Trial(classifier, instanceLists[TESTING]);
      }
     
    public static void main(String[] args){
        //define training samples
        Alphabet featureAlphabet = new Alphabet();//特征词典
        LabelAlphabet targetAlphabet = new LabelAlphabet();//类标词典
        targetAlphabet.lookupIndex("positive");
        targetAlphabet.lookupIndex("negative");
        targetAlphabet.lookupIndex("neutral");
        targetAlphabet.stopGrowth();
        featureAlphabet.lookupIndex("f1");
        featureAlphabet.lookupIndex("f2");
        featureAlphabet.lookupIndex("f3");
        InstanceList trainingInstances = new InstanceList (featureAlphabet,targetAlphabet);//实例集对象
        final int size = targetAlphabet.size();
        double[] featureValues1 = {1.0, 0.0, 0.0};
        double[] featureValues2 = {2.0, 0.0, 0.0};
        double[] featureValues3 = {0.0, 1.0, 0.0};
        double[] featureValues4 = {0.0, 0.0, 1.0};
        double[] featureValues5 = {0.0, 0.0, 3.0};
        String[] targetValue = {"positive","positive","neutral","negative","negative"};
        List<double[]> featureValues = Arrays.asList(featureValues1,featureValues2,featureValues3,featureValues4,featureValues5); 
        int i = 0;
        for(double[]featureValue:featureValues){
            FeatureVector featureVector = new FeatureVector(featureAlphabet,
                    (String[])targetAlphabet.toArray(new String[size]),featureValue);//change list to array
            Instance instance = new Instance (featureVector,targetAlphabet.lookupLabel(targetValue[i]), "xxx",null);
            i++;
            trainingInstances.add(instance);
        }
         
        Maxent maxent = new Maxent();
        Classifier maxentclassifier = maxent.trainClassifier(trainingInstances);
        //loading test examples
        double[] testfeatureValues = {0.5, 0.5, 6.0};
        FeatureVector testfeatureVector = new FeatureVector(featureAlphabet,
                (String[])targetAlphabet.toArray(new String[size]),testfeatureValues);
        //new instance(data,target,name,source)
        Instance testinstance = new Instance (testfeatureVector,targetAlphabet.lookupLabel("negative"), "xxx",null);
        System.out.print(maxent.predict(maxentclassifier, testinstance));
        //maxent.evaluate(maxentclassifier, "resource/testdata.txt");
    }
}