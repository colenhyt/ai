package cn.hd.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * 
 * <p>Title: XMLUtil.java</p>
 * 
 * <p>Description: xml工具类</p>
 * 
 * <p>Date: May 9, 2013</p>
 * 
 * <p>Time: 8:34:46 PM</p>
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
public class XMLUtil {

	/**
	 * 生成xml文件
	 * @param document
	 * @param filepath
	 */
	public static void writeFile(Document document,String filepath){
		XMLWriter out = null;

		BufferedWriter bw = null;
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			File xmlFile = new File(filepath);//输出xml的路径
			fos = new FileOutputStream(xmlFile);
			osw = new OutputStreamWriter(fos, "UTF-8");//指定编码，防止写中文乱码
			bw = new BufferedWriter(osw);

			//对xml输出格式化
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			out = new XMLWriter(bw, format);
			out.write(document);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (bw != null) {
					bw.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
