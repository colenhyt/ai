/*
 * Created on 2005-9-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.io;

import easyshop.model.ModelConstants;

/**
 * @author Allenhuang
 *
 * created on 2005-9-29
 */
public class PageTypeResolver {
    
    public static int getType(String urlStr){
        if (isASP(urlStr))
            return ModelConstants.PAGE_TYPE_ASP;
        if (isHTML(urlStr))
            return ModelConstants.PAGE_TYPE_HTML;
        if (isJSP(urlStr))
            return ModelConstants.PAGE_TYPE_JSP;
        if (isXML(urlStr))
            return ModelConstants.PAGE_TYPE_XML;
        if (isDLL(urlStr))
            return ModelConstants.PAGE_TYPE_DLL;
        if (isPHP(urlStr))
            return ModelConstants.PAGE_TYPE_PHP;
        if (isJPG(urlStr))
            return ModelConstants.PAGE_TYPE_JPG;
        if (isGIF(urlStr))
            return ModelConstants.PAGE_TYPE_GIF;       
        if (isDirectory(urlStr))
            return ModelConstants.PAGE_TYPE_DIRECTORY;
        
        return ModelConstants.PAGE_TYPE_UNKNOWN;
    }
    
    public static boolean isDirectory(String urlStr){
        if (urlStr.endsWith("/")||urlStr.endsWith(".com")||urlStr.endsWith(".org")||urlStr.endsWith("net"))
            return true;
        
        int i1=urlStr.lastIndexOf("/");
        int i2=urlStr.lastIndexOf(".");
        if (i1>0&&i2>0&&i1>i2)
            return true;
        return false;
    }
    
    public static boolean isASP(String urlStr){
        return (urlStr.toLowerCase().indexOf(".asp")>0);
    }
    
    public static boolean isJSP(String urlStr){
        return (urlStr.toLowerCase().endsWith(".jsp"));
    }
    
    public static boolean isHTML(String urlStr){
        return (urlStr.toLowerCase().endsWith(".htm")||urlStr.toLowerCase().endsWith(".html"));
    }
    
    public static boolean isDLL(String urlStr){
        return (urlStr.toLowerCase().indexOf(".dll")>0);
    }
    
    public static boolean isXML(String urlStr){
        return (urlStr.toLowerCase().endsWith(".xml"));
    }
    
    public static boolean isPHP(String urlStr){
        return (urlStr.toLowerCase().indexOf(".php")>0);
    }        
    
    public static boolean isGIF(String urlStr){
        return (urlStr.toLowerCase().indexOf(".gif")>0);
    }   
    
    public static boolean isJPG(String urlStr){
        return (urlStr.toLowerCase().indexOf(".jpg")>0);
    }   
    
    public static boolean isStaticFile(String urlStr){      
        return (isJPG(urlStr)||isGIF(urlStr));
    }    
    
    public static boolean isImg(String urlStr){      
        return (isJPG(urlStr)||isGIF(urlStr));
    } 
    
    public static boolean isPage(String urlStr){
        return (isPHP(urlStr)||isXML(urlStr)||isDLL(urlStr)||isHTML(urlStr)||isJSP(urlStr)||isASP(urlStr));
    }

}
