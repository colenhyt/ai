/*
 * Created on 2005-10-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cl.html.helper;

import es.util.string.StringHelper;

/**
 * @author Allenhuang
 *
 * created on 2005-10-16
 */
public class HTMLTagResolver {
    
    public static String cutTag(String word,String tagName){
        return resolve(word.toLowerCase(),tagName.toLowerCase());
    }

    
    private static String resolve(String input,String tagName){
        int startPos = 0;
        String startTag = "<" + tagName;
        String endTag="</"+tagName;
        boolean noStartTag=false,noEndTag=false;
        while(true)
        {
            //cut start tag:
            int tagPos = input.indexOf(startTag);
            
            if(tagPos < 0) noStartTag=true;
            
            int nextClosePos = input.indexOf(">", tagPos +1);
            if (tagPos>=0){
            //完整开始标签:
            if(tagPos < nextClosePos)
            {
                String stag=input.substring(tagPos,nextClosePos+1);
                input=StringHelper.textReplace(stag,"",input);
             }else{		//残缺标签,无法解决:
                 return input;
                 
             }
            }
            
            //cut end tag:
            int endTagPos = input.indexOf(endTag);
            
            if(endTagPos < 0)                noEndTag=true;
            
            int nextEndClosePos = input.indexOf(">", endTagPos + 1);
            if (endTagPos>=0){
                //完整结束标签"
            if(endTagPos < nextEndClosePos)
            {
                String etag=input.substring(endTagPos,nextEndClosePos+1);
                input=StringHelper.textReplace(etag,"",input);
             }else
                 return input;
            }
            if (noStartTag&&noEndTag)
                return input;
        }
    }
}
