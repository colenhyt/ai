/*
 * Created on 2005-10-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import es.util.string.StringHelper;



/**
 * @author Allenhuang
 *
 * created on 2005-10-15
 */
public class URLStrFormattor {
	static String encode="utf-8";
   
    public static String encodeAll(String url){
    	if (url==null)
    		return null;
    	
	    try {
        url = StringHelper.textReplace("?", URLEncoder.encode("?",encode), url);
        url = StringHelper.textReplace("&", URLEncoder.encode("&",encode), url);
        url = StringHelper.textReplace(":", URLEncoder.encode(":",encode), url);
        url = StringHelper.textReplace("/", URLEncoder.encode("/",encode), url);
	    url = StringHelper.textReplace("=",URLEncoder.encode("=",encode),  url);
        url=url.replaceAll("'", "''");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        return url;
    }
    
    public static String decodeAll(String url){
		if (url==null)
			return null;
	    try {
			url = StringHelper.textReplace(URLEncoder.encode("?",encode),"?",  url);
	    url = StringHelper.textReplace("%3f","?",  url);
	    url = StringHelper.textReplace(URLEncoder.encode("&",encode),"&",  url);
	    url = StringHelper.textReplace(URLEncoder.encode(":",encode),":",  url);
	    url = StringHelper.textReplace(URLEncoder.encode("/",encode),"/",  url);
	    url = StringHelper.textReplace(URLEncoder.encode("=",encode),"=",  url);
	    url=url.replaceAll("''", "'");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return url;
	}

	public static String decode(String url){
    	if (url==null)
    		return null;
    	
	    try {
        url = StringHelper.textReplace(URLEncoder.encode("?",encode),"?",  url);
        url = StringHelper.textReplace("%3f","?",  url);
        url = StringHelper.textReplace(URLEncoder.encode("&",encode),"&",  url);
        url=url.replaceAll("''", "'");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return url;
    }    
    
    public static boolean isJS(String url){
        if(url == null)
        {
            return false;
        }

        url = url.toLowerCase();
        return (url.indexOf("javascript:") != -1);        
    }    
    
    public static boolean isHTTP(String url){
        return (url.indexOf("http") ==0);        
    }   
    
    public static boolean isMms(String url){
        return (url.indexOf("mms:") ==0);        
    }   
    
    
    public static boolean isMailTo(String url)
    {
        if(url == null)
        {
            return false;
        }

        url = url.toUpperCase();
        return (url.indexOf("MAILTO:") != -1);
    }

	public static String encode(String url){
		if (url==null)
			return null;
		
	    try {
	    url = StringHelper.textReplace("?", URLEncoder.encode("?",encode), url);
	    url = StringHelper.textReplace("&", URLEncoder.encode("&",encode), url);
	    url=url.replaceAll("'", "''");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    return url;
	}    
    
    

}
