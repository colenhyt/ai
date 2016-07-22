package box.site.parser.sites;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class ImgGetter {
	protected Logger  log = Logger.getLogger(getClass());
	
	public static String filePath(String sitekey,String httpUrl){
		String fileName = httpUrl.substring(httpUrl.lastIndexOf("/"));
		fileName = httpUrl.hashCode()+ fileName.substring(fileName.lastIndexOf("."));
		return "pics/"+sitekey+"/"+fileName;		
	}
	
	public void getHtmlPicture(String sitekey,String httpUrl) {
		URL url;
		BufferedInputStream in;
		FileOutputStream file;
		try {
			String path = ImgGetter.filePath(sitekey,httpUrl);
			
			File ff = new File(path);
			if (ff.exists()){
//				log.warn("图片已存在"+httpUrl);
				return;
			}
			
			url = new URL(httpUrl);

			in = new BufferedInputStream(url.openStream());

			
			FileUtil.checkAndMakeParentDirecotry(path);
			
			file = new FileOutputStream(ff);
			int t;
			while ((t = in.read()) != -1) {
				file.write(t);
			}
			file.close();
			in.close();
			log.warn("图片获取成功:"+path);
		} catch (MalformedURLException e) {
			log.warn("图片获取失败:"+e.getMessage());
		} catch (FileNotFoundException e) {
			log.warn("图片获取失败:"+e.getMessage());
		} catch (IOException e) {
			log.warn("图片获取失败:"+e.getMessage());
		}
	}

	public static Set<String> findImgUrls(String url,String content){
		String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		String searchImgReg2 = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";

		Set<String> imgUrls = new HashSet<String>();
		Pattern pattern = Pattern.compile(searchImgReg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			//System.out.println(matcher.group(3));
			imgUrls.add(matcher.group(3));
		}

		pattern = Pattern.compile(searchImgReg2);
		matcher = pattern.matcher(content);
		while (matcher.find()) {
			//System.out.println(matcher.group(3));
			imgUrls.add(matcher.group(3));
		}
		return imgUrls;
	}
	
	public void get(String url, String content)  {
		Set<String> imgurls = findImgUrls(url,content);
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		for (String imgUrl:imgurls){
			if (imgUrl.indexOf("http")<0){
				String urlHead = url.substring(0,url.lastIndexOf("/"));
				imgUrl = urlHead+imgUrl;
				
			}
			this.getHtmlPicture(sitekey,imgUrl);
		}
	}

	public static void main(String[] args) throws IOException {
		ImgGetter gg = new ImgGetter();
		String cc = "abc";
		gg.get("ee", cc);
		System.out.println(cc);
	}
}