package es.util.tfidf;

/**
 * Author: Orisun
 * Date: Aug 29, 2011
 * FileName: FS.java
 * Function: feature select with information gain
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Comparator;

import es.util.FileUtil;

public class FS {
	private String freqFilesPath;
    public static ArrayList<String> features=new ArrayList<String>();//存放最终选择的特征词
    
    //选择信息增益值最大的n个单词作为特征项
    public void featureSelect(String matrixPath,int n,int classNumber){
        IG inst=new IG();
        List<File> freqs = FileUtil.getFiles(freqFilesPath);
        int docnumber = freqs.size();        
        inst.calIG(docnumber,matrixPath,classNumber);
        ArrayList<Entry<String,Double>> list=sort(inst.map);
        Iterator<Entry<String,Double>> iter=list.iterator();
        int index=0;
        while(index++<n && iter.hasNext()){
            Entry<String,Double> entry=iter.next();
//            System.out.println(entry.getKey()+"  "+entry.getValue());
            features.add(entry.getKey());
        }
    }
    
    //Map按value进行排序
    public ArrayList<Entry<String,Double>> sort(HashMap<String,Double> arg){
        ArrayList<Entry<String,Double>> al=new ArrayList<Entry<String,Double>>(arg.entrySet());
        Collections.sort(al, new Comparator<Map.Entry<String,Double>>(){
            public int compare(Map.Entry<String, Double> o1,Map.Entry<String,Double> o2){
                double res=o2.getValue()-o1.getValue();
                if(res<0)
                    return -1;
                else if(res>0)
                    return 1;
                else
                    return 0;
        }
        });
        return al;
    }
    
    public void createFeatures(String _freqFilesPath,String matrixPath,String featurePath){
    	this.freqFilesPath = _freqFilesPath;

        featureSelect(matrixPath,30,28);
        try{    
            File file=new File(featurePath);
            file.createNewFile();
            FileWriter fw=new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            Iterator<String> iter=FS.features.iterator();
            while(iter.hasNext()){
                String feature=iter.next();
                bw.write(feature);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }    	
    }
    
    //把最终选择的特征词存入文件
    public static void main(String[] args){
        FS inst=new FS();
        inst.createFeatures("orisun/frequency", "orisun/matrix","orisun/features");
    }
}
