package es.util.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import easyshop.downloadhelper.OriHttpPage;
import easyshop.model.ModelConstants;
import es.util.pattern.ESPattern;
import es.util.url.URLStrHelper;


public class FileUtils {
    public static String root;
    
    public static boolean copyFile(String from,String to){
	
	  File fromFile,toFile;
	
	  fromFile = new File(from);
	
	  toFile = new File(to);
	
	  return copyFile(fromFile,toFile);
	  }

	public static boolean copyFile(File fromFile,File toFile){
	
	  FileInputStream fis = null;
	
	  FileOutputStream fos = null;
	
	  try{
	
	if (!toFile.exists())
		toFile.createNewFile();
	
	  fis = new FileInputStream(fromFile);
	
	  fos = new FileOutputStream(toFile);
	
	  int bytesRead;
	
	  byte[] buf = new byte[4 * 1024];// 4K buffer
	
	  while((bytesRead=fis.read(buf))!=-1){
	
	  fos.write(buf,0,bytesRead);
	
	  }
	
	  fos.flush();
	
	  fos.close();
	
	  fis.close();
	
	  }catch(IOException e){
	
	  System.out.println(e);
	
	  return false;
	
	  }
	
	  return true;
	
	  }

	public static int copyDirectory(File fromDirect,File toDirect,boolean cut){
		int count=0;
 			try {
 				if (!fromDirect.exists())
 					System.out.println("原路径不存在"+fromDirect.getAbsolutePath());
 				
 			   	if (!toDirect.exists())
				toDirect.createNewFile();
 			   	
 			   	File[] files=fromDirect.listFiles();
 			   count=files.length;
 			   	for (int i=0;i<files.length;i++){
 			   		copyFile(files[i],new File(toDirect,files[i].getName()));
 			   		if (cut)
 			   			files[i].delete();
 			   	}
 			   	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	return count;

    	  }
    
    public static void setRoot(String root) {
		FileUtils.root = root;
	}

    public void processPictures(String oldPath){
    	File old=new File(oldPath);
    	File[] pictures=old.listFiles();
    	
    }
    public File getDirectory(String path){
    	File f=new File(root,path);
    	if (!f.exists())
    		f.mkdirs();
    	return f;
    }
    
    public static void main(String[] args){
    	File f=new File("d:/","/a/b/c");
    	f.mkdirs();    	
    }
	public static File getDirectory(String siteId,String type){
        File direct=new File(root,siteId);
        if (!direct.exists())
            direct.mkdir();       
        File direct2=new File(direct,type);
        if (!direct2.exists())
            direct2.mkdir();       
            return direct2;
    }
    
    public static File getFile(String parent,String child){
        File direct=new File(parent,child);
        if (!direct.exists())
            try {
                direct.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }       
            return direct;
    }
    
    public static boolean writeToFile(String context,File file){
        return writeToFile(context.getBytes(),file);
        
    }
    
    
    public static boolean writeToFile(byte[] content,File file){
    	FileOutputStream out=null;
        try {
            out=new FileOutputStream(file);
            out.write(content);
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
        	
        }
        return false;
        
    }    
        
    public static void serialize(Set httpPages,String path){
    	for (Iterator it=httpPages.iterator();it.hasNext();){
    		OriHttpPage page=(OriHttpPage)it.next();
    		serialize(page,new File(path));
    	}
    }
    public static void serialize(byte[] bytes,String siteId,String urlkey,String urlStr,int type){
        
        File path=getDirectory(siteId,String.valueOf(type));
        int pageType=URLStrHelper.getFileType(urlStr);
        String postFix="htm";
        if (pageType==ModelConstants.PAGE_TYPE_JPG)
        	postFix="jpg";
        else if (pageType==ModelConstants.PAGE_TYPE_GIF)
        	postFix="gif";
        String pageName=urlkey+"."+postFix;
        File item=getFile(path.getAbsolutePath(),pageName);
        writeToFile(bytes,item);    	
    }   
    
    public static void serialize(OriHttpPage oPage,File path){
        
        int pageType=URLStrHelper.getFileType(oPage.getUrlStr());
        String postFix="htm";
        if (pageType==ModelConstants.PAGE_TYPE_JPG)
        	postFix="jpg";
        else if (pageType==ModelConstants.PAGE_TYPE_GIF)
        	postFix="gif";
        String pageName=oPage.getUrlKey()+"."+postFix;
//        File parent=path.getParentFile();
        if (!path.exists())
        	path.mkdirs();
        File item=getFile(path.getAbsolutePath(),pageName);
        writeToFile(oPage.getContent(),item);    
    }

	public static void serialize(OriHttpPage oPage,String siteId,int type,String pageName){
		    
		    File path=getDirectory(siteId,String.valueOf(type));
		    
		    if (pageName!=null){
		    File file=getFile(path.getAbsolutePath(),pageName);
		    writeToFile(oPage.getContent(),file);    	
		    }
		}

	public static void serialize(OriHttpPage oPage,String siteId,int type){
		    
		    File path=getDirectory(siteId,String.valueOf(type));
		    int pageType=URLStrHelper.getFileType(oPage.getUrlStr());
		    String postFix="htm";
		    if (pageType==ModelConstants.PAGE_TYPE_JPG)
		    	postFix="jpg";
		    else if (pageType==ModelConstants.PAGE_TYPE_GIF)
		    	postFix="gif";
		    String pageName=null;
		    if (oPage.getUrlKey().equals("-1"))
	    		pageName=oPage.getRelWord()+"."+postFix;
		    else if (ESPattern.isNumber(oPage.getUrlKey()))
	    		pageName=oPage.getUrlKey()+"."+postFix;
		    
		    if (pageName!=null){
		    File file=getFile(path.getAbsolutePath(),pageName);
		    writeToFile(oPage.getContent(),file);    	
		    if (siteId.startsWith("joyo")){
		    	try{
		    		File wpath=getDirectory(siteId,"joyoerrors");
		    		File bigpath=getDirectory(siteId,"102");
				  BufferedImage bi;
					bi = ImageIO.read(file);	
					long klen=file.length()/1024;
	//				if (bi.getHeight()==90&&bi.getWidth()==70&&bi.getType()==13){
						if (bi!=null&&bi.getHeight()==90&&bi.getWidth()==70&&(bi.getType()==13||bi.getType()==5)){
						oPage.setDownloadStatus(-11);
						boolean copy=copyFile(file.getAbsolutePath(),wpath+"\\"+file.getName());
						if (copy) file.delete();
					}else if (klen>=119){
						//大图片:
						oPage.setDownloadStatus(-11);
						boolean copy=copyFile(file.getAbsolutePath(),bigpath+"\\"+file.getName());
						if (copy) file.delete();
					}else
						oPage.setDownloadStatus(oPage.getResponse().getCode());
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    }else if (siteId.equals("dangdang")){
		    	try{
		    		File wpath=getDirectory(siteId,"dderrors");
				  BufferedImage bi;
					bi = ImageIO.read(file);	
					if (bi.getWidth()==140&&bi.getHeight()==200){
						oPage.setDownloadStatus(-11);
						boolean copy=copyFile(file.getAbsolutePath(),wpath+"\\"+file.getName());
						if (copy) file.delete();
					}else
						oPage.setDownloadStatus(oPage.getResponse().getCode());
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    }else
		    	oPage.setDownloadStatus(oPage.getResponse().getCode());
		    }
		}
}
