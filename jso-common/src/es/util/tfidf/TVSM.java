package es.util.tfidf;

/**
 * Author: Orisun
 * Date: Aug 29, 2011
 * FileName: DVM.java
 * Function: build document vector model for documents in train
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Vector;
import es.util.tfidf.*;

public class TVSM {

    HashSet<String> features = new HashSet<String>();// 存放最终选择的特征词
    int len; // 特征项的个数,亦即文档向量的长度
    File path = new File("orisun/dvm"); // 存放文档向量的路径
    HashMap<String, Vector<Double>> dv = new HashMap<String, Vector<Double>>(); // key是文档名，value是文档对应的规一化之前的向量
    double[] sqrt;        //存储向量数组每个位置上的平方和

    public void printFeature() {
        Iterator<String> iter = features.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + "\t");
        }
        System.out.println();
    }

    // 从文件中读入特征项。参数文件存储经过特征选择后剩下的特征项。
    public void initFeatures(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                features.add(line.split("\\s+")[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        len = features.size();
        sqrt = new double[len];
    }

    public void buildDVM(File srcFile) {
    	int docNumber = 6;
        if (srcFile.isDirectory()) {
            File[] children = srcFile.listFiles();
            for (File child : children) {
                buildDVM(child);
            }
        } else if (srcFile.isFile()) {
            IG.initMatrix(new File("orisun/matrix"),docNumber);
            // new featureselect.WordDocMatrix().printMatrix(System.out,featureselect.IG.matrix);
            Object[] feature_array = features.toArray();
            HashMap<String, Double> fea_wei = new HashMap<String, Double>();

            int filename = Integer.parseInt(srcFile.getName());
            Vector<Double> v = new Vector<Double>(len);
            for (int i = 0; i < len; i++)
                v.add(0.0);
            try {
                FileReader fr = new FileReader(srcFile);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) { // 逐个读取文档中的词语
                    String word = line.split("\\s+")[0];
                    if (!features.contains(word))
                        continue;
                    int tf = 0; // 特征项在本文档中出现的频率
                    int Ni = 0; // 出现特征项的文档数目
                    int N = docNumber; // 全部文档数目
                    ArrayList<Short> al = IG.matrix.get(word);
                    tf = al.get(filename);
                    for (int i = 0; i < N; i++) {
                        if (al.get(i) > 0)
                            Ni++;
                    }
                    // System.out.println("word="+word+"\tfilenmae="+filename+"\ttf="+tf+"\tNi="+Ni);
                    double weight = -1.0 * tf
                            * Math.log(1.0 * Ni / N + Double.MIN_VALUE);
                    fea_wei.put(word, weight);
                }
                for (int i = 0; i < feature_array.length; i++) {
                    String feat = feature_array[i].toString();
                    double w = 0.0;
                    if (fea_wei.containsKey(feat))
                        w = fea_wei.get(feat);
                    v.set(i, w);
                    sqrt[i] += Math.pow(w, 2);
                }
                dv.put(String.valueOf(filename), v);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //文档向量归一化
    public void unionVector() {
        Iterator<Entry<String, Vector<Double>>> iter = dv.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Vector<Double>> entry = iter.next();
            String fname = entry.getKey();
            Vector<Double> v = entry.getValue();
            Iterator<Double> it=v.iterator();
            
            File newFile = new File(path, fname);
            try {
                newFile.createNewFile();
                FileWriter fw=new FileWriter(newFile);
                BufferedWriter bw=new BufferedWriter(fw);
                
                int index=0;
                while(it.hasNext()){
                    double d=it.next();
                    d/=Math.sqrt(sqrt[index]);
                    //归一化后写入文件
                    bw.write(String.valueOf(d));
                    bw.newLine();
                    index++;
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        TVSM inst = new TVSM();
        File feaFile = new File("orisun/features");
        inst.initFeatures(feaFile);
        // inst.printFeature();
        File freqFile = new File("orisun/frequency");
        
        if (!freqFile.exists()) {
            System.out.println("文件不存在，程序退出.");
            System.exit(2);
        }
        inst.buildDVM(freqFile);
        inst.unionVector();
    }
}