/*
 * Created on 2005-10-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import easyshop.model.ModelConstants;
import es.util.io.PageTypeResolver;

/**
 * @author Allenhuang
 *
 * created on 2005-10-30
 */
public class URLGenerator {
    
    
    public static String generate(String parent,String urlStr){
        
        if (parent==null||parent.trim().length()==0||urlStr==null)
            return null;
        
        //urlstr是锚,取当前路径:
        if (urlStr.length()>0&&(urlStr.indexOf("#")==0||urlStr.equals("/")||urlStr.equals("?")))
            return parent;
        
        //绝对路径urlStr:
        if (urlStr.startsWith("http:")||urlStr.startsWith("https:"))
            return urlStr;
        
        //先取parent路径(不包含/):
        String directory;
        if (!PageTypeResolver.isDirectory(parent)||parent.endsWith("/"))
            directory=parent.substring(0,parent.lastIndexOf("/"));
        else 
            directory=parent;

        String root;
        //取出根路径:
        int index=directory.indexOf("/",directory.indexOf("://")+3);
        //本身为根目录:        
        if (index<0)
            root=directory;
         else
            root=directory.substring(0,index); 
        
        //"/"开头, 取directory根路径:
        if(urlStr.startsWith("/")||urlStr.startsWith("../"))            
            return root+urlStr.substring(urlStr.indexOf("/"));            
        else if (urlStr.startsWith("./"))
            return directory+urlStr.substring(1);
        else if (!urlStr.startsWith("?"))
            return directory+"/"+urlStr;
        
        //"?"开头的相对路径:
        if (urlStr.startsWith("?")&&PageTypeResolver.getType(parent)!=ModelConstants.PAGE_TYPE_HTML)
            return parent+urlStr;
        
        
        //绝对路径:
        return urlStr;
    }

}
