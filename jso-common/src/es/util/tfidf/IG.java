package es.util.tfidf;

/**
 * Author: Orisun
 * Date: Aug 29, 2011
 * FileName: IG.java
 * Function: calculate the information gain of each word in documents
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import es.util.FileUtil;

public class IG {
    // matrix记录各个词在各个文档中出现过几次
    public static HashMap<String, ArrayList<Short>> matrix = new HashMap<String, ArrayList<Short>>();
    HashMap<String, Double> map = new HashMap<String, Double>(); // 存储每个单词的信息增益值

    //参数文件存储word-doc矩阵
    public static void initMatrix(File srcFile,int docNumber) {
        if (!(srcFile.exists() && srcFile.isFile())) {
            System.out.println("Matrix文件不存在.程序退出.");
            System.exit(2);
        }
        try {
            FileReader fr = new FileReader(srcFile);
            BufferedReader br=new BufferedReader(fr);                    
            String line=null;
            while((line=br.readLine())!=null){        
                //System.out.println(line);
                String[] content=line.split("\\s+");
                String word=content[0];
                //System.out.print(word+"\t");
                ArrayList<Short> al=new ArrayList<Short>(docNumber);
                for(int i=0;i<docNumber;i++){
                    short count=Short.parseShort(content[i+1]);
                    //System.out.print(count+"\t");
                    al.add(count);
                }
                //System.out.println();
                matrix.put(word, al);
            }
            //System.out.println("word-doc矩阵的行数："+matrix.size());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //参数文件是文件夹，其中的文件分别存储了文档中各个单词出现的次数
    public void calIG(int docNumber ,String matrixFilePath,int classNumber) {
        initMatrix(new File(matrixFilePath),docNumber);
        Iterator<Entry<String, ArrayList<Short>>> iter = matrix.entrySet().iterator();
        double entropy = Math.log(classNumber);
        while (iter.hasNext()) {
            Entry<String, ArrayList<Short>> entry = iter.next();
            String word = entry.getKey();
            ArrayList<Short> al = entry.getValue();
            int category = docNumber / classNumber;
            int wcount = 0; // 出现word的文档的文档数量
            int[] wcount_class = new int[classNumber];// 每个类别中出现单词word的文档数
            double pw = 0.0; // 出现word的文档占全部文档的比重
            double[] pcw = new double[classNumber]; // 在单词word出现时各个类别所占的比重
            double[] pcw_b = new double[classNumber]; // 在单词word不出现时各个类别所占的比重
            for (int i = 0; i < classNumber; i++) {
                for (int j = 0; j < category; j++) {
                    if (al.get(j + i * category) > 0) {
                        wcount_class[i]++;
                    }
                }
                wcount += wcount_class[i];
            }
            pw = 1.0 * wcount / docNumber;
            for (int i = 0; i < classNumber; i++) {
                pcw[i] = 1.0 * wcount_class[i] / wcount;
                pcw_b[i] = 1.0 * (category - wcount_class[i])
                        / (docNumber- wcount);
            }
            double d1 = 0.0;
            double d2 = 0.0;
            for (int i = 0; i < classNumber; i++) {
                d1 += pcw[i] * Math.log(pcw[i] + Double.MIN_VALUE);
                d2 += pcw_b[i] * Math.log(pcw_b[i] + Double.MIN_VALUE);
            }
            double ig = entropy + pw * d1 + (1.0 - pw) * d2;
            map.put(word, ig);
            System.out.printf(word+": %.20f \n",ig);
        }
    }
    
    public static void main(String[] args){
        IG inst=new IG();
        File matrixFile=new File("orisun/matrix");
        initMatrix(matrixFile,6);
        //new featureselect.WordDocMatrix().printMatrix(System.out, matrix);
        inst.calIG(6,"orisun/matrix",2);
    }
}
