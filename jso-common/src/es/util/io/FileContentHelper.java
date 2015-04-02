/*
 * Created on 2005-3-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import easyshop.downloadhelper.HttpPageGetter;
import es.Constants;
import es.webref.model.JSPageRef;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileContentHelper {
	private final static int DEFAULT_FILE_SIZE=10240;
	
	public static byte[] getContent(String fileName){
		return getContent(fileName,DEFAULT_FILE_SIZE);
	}
	
	public static byte[] getContent(File f){
		FileInputStream fileBIS;
		try {
			fileBIS = new FileInputStream(f);
			return getContent(fileBIS,DEFAULT_FILE_SIZE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	    
	}
	
	public static InputStream getInputStream(String fileName){
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static byte[] getContent(String fileName, int size){
		FileInputStream fileBIS;
		try {
			fileBIS = new FileInputStream(fileName);
			return getContent(fileBIS,size);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] getContent(InputStream in){
		return getContent(in,DEFAULT_FILE_SIZE);
	}
	
	public static byte[] getContent(InputStream in,int size){
		try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            byte[] buf = new byte[1024];
            int bytesRead = 0;
            while(bytesRead >= 0)
            {
                baos.write(buf, 0, bytesRead);
                bytesRead = in.read(buf);
            }

            return baos.toByteArray();		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
						
	}
	
	public static String getStringContent(String fileName){
		return new String(getContent(fileName));
	}
	
	public static String getStringContent(URL urlStr){
		JSPageRef ref=new JSPageRef(urlStr.toExternalForm());
		byte[] content=new HttpPageGetter().getOriHttpPage(ref).getContent();
		try {
			return new String(content,Constants.CHARTSET_DEFAULT);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStringContent(File f){
		return new String(getContent(f));
	}
	
	public static String getStringContent(InputStream in){
		return new String(getContent(in));
	}
	
	
}
