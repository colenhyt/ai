package es.util.tfidf;
/**
 * Author: Orisun
 * Date: Aug 31, 2011
 * FileName: ADVM.java
 * Function: build document vector model for a new come document
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
import java.util.Vector;
import es.util.tfidf.*;

public class AVSM {

    HashSet<String> features = new HashSet<String>();// 存放最终选择的特征词
    int len; // 特征项的个数,亦即文档向量的长度
    File path = new File("orisun/dvm2"); // 存放文档向量的路径

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
    }
    
    //参数文件存放文档中单词的频数
    public void buildDVM(File srcFile) {
    	int docNumber = 6;
        if (srcFile.isDirectory()) {
            File[] children = srcFile.listFiles();
            for (File child : children) {
                buildDVM(child);
            }
        } else if (srcFile.isFile()) {
            IG.initMatrix(new File("orisun/matrix"),docNumber);
            Object[] feature_array = features.toArray();
            HashMap<String, Double> fea_wei = new HashMap<String, Double>();

            String filename = srcFile.getName();
            File newFile = new File(path, filename);
            Vector<Double> v = new Vector<Double>(len);
            for (int i = 0; i < len; i++)
                v.add(0.0);
            try {
                newFile.createNewFile();
                FileReader fr = new FileReader(srcFile);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) { // 逐个读取文档中的词语
                    String[] content=line.split("\\s+");
                    String word = content[0];
                    int tf=Integer.parseInt(content[1]);// 特征项在本文档中出现的频率
                    if (!features.contains(word))
                        continue;
                    int Ni = 0; // 出现特征项的文档数目
                    int N = docNumber; // 全部文档数目
                    ArrayList<Short> al = IG.matrix.get(word);
                    for (int i = 0; i < N; i++) {
                        if (al.get(i) > 0)
                            Ni++;
                    }
                    System.out.println("word="+word+"\tfilenmae="+filename+"\ttf="+tf+"\tNi="+Ni);
                    double weight = -1.0 * tf
                            * Math.log(1.0 * Ni / N + Double.MIN_VALUE);
                    fea_wei.put(word, weight);
                }
                for(int i=0;i<feature_array.length;i++){
                    String feat=feature_array[i].toString();
                    double w=0.0;
                    if(fea_wei.containsKey(feat))
                        w=fea_wei.get(feat);
                    v.set(i, w);
                }
                
                //把文档向量写入dvm路径下的文件
                FileWriter fw=new FileWriter(newFile);
                BufferedWriter bw=new BufferedWriter(fw);
                Iterator<Double> iter=v.iterator();
                while(iter.hasNext()){
                    bw.write(String.valueOf(iter.next()));
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args){
        AVSM inst=new AVSM();
        File feaFile=new File("orisun/features"); 
        inst.initFeatures(feaFile);
        //inst.printFeature();
        File freqFile=new File("orisun/unknown");
        inst.buildDVM(freqFile);
        if(!freqFile.exists()){
            System.out.println("文件不存在，程序退出.");
            System.exit(2);
        }
        
    }
}