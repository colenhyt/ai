package es.util.tfidf;

/**
 * Author: Orisun
 * Date: Aug 30, 2011
 * FileName: KNN.java
 * Function: K-Nearest Neighbour Algorithm
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

import es.util.tfidf.*;

public class KNN {
    //训练集文档的向量表示
    HashMap<String, Double> dist = new HashMap<String, Double>(); // 等分类文档互每个训练集文档的距离
    Vector<Double> uv = new Vector<Double>(); // 待分类文档的向量表示

    // srcFile存放了待分类文档的向量表示
    public void initUV(File srcFile) {
        if (!srcFile.exists()) {
            System.err.println("File not found.Program exit with failure.");
            System.exit(2);
        }
        try {
            FileReader fr = new FileReader(srcFile);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                uv.add(Double.valueOf(line.trim()));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // srcFile是训练集的文档向量所在的文件夹
    public void calDist(File srcFile) {
        File[] children = srcFile.listFiles();
        
        for (File child : children) {
            String filename = child.getName();
            Vector<Double> v = new Vector<Double>();
            try {
                FileReader fr = new FileReader(child);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    v.add(Double.valueOf(line.trim()));
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int len = v.size();
            double d = cos(v, uv, len);
            dist.put(filename, d);
            System.out.println("到"+filename+"的距离是"+d);
        }
    }

    // 计算两个向量的夹角的余弦。如果此值的绝对值越大，说明夹角越小，越相似，距离越近。
    public double cos(Vector<Double> v1, Vector<Double> v2, int len) {
        double res = 0.0;
        double mul = 0.0;
        double p1 = 0.0, p2 = 0.0;
        for (int i = 0; i < len; i++) {
            double one = v1.get(i);
            double two = v2.get(i);
            mul += one * two;
            p1 += Math.pow(one, 2);
            p2 += Math.pow(two, 2);
        }
        res = Math.abs(mul) / Math.sqrt(p1 * p2);
        return res;
    }

    public void knn(int k){
        //对新向量到所有训练向量的距离按从大到小排序
        FS fs=new FS();
        ArrayList<Entry<String,Double>> dist_list=fs.sort(dist);
        int c1=0,c2=0;
        for(int i=0;i<k;i++){
            Entry<String,Double> entry=dist_list.get(i);
            int fileNum=Integer.parseInt(entry.getKey());
            if(fileNum>=0 && fileNum<3)
                c1++;
            else if(fileNum>=3 && fileNum<6)
                c2++;
        }
        if(c1>c2)
            System.out.println("属于第1类！");
        else if(c2>1)
            System.out.println("属于第2类！");
        else
            System.out.println("属于两类的可能性一样大！");
    }
    
    public static void main(String[] args){
        WordCount wc=new WordCount();
        //word count and al freq:
        File file=new File("orisun/unknown");
        if(!file.exists()){
            System.out.println("文件不存在，程序退出.");
            System.exit(2);
        }
        wc.wordCount(file,"orisun");
        
        AVSM sm=new AVSM();
        File feaFile=new File("orisun/features"); 
        sm.initFeatures(feaFile);
        //inst.printFeature();
        File freqFile=new File("orisun/unknown");
        sm.buildDVM(freqFile);
        if(!freqFile.exists()){
            System.out.println("文件不存在，程序退出.");
            System.exit(2);
        }
        
        KNN inst=new KNN();
        File uvFile=new File("orisun/dvm2/unknown");
        inst.initUV(uvFile);
        File tvFiles=new File("orisun/dvm");
        inst.calDist(tvFiles);
        inst.knn(3);
    }
}