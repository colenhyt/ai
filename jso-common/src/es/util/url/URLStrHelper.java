/*
 * Created on 2005-10-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import easyshop.model.ModelConstants;
import es.util.html.HTMLContentHelper;
import es.util.io.PageTypeResolver;
import es.util.pattern.ESPattern;


/**
 * @author Allenhuang
 * 
 * created on 2005-10-15
 */
public class URLStrHelper {
    public static final String URLStr_AND = "&";

    public static final String URLStr_QUOTE = "?";

    public static final String URLStr_EQUALS = "=";
    
    public static final String URLStr_COLON=";";

    public static final String URLStr_ANCHOR = "#";
    
    public static final String URL_HTTP_PREFIX = "http://";
    public static final String URL_HTTPS_PREFIX = "https://";
    public static final String URL_FTP_PREFIX = "ftp://";
    public static final String URL_FTPS_PREFIX = "ftps://";
    
    public static final String URL_INDEX="index";

    public static final String[] strs = { ",", ";" };

    public static String basicFilter(String oldStr) {     
        //去掉空格:
        oldStr=oldStr.replaceAll("[\u0020]{1,}", "");
        //去掉非法字符及后缀:
        for (int i = 0; i < strs.length; i++) {
            if (oldStr.indexOf(strs[i]) > 0)
                oldStr = oldStr.substring(0, oldStr.indexOf(strs[i]));
        }
        //单引号变为双引号:
        oldStr=oldStr.replaceAll("'","''");
        return oldStr;
    }
    
    public static String cutParameters(String oldStr, String[] params) {
        for (int i = 0; i < params.length; i++) {
            oldStr = cutParameter(oldStr, params[i]);
        }
        return oldStr;
    }
	
	/*定义urlstr的bran规则如下:
	 * 1. 动态网页,除了参数,其他url部分就是它的bran,比如http://www.a.com/a.asp?a=b&c=d
	 * bran为http://www.a.com/a.asp加上参数名
	 * 2. 静态网页,整个前缀,对于filename,假如是数字，或者数字加"-",数字加"_"，则很有可能是生成的精通
	 * 网页,这些可归为一类,通称branch为http://www.a.com/(d*)/html;
	 * 其他的静态网页,urlStr本身则是branch
	 * 3. 没有文件名的(目录),urlStr就是它bran
	 */
    private static String[] ddRegx={"[\\d]+","([\\d]+[-]*)+","([\\d]+[_]*)+"};
	public static String getUrlBran(String urlStr){
		String dir=getURLDir(urlStr);
		String post=getFilePostfix(urlStr);
		if (isStatic(post)){
			String name=getFileName(urlStr);
			if (ESPattern.matches1atLst(ddRegx,name))
				return dir+"/"+"d*/"+post;
		}else if (isDymatic(post)){
			Map params=getParams(urlStr);
			synchronized (params){
			if (params.size()>0){
				Set<String> names=params.keySet();
				String str="";
				for (Iterator it=names.iterator();it.hasNext();){
					str=str+(String)it.next()+"&";
				}
				return dir+"/"+getFullFileName(urlStr)+"?"+str;
			}else
				return dir+"/"+getFullFileName(urlStr);
			}
		}
		return urlStr;
	}
	
    public static String cutParameter(String oldStr, String paramName) {
        //参数只可能以两种情况出现:前面是URLStr_QUOTE或者前面是URLStr_AND:
    	oldStr=oldStr.toLowerCase();
        String newStr=oldStr;
        int sIndex,mIndex,eIndex;
        paramName=paramName.toLowerCase();
        sIndex=oldStr.toLowerCase().indexOf(URLStr_QUOTE+paramName+URLStr_EQUALS);
        if (sIndex>0){//属于第一个,
            int nIndex=oldStr.indexOf(URLStr_AND,sIndex);
            if (nIndex>0)//有其他参数
                newStr=oldStr.substring(0,sIndex+URLStr_QUOTE.length())+oldStr.substring(nIndex+1);
            else
                newStr=oldStr.substring(0,sIndex);
        }else{
            mIndex=oldStr.indexOf(URLStr_AND+paramName+URLStr_EQUALS);
            if (mIndex>0){
                int nIndex=oldStr.indexOf(URLStr_AND,mIndex+1);
                if (nIndex>0)//后面还有其他参数
                    newStr=oldStr.substring(0,mIndex)+oldStr.substring(nIndex);
                else
                    newStr=oldStr.substring(0,mIndex);
            }
     }
        

        return newStr;
    }
    
    public static String getFilePostfix(String urlStr){
    	urlStr=urlStr.toLowerCase();
    	if (urlStr.indexOf(".")<0)
    		return null;
    	
    	if (urlStr.indexOf(".asp")>0)
    		return "asp";    	
    	if (urlStr.indexOf(".jsp")>0)
    		return "jsp";
    	if (urlStr.indexOf(".html")>0)
    		return "html";
    	if (urlStr.indexOf(".htm")>0)
    		return "htm";
    	if (urlStr.indexOf(".php")>0)
    		return "php";
    	if (urlStr.indexOf(".shtml")>0)
    		return "shtml";
    	if (urlStr.indexOf(".shtm")>0)
    		return "shtm";
    	if (urlStr.indexOf(".aspx")>0)
    		return "aspx";
    	if (urlStr.indexOf(".xml")>0)
    		return "xml";
    	if (urlStr.indexOf(".jpg")>0)
    		return "jpg";
    	if (urlStr.indexOf(".xml")>0)
    		return "xml";
    	if (urlStr.indexOf(".gif")>0)
    		return "gif";
    	if (urlStr.indexOf(".dll")>0)
    		return "dll";
    	if (urlStr.endsWith("/")||urlStr.equalsIgnoreCase(getHostWithPre(urlStr)))
    		return "/";    
    	
    	return null;
    }
    
    public static boolean isStatic(String postFix){
    	if (postFix!=null){
    		if (postFix.equals("jpg"))
    			return false;
    		else if (postFix.equals("gif"))
    			return false;
    		else if (postFix.equals("asp")|postFix.equals("aspx"))
    			return false;
    		else if (postFix.equals("html")|postFix.equals("htm")|postFix.equals("shtml")|postFix.equals("shtm"))
    			return true;   		 
    		else if (postFix.equals("jsp"))
    			return false;
    		else if (postFix.equals("php"))
    			return false;
    		else if (postFix.equals("xml"))
    			return false;    		 
    		else if (postFix.equals("/"))
    			return false;    
    	}    
    	return false;
    }
    
    public static boolean isDymatic(String postFix){
    	if (postFix!=null){
    		if (postFix.equals("jpg"))
    			return false;
    		else if (postFix.equals("gif"))
    			return false;
    		else if (postFix.equals("asp")|postFix.equals("aspx"))
    			return true;
    		else if (postFix.equals("html")|postFix.equals("htm")|postFix.equals("shtml")|postFix.equals("shtm"))
    			return false;   		 
    		else if (postFix.equals("jsp"))
    			return true;
    		else if (postFix.equals("php"))
    			return true;
    		else if (postFix.equals("xml"))
    			return true;    		 
    		else if (postFix.equals("/"))
    			return false;    
    	}    
    	return false;
    }
    public static int getFileType(String urlStr){
    	String postFix=getFilePostfix(urlStr);
    	if (postFix!=null){
    		if (postFix.equals("jpg"))
    			return ModelConstants.PAGE_TYPE_JPG;
    		else if (postFix.equals("gif"))
    			return ModelConstants.PAGE_TYPE_GIF;
    		else if (postFix.equals("asp")|postFix.equals("aspx"))
    			return ModelConstants.PAGE_TYPE_ASP;
    		else if (postFix.equals("html")|postFix.equals("htm")|postFix.equals("shtml")|postFix.equals("shtm"))
    			return ModelConstants.PAGE_TYPE_HTML;   		 
    		else if (postFix.equals("jsp"))
    			return ModelConstants.PAGE_TYPE_JSP;
    		else if (postFix.equals("php"))
    			return ModelConstants.PAGE_TYPE_PHP;
    		else if (postFix.equals("xml"))
    			return ModelConstants.PAGE_TYPE_XML;    		 
    		else 
    			return ModelConstants.PAGE_TYPE_DIRECTORY;    
    	}
    	return ModelConstants.PAGE_TYPE_DIRECTORY;
    }
    
    //不包括后缀名的url文件名    
    public static String getFileName(String urlStr){
    	String file=null;
        List urlItems=URLConstructor.parse(urlStr);
        if (urlItems!=null&&urlItems.size()>0){
        	String lastStr=(String)urlItems.get(urlItems.size()-1);
        	int i=lastStr.indexOf(".");
        	if (i>0)
        		file=lastStr.substring(0,i);
        	else
        		file=lastStr;
        }
        return file;
    	
    }
    
    public static final String POST_COM_CN=".com.cn";
    public static final String POST_COM=".com";
    public static final String POST_CN=".cn";
    public static final String POST_NET_CN=".net.cn";
    public static final String POST_NET=".net";
    public static final String POST_CC=".cc";
    //取url的域名后缀,比如com,com.cn,cn,cc等
    public static String getHostPost(String hostStr){
    	if (hostStr!=null){
    		hostStr=hostStr.toLowerCase();
    		if (hostStr.endsWith(POST_COM_CN))
    			return POST_COM_CN;
    		else  if (hostStr.endsWith(POST_NET_CN))
    			return POST_NET_CN;
    		else  if (hostStr.endsWith(POST_NET))
    			return POST_NET;
    		else  if (hostStr.endsWith(POST_COM))
    			return POST_COM;
    		else  if (hostStr.endsWith(POST_CN))
    			return POST_CN;
    		else  if (hostStr.endsWith(POST_CC))
    			return POST_CC;    		
    	}
    	return null;
    }
    public static String getHostWithPre(String urlStr){
    	//"http://www.c.com/fdaf.jsp"返回"http://www.c.com"
        int i2=urlStr.indexOf("/",URL_HTTP_PREFIX.length());
        int i3=urlStr.indexOf("?",URL_HTTP_PREFIX.length());
    	if (i2>0&&i2>URL_HTTP_PREFIX.length())
    		return urlStr.substring(0,i2);
    	else if (i3>0&&i3>URL_HTTP_PREFIX.length())
    		return urlStr.substring(0,i3);
    	else
    		return urlStr;
    	
    }
    
    public static String getHost(String urlStr){
//    	http://www.c.com返回c.com
		String host=getHostStr(urlStr);
		String post=getHostPost(host);
		if (host!=null&&post!=null){
			String pre=host.substring(0,host.indexOf(post));
			int i=pre.lastIndexOf(".");
			if (i>0&&i<pre.length()-1)
				return pre.substring(i+1)+post;
			else
				return pre+post;
		}
		return null;
    }
    
    public static String getAnchorText(String refWord){
    	return HTMLContentHelper.getPureText(refWord);
    }
    
    public static String getProtocol(String urlStr){
    	String pre=null;
    	
    	urlStr=urlStr.trim();
    	if (urlStr.indexOf(URL_HTTPS_PREFIX)==0)
    		pre=URL_HTTPS_PREFIX;
    	else if (urlStr.indexOf(URL_HTTP_PREFIX)==0)
    		pre=URL_HTTP_PREFIX;
    	else if (urlStr.indexOf(URL_FTP_PREFIX)==0)
    		pre=URL_FTP_PREFIX;
    	else if (urlStr.indexOf(URL_FTPS_PREFIX)==0)
    		pre=URL_FTPS_PREFIX;
    	
    	return pre;
    }
    
    public static String getHostStr(String urlStr){
    	//http://www.c.com返回www.c.com
    	String pre=getProtocol(urlStr);
    	
    	if (pre!=null){
    		int i=urlStr.indexOf("/", pre.length());
    		int j=urlStr.indexOf("?",pre.length());
    		if (i>0&&i>pre.length())
    			return urlStr.substring(pre.length(),i);
    		else if (j>0&&j>pre.length())
    			return urlStr.substring(pre.length(),j);
    		else if (urlStr.length()>pre.length())
    			return urlStr.substring(pre.length());
    		else 
    			return null;
    	}else
    		return null;
    }
    
    //包括后缀名的url文件名
    public static String getFullFileName(String urlStr){
    	String file=null;
    	if (urlStr.endsWith("/"))
    		urlStr=urlStr.substring(0,urlStr.length()-1);
    	
    	int h=getHostWithPre(urlStr).length();
        int i=urlStr.lastIndexOf('/');
        
        if (h<=0)
        	return null;
//        System.out.println(urlStr.substring(h));
        int i2=urlStr.lastIndexOf('.');
//        System.out.println(urlStr.substring(i2));
        int i3=urlStr.indexOf("?");
        if (i3>h){
        	String s=urlStr.substring(0,i3);
        	int ss=s.lastIndexOf("/");
        	if (ss>h-1)
        		file=s.substring(ss+1);
        }else if (i>h-1){
        		file=urlStr.substring(i+1);
        }else
        	file=URL_INDEX;
        return file;
    	
    }    
    
    public static String getURLStr(String base,String urlStr){
    	if (base==null)
    		return urlStr;
    	
	    if (urlStr!=null){
	    	if (getProtocol(base)!=null&&getProtocol(urlStr)==null){
	    	try {
	    	if (base.indexOf("?")>0)
	    		base=base.substring(0,base.indexOf("?"));
	    	if (urlStr.startsWith("?"))
	    		return base+urlStr;
	    	else{
	    	URL url=new URL(base.trim());
	    	URL url2=new URL(url,urlStr);
			return url2.toExternalForm();
	    	}
			} catch (MalformedURLException e) {
				// log error here
				System.err.println("wrong urlstr "+urlStr);
			}}else {	                	
		        return urlStr;
			}
	
	    }
	    return null;
    }
    private static boolean isLink(String urlStr){
    	if (urlStr==null)
    		return false;
    	
    	urlStr=urlStr.trim().toLowerCase();
    	if (urlStr.startsWith("mailto")||urlStr.startsWith("javascript:")||urlStr.startsWith("mms:"))
    		return false;
    	
//    	if (getFileName(urlStr)==null)
//    		return false;
    	
    	return true;
    }
    
    public static boolean isSameURLDir(String urlStr1,String urlStr2){
    	String dir1=getURLDir(urlStr1);
    	String dir2=getURLDir(urlStr2);
    	return (dir1!=null&&dir2!=null&&dir1.equalsIgnoreCase(dir2));
    }
    
    public static String getURLDir(String urlStr){
    	String urlDir=null;
//    	List urlItems=URLConstructor.parse(urlStr);
//    	String[] odir=(String[])urlItems.toArray(new String[urlItems.size()]);
//    	if (odir.length>0){
//    		String[] dir=new String[odir.length-1];
//    		System.arraycopy(odir,0,dir,0,dir.length);
//    		urlDir=URLConstructor.HTTP_START+URLConstructor.compose(dir);
//    	}
    	if (urlStr!=null){
    		String protocol=URLStrHelper.getProtocol(urlStr);
    		if (protocol!=null&&urlStr.indexOf("/",protocol.length()+1)>0)
    			urlDir=urlStr.substring(0,urlStr.lastIndexOf("/"));
    		else
    			urlDir=urlStr;
    	}
    			
    	return urlDir;
    }

    public static Map getParams(String urlStr){
    	Map params=Collections.synchronizedMap(new HashMap());
    	int i=urlStr.indexOf("?");
    	if (i<=0)
    		return params;
    	
    	String paraStr=urlStr.substring(i+1);
    	String[] strs=paraStr.split("&");
    	synchronized(params){
    	for (int j=0;j<strs.length;j++){
    		int k=strs[j].indexOf("=");
    		if (k>0&&strs[j].length()>k){
    			String name=strs[j].substring(0,k);
    			String value=strs[j].substring(k+1);
    			if (name!=null&&name.trim().length()>0&&value!=null&&value.trim().length()>0)
    				params.put(name.toLowerCase(), value.toLowerCase());
    		}
    	}
    	}
    	
    	return params;
    	
    }
    
    public static String getParamValue(String urlStr, String paramName) {
    	//urlStr=urlStr.toLowerCase();
        int index = urlStr.indexOf(paramName + URLStr_EQUALS);
        int vIndex = index + paramName.length() + URLStr_EQUALS.length();

        //没有该参数:
        if (index < 0)
            return null;

        String preStr = urlStr.substring(index - 1, index);
        boolean b = preStr.equals(URLStr_QUOTE) | preStr.equals(URLStr_AND)|preStr.equals(URLStr_COLON);
        if (!b)
            return null;

        String paramValue = null;
        int i2 = urlStr.indexOf(URLStr_AND, vIndex);

        if (i2 > 0)//后面还有其他参数:
            paramValue = urlStr.substring(vIndex, i2);
        else
            paramValue = urlStr.substring(vIndex);

        return paramValue;
    }

    public static String cutUselessChas(String oldStr) {

        if (oldStr.endsWith(URLStr_QUOTE))
            oldStr = oldStr.substring(0, oldStr.length() - 1);
        if (oldStr.endsWith("/"))
            oldStr = oldStr.substring(0, oldStr.length() - 1);
        if (oldStr.endsWith(URLStr_QUOTE))
            oldStr = oldStr.substring(0, oldStr.length() - 1);
        if (oldStr.endsWith(URLStr_ANCHOR))
            oldStr = oldStr.substring(0, oldStr.length() - 1);
        if (oldStr.endsWith("/"))
            oldStr = oldStr.substring(0, oldStr.length() - 1);

        if (oldStr.indexOf(URLStr_ANCHOR) > 0
                && PageTypeResolver.getType(oldStr) == ModelConstants.PAGE_TYPE_ASP)
            oldStr = oldStr.substring(0, oldStr.indexOf(URLStr_ANCHOR));

        return oldStr;
    }

    public static String[] getParts(String urlStr){
    	List list=URLConstructor.parse(urlStr);
    	return (String[])list.toArray(new String[list.size()]);
    }
    
    //    private static
    public static String cutEndSuffix(String oldStr, String endSuffix) {
    	if (oldStr.indexOf(endSuffix)>0)
        return oldStr.substring(0, oldStr.indexOf(endSuffix)+ endSuffix.length());
    	return oldStr;
    }

	public static String legalUrl(String parent,String urlStr){
		if (!isLink(urlStr))
			return null;		
	    return getURLStr(parent, urlStr);
	}

	   //转换为%E4%BD%A0形式
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }	

    //将%E4%BD%A0转换为汉字 
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
            /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
            case '%':
                ch = s.charAt(++i);
                int hb = (Character.isDigit((char) ch) ? ch - '0'
                        : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                ch = s.charAt(++i);
                int lb = (Character.isDigit((char) ch) ? ch - '0'
                        : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                b = (hb << 4) | lb;
                break;
            case '+':
                b = ' ';
                break;
            default:
                b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)   
                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb   
                if (--more == 0)
                    sbuf.append((char) sumb); // Add char to sbuf   
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)   
                sbuf.append((char) b); // Store in sbuf   
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)   
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte   
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)   
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes   
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)   
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes   
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)   
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes   
            } else /*if ((b & 0xfe) == 0xfc)*/{ // 1111110x (yields 1 bit)   
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes   
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }    
}