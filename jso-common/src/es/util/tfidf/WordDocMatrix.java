package es.util.tfidf;

/**
 * Author: Orisun
 * Date: Aug 29, 2011
 * FileName: WordDocMatrix.java
 * Function: word-document matrix denote each word frequency in each document.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import es.util.FileUtil;

public class WordDocMatrix {

    // 训练集分为3类，每类各1000个文档
    public int docnumber = 6; 
    //matrix记录各个词在各个文档中出现过几次
    HashMap<String, ArrayList<Short>> matrix = new HashMap<String, ArrayList<Short>>();

    //参数文件是文件夹，其中的文件分别存储了文档中各个单词出现的次数
    private void buildMatrix(File srcFile) {
        if (srcFile.isDirectory()) {
            File[] children = srcFile.listFiles();
            for (File child : children) {
                buildMatrix(child);
            }
        } else if (srcFile.isFile()) {
            int filename=Integer.parseInt(srcFile.getName());
            try {
                FileReader fr = new FileReader(srcFile);
                BufferedReader br = new BufferedReader(fr);
                String line=null;
                while((line=br.readLine())!=null){
                    String[] pair=line.split("\\s+");
                    if(!matrix.containsKey(pair[0])){
                        ArrayList<Short> al=new ArrayList<Short>(docnumber);
                        for(int i=0;i<docnumber;i++)
                            al.add((short)0);
                        System.out.println(filename+":"+pair[1]);
                        al.set(filename, Short.parseShort(pair[1]));
                        matrix.put(pair[0], al);
                    }else{
                        ArrayList<Short> al=matrix.get(pair[0]);
                        short orig=al.get(filename);
                        al.set(filename, (short)(orig+Short.parseShort(pair[1])));
                    }
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createMatrix(String freqFilesPath,String matrixPath){
        try{
            File Mfile=new File(freqFilesPath);
            if (!Mfile.exists()) {
                System.out.println("文件不存在，程序退出.");
                System.exit(2);
            }
            List<File> freqs = FileUtil.getFiles(freqFilesPath);
            this.docnumber = freqs.size();
            buildMatrix(Mfile);
            File file=new File(matrixPath);
            file.createNewFile();
            PrintStream ps=new PrintStream(file);
            printMatrix(ps,matrix);
//            inst.printMatrix(System.out,inst.matrix);
        } catch (Exception e) {
            e.printStackTrace();
        }   	
    }
    
    //打印矩阵的前几行，输出到文件，以作验证（如果全部打印文件会因太大而加载过慢，甚至可能打不开）
    public static void main(String[] args){
        WordDocMatrix inst=new WordDocMatrix();
        inst.createMatrix("orisun/frequency", "orisun/matrix");
    }
    
    //输出matrix
    private void printMatrix(PrintStream out,HashMap<String, ArrayList<Short>> matrix){
        Iterator<Entry<String, ArrayList<Short>>> iter=matrix.entrySet().iterator();
        try{
        while(iter.hasNext()){
            Entry<String, ArrayList<Short>> entry=iter.next();
            out.print(entry.getKey());
            out.print("\t");
            for(int i=0;i<docnumber;i++){
                out.print(String.valueOf(entry.getValue().get(i)));
                out.print("\t");
            }
            out.println();
        }
        out.flush();
        out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
