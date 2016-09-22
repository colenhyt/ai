package es.util.tfidf;

/**
 * Author: Orisun
 * Date: Sep 3, 2011
 * FileName: FeatureSelect.java
 * Function: 读取word-doc矩阵，计算每个词的信息增益值，排序。输出IG最大的前300个特征项。
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import es.util.FileUtil;

public class FeatureSelect {
    private Environment env;
    protected Database database; // 用来存放url队列的数据库
    protected Database catalogdatabase; // 用来创建StoredClassCatalog实例的数据库
    private static final String CLASS_CATALOG = "java_class_catalog"; // catalogdatabase的数据库名
    protected StoredClassCatalog javaCatalog; // StoredClassCatalog实例用来序列化对象
    StoredMap<String, Double> FeaDB = null;

    public FeatureSelect(String homeDirectory) {
        EnvironmentConfig envConfig = new EnvironmentConfig(); // 环境配置
        envConfig.setTransactional(true); // 允许事务
        envConfig.setAllowCreate(true); // 当环境配置不存在时就创建
        env = new Environment(new File(homeDirectory), envConfig); // 创建环境

        DatabaseConfig dbConfig0 = new DatabaseConfig(); // 数据库配置
        dbConfig0.setTransactional(true); // 允许事务
        dbConfig0.setAllowCreate(true); // 当数据库不存在时就创建
        catalogdatabase = env.openDatabase(null, CLASS_CATALOG, dbConfig0);
        javaCatalog = new StoredClassCatalog(catalogdatabase);
        
        DatabaseConfig dbConfig = new DatabaseConfig(); // 数据库配置
        dbConfig.setTransactional(true); // 允许事务
        dbConfig.setAllowCreate(true); // 当数据库不存在时就创建
        database = env.openDatabase(null, "URL", dbConfig); // 打开数据库
        
        EntryBinding<String> keyBinding = new SerialBinding<String>(
                javaCatalog, String.class);
        EntryBinding<Double> valueBinding = new SerialBinding<Double>(
                javaCatalog, Double.class);
        FeaDB = new StoredMap<String, Double>(database, keyBinding,
                valueBinding, true);
    }

    public void close() throws DatabaseException {
        database.close(); // 关闭存放url的数据库
        javaCatalog.close(); // 关闭用来序列化对象的javaCatalog类
        env.close(); // 关闭环境
    }

    public void calIG(File matrixFile,int docNumber,int classNumber) {
        if (!matrixFile.exists()) {
            System.out.println("Matrix文件不存在.程序退出.");
            System.exit(2);
        }

        double entropy = Math.log(classNumber);
        try {
            FileReader fr = new FileReader(matrixFile);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] content = line.split("\\s+");
                String word = content[0];
                ArrayList<Short> al = new ArrayList<Short>(docNumber);
                for (int i = 0; i < docNumber; i++) {
                    short count = Short.parseShort(content[i + 1]);
                    al.add(count);
                }
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
                            / (docNumber - wcount);
                }
                double d1 = 0.0;
                double d2 = 0.0;
                for (int i = 0; i < classNumber; i++) {
                    d1 += pcw[i] * Math.log(pcw[i] + Double.MIN_VALUE);
                    d2 += pcw_b[i] * Math.log(pcw_b[i] + Double.MIN_VALUE);
                }
                double ig = entropy + pw * d1 + (1.0 - pw) * d2;
                FeaDB.put(word, ig);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Map按value进行排序(从大到小)
    public ArrayList<Entry<String, Double>> sort() {
        ArrayList<Entry<String, Double>> al = new ArrayList<Entry<String, Double>>();
        //从数据库中读取数据
        if(!FeaDB.isEmpty()){
            Iterator<Entry<String,Double>> iter=FeaDB.entrySet().iterator();
            while(iter.hasNext()){
                Entry<String,Double> entry=iter.next();
                al.add(entry);
            }
        }
        Collections.sort(al, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                    Map.Entry<String, Double> o2) {
            	return o2.getValue().compareTo(o1.getValue());
//            	return o2.getValue()==o1.getValue()?0:(o2.getValue()>o1.getValue()?1:-1);
//                double res = o2.getValue() - o1.getValue();
//                if (res < 0)
//                    return -1;
//                else if (res > 0)
//                    return 1;
//                else
//                    return 0;
            }
        });
        return al;
    }

    public void createFeatures(String _freqFilesPath,String matrixPath,String featurePath){
    	List<File> freqs = FileUtil.getFiles(_freqFilesPath);
        int docnumber = freqs.size();  
      calIG(new File(matrixPath),docnumber,28);
      
      ArrayList<Entry<String, Double>> al = sort();
      close();
      Iterator<Entry<String, Double>> iter = al.iterator();
      int n = 0;
      int number = 30;
      File file = new File(featurePath);
      try {
          file.createNewFile();
          FileWriter fw = new FileWriter(file);
          BufferedWriter bw = new BufferedWriter(fw);
          while (iter.hasNext() && n++ < number) {
              Entry<String, Double> entry = iter.next();
              bw.write(entry.getKey() + "\t");
              bw.write(String.valueOf(entry.getValue()));
              bw.newLine();
          }
          bw.flush();
          bw.close();
      } catch (Exception e) {
          e.printStackTrace();
      }    	
    }
    
    public static void main(String[] args) throws Exception{
        FeatureSelect fs = new FeatureSelect("/home/orisun/develop/workspace");
//        fs.calIG(new File("/home/orisun/matrix/part-r-00000"));
//        ArrayList<Entry<String, Double>> al = fs.sort();
//        fs.close();
//        Iterator<Entry<String, Double>> iter = al.iterator();
//        int n = 0;
//        File file = new File("/home/orisun/features");
//        try {
//            file.createNewFile();
//            FileWriter fw = new FileWriter(file);
//            BufferedWriter bw = new BufferedWriter(fw);
//            while (iter.hasNext() && n++ < 300) {
//                Entry<String, Double> entry = iter.next();
//                bw.write(entry.getKey() + "\t");
//                bw.write(String.valueOf(entry.getValue()));
//                bw.newLine();
//            }
//            bw.flush();
//            bw.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}