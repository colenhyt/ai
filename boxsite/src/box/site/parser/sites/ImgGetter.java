package box.site.parser.sites;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hd.util.FileUtil;
import es.util.url.URLStrHelper;

public class ImgGetter {
	public static String filePath(String httpUrl){
		String fileName = httpUrl.substring(httpUrl.lastIndexOf("/"));
		fileName = httpUrl.hashCode()+ fileName.substring(fileName.lastIndexOf("."));
		String domainName = URLStrHelper.getHost(httpUrl).toLowerCase();
		return "pics/"+domainName+"/"+fileName;		
	}
	
	public void getHtmlPicture(String httpUrl) {
		URL url;
		BufferedInputStream in;
		FileOutputStream file;
		try {
			String path = ImgGetter.filePath(httpUrl);
			
			File ff = new File(path);
			if (ff.exists()){
				System.out.println("图片已存在"+httpUrl);
				return;
			}
			
			System.out.println("取网络图片");

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
			System.out.println("图片获取成功:"+path);
		} catch (MalformedURLException e) {
			System.out.println("图片获取失败:"+e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("图片获取失败:"+e.getMessage());
		} catch (IOException e) {
			System.out.println("图片获取失败:"+e.getMessage());
		}
	}

	public static Set<String> findImgUrls(String url,String content){
		String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		String searchImgReg2 = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";

		Set<String> imgUrls = new HashSet<String>();
		Pattern pattern = Pattern.compile(searchImgReg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(3));
			imgUrls.add(matcher.group(3));
		}

		pattern = Pattern.compile(searchImgReg2);
		matcher = pattern.matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(3));
			imgUrls.add(matcher.group(3));
		}
		return imgUrls;
	}
	
	public void get(String url, String content)  {
		Set<String> imgurls = findImgUrls(url,content);
		for (String imgUrl:imgurls){
			if (imgUrl.indexOf("http")<0){
				String urlHead = url.substring(0,url.lastIndexOf("/"));
				imgUrl = urlHead+imgUrl;
				
			}
			this.getHtmlPicture(imgUrl);
		}
	}

	public static void main(String[] args) throws IOException {
		ImgGetter gg = new ImgGetter();
		String cc = "abc";
		gg.get("ee", cc);
		System.out.println(cc);
	}
}