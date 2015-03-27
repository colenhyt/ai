package cn.hd.util;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.InputStream;  

import org.apache.tools.zip.ZipOutputStream;
  /**
   * 
   * <p>Title: ZipTools.java</p>
   * 
   * <p>Description: zip解压</p>
   * 
   * <p>Date: May 2, 2013</p>
   * 
   * <p>Time: 8:56:36 PM</p>
   * 
   * <p>Copyright: 2013</p>
   * 
   * <p>Company: hd</p>
   * 
   * @author hd
   * @version 1.0
   * 
   * <p>============================================</p>
   * <p>Modification History
   * <p>Mender: </p>
   * <p>Date: </p>
   * <p>Reason: </p>
   * <p>============================================</p>
   */
  
public class ZipTools {  
	public static void  createDirectory(String directory, String subDirectory) {
	    String dir[];
	    File fl = new File(directory);
	      if (subDirectory == "" && fl.exists() != true)
	        fl.mkdir();
	      else if (subDirectory != "") {
	        dir = subDirectory.replace("//", "/").split("/");
	        for (int i = 0; i < dir.length; i++) {
	          File subFile = new File(directory + File.separator + dir[i]);
	          if (subFile.exists() == false)
	            subFile.mkdir();
	          directory += File.separator + dir[i];
	        }
	      }
	  }

	public static  void unZip(String zipFileName, String outputDirectory) throws Exception {
	      org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(zipFileName);
	      java.util.Enumeration e = zipFile.getEntries();
	      org.apache.tools.zip.ZipEntry zipEntry = null;
	      createDirectory(outputDirectory, "");
	      while (e.hasMoreElements()) {
	        zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
	        if (zipEntry.isDirectory()) {
	          String name = zipEntry.getName();
	          name = name.substring(0, name.length() - 1);
	          File f = new File(outputDirectory + File.separator + name);
	          f.mkdir();
	        }
	        else {
	          String fileName = zipEntry.getName();
	          fileName = fileName.replace("//", "/");
	          if (fileName.indexOf("/") != -1)
	          {
	              createDirectory(outputDirectory,
	                              fileName.substring(0, fileName.lastIndexOf("/")));
	              fileName=fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
	          }

	                   File f = new File(outputDirectory + File.separator + zipEntry.getName());

	          f.createNewFile();
	          InputStream in = zipFile.getInputStream(zipEntry);
	          FileOutputStream out=new FileOutputStream(f);

	          byte[] by = new byte[1024];
	          int c;
	          while ( (c = in.read(by)) != -1) {
	            out.write(by, 0, c);
	          }
	          out.close();
	          in.close();
	        }
	      }
	        
	    }
	/*
	    * inputFileName 输入一个文件夹
	    * zipFileName 输出一个压缩文件夹
	    */
	    public static void zip(String inputFileName,String zipfile) throws Exception {
	        String zipFileName = zipfile; //打包后文件名字
	        System.out.println(zipFileName);
	        zip(zipFileName, new File(inputFileName));
	    }

	    private static void zip(String zipFileName, File inputFile) throws Exception {
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
	        zip(out, inputFile, "");
	        System.out.println("zip done");
	        out.close();
	    }

	    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
	        if (f.isDirectory()) {
	           File[] fl = f.listFiles();
	           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
	           base = base.length() == 0 ? "" : base + "/";
	           for (int i = 0; i < fl.length; i++) {
	           zip(out, fl[i], base + fl[i].getName());
	         }
	        }else {
	           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
	           FileInputStream in = new FileInputStream(f);
	           int b;
	           System.out.println(base);
	           while ( (b = in.read()) != -1) {
	            out.write(b);
	         }
	         in.close();
	       }
	    }

    public static void main(String[] args) {
    	  try {  
              //ZipTools.unZip("d:/test.zip","d:/up/");  
              zip("d:/up/", "d:/test11.zip");
          } catch (Exception e) {  
              e.printStackTrace();  
          } 
	}
}  