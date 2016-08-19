package es.util.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalIG {

    public void calIG(File matrixFile,File IGFile) {
        if (!matrixFile.exists()) {
            System.out.println("Matrix文件不存在.程序退出.");
            System.exit(2);
        }

        int category_num = 9;    //一共有9大分类
        int doc_num=7196;//总共有7196篇文档，也是word-doc矩阵的列数
        int[] category_count={1070,440,513,816,750,756,1392,473,986};    //每个分类包含的文档数
        
        double HC=getEntropy(category_count);
        
        try {
            FileReader fr = new FileReader(matrixFile);
            BufferedReader br = new BufferedReader(fr);
            PrintWriter pw=new PrintWriter(new FileOutputStream(IGFile));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] content = line.split("\\s+");
                String term = content[0];
                ArrayList<Short> al = new ArrayList<Short>(doc_num);    
                for (int i = 0; i < doc_num; i++) {
                    short count = Short.parseShort(content[i + 1]);
                    al.add(count);
                }
                
                int term_count = 0; // 出现term的文档数量
                int[] term_class_count = new int[category_num];// 每个类别中出现term的文档数量
                int[] term_b_class_count = new int[category_num];// 每个类别中不出现term的文档数量
                int index=0;
                for (int i = 0; i < category_num; i++) {
                    for (int j = 0; j < category_count[i]; j++) {
                        if (al.get(index) > 0) {
                            term_class_count[i]++;
                        }
                        index++;
                    }
                    term_b_class_count[i]= category_count[i]-term_class_count[i];
                    term_count += term_class_count[i];
                }
                
                double HCT=1.0*term_count/doc_num*getEntropy(term_class_count)+1.0*(doc_num-term_count)/doc_num*getEntropy(term_b_class_count);
//                double IG = HC - HCT;

//                pw.println(term+"\t"+String.valueOf(IG));
                pw.flush();
            }
            br.close();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public double getEntropy(int[] arr){
        int sum=0;
        double entropy=0.0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
            entropy+=arr[i]*Math.log(arr[i]+Double.MIN_VALUE)/Math.log(2);
        }
        entropy/=sum;
        entropy-=Math.log(sum)/Math.log(2);
        return 0-entropy;
    }

    public static void main(String[] args) throws Exception{
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Begin Time: "+formatter.format(currentTime));
        
        CalIG inst=new CalIG();
        File in=new File("/home/orisun/matrix/part-r-00000");
        File out=new File("/home/orisun/frequency1");
        inst.calIG(in, out);
        
        currentTime = new Date();
        System.out.println("End Time: "+formatter.format(currentTime));
    }
}