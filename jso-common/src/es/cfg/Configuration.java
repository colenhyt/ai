/*
 * Created on 2005-9-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import es.Constants;


/**
 * @author Allenhuang
 *
 * created on 2005-9-25
 */
public class Configuration {
	private static Configuration instance;
	
	public Configuration(){
		
	}
	
	public static Configuration get(){
	    if (instance==null)
	        instance=new Configuration();
	    return instance;
	}
	
	private Document doc1;
	public Document getConfigDoc(){
	    if (doc1==null)
		doc1= parse(Constants.CFG_FILE);
	    return doc1;
	}
	
	private Document actionDoc;
	private Document getActionDoc(){
	    if (actionDoc==null)
	    	actionDoc= parse(Constants.ACTION_FILE);
	    return actionDoc;
	}
	
	public String getVarietyAnalyserClass(String siteId){
	    return getAnalyserClass(siteId,"variety-analyser");
	}

	public String getProductContentAnalyserClass(String siteId){
	    return getAnalyserClass(siteId,"content-analyser");
	}
	
    public String getClassifyAnalyserClass(String siteId){
        return getAnalyserClass(siteId,"classify-analyser");
    }
    
    private String getAnalyserClass(String siteId,String elementName){
    	String className=null;
    	List elements=getServices();
    	Iterator it=elements.iterator();
    	while (it.hasNext()){
    		Element e=(Element)it.next();
    		if (e.getAttributeValue("name").equalsIgnoreCase(siteId)){
    			className=e.getChild(elementName).getAttributeValue("class");
    			break;
    		}
    	}
    	return className;        
    }
    
    private Map classifyAnalyserMap;
    public Map getClassifyAnalyserMap(){
    	if (classifyAnalyserMap==null)
    		classifyAnalyserMap=getMap("classify-analyser");
    	return classifyAnalyserMap;
    }    
    
    private Map varietyAnalyserMap;
    public Map getVarietyAnalyserMap(){
    	if (varietyAnalyserMap==null)
    		varietyAnalyserMap=getMap("variety-analyser");
    	return varietyAnalyserMap;
    } 
    
    private Map sortAnalyserMap;
    public Map getSortAnalyserMap(){
    	if (sortAnalyserMap==null)
    		sortAnalyserMap=getMap("direct-analyser");
    	return sortAnalyserMap;
    }    
    private Map contentAnalyserMap;
    public Map getContentAnalyserMap(){
    	if (contentAnalyserMap==null)
    		contentAnalyserMap=getMap("content-analyser");
    	return contentAnalyserMap;
    }      
    
    private Map reviseAnalyserMap;
    public Map getReviseAnalyserMap(){
    	if (reviseAnalyserMap==null)
    		reviseAnalyserMap=getMap("revise-analyser");
    	return reviseAnalyserMap;
    }
    
    public String findDefaultActionName(){
        return getActions().getChild("defaultaction").getAttributeValue("name");
    }
    
    public Element findConfigs(){
       return getConfigDoc().getRootElement().getChild("siteconfigs");
       
    }
    
    public Element findConfigElement(String siteId){
        Element config=null;
        List list=getConfigDoc().getRootElement().getChild("siteconfigs").getChildren("config");
        for (Iterator it=list.iterator();it.hasNext();){
    		Element e=(Element)it.next();
    		if (e.getAttributeValue("site").equalsIgnoreCase(siteId)){
    		    config=e;
    		    break;
    		}
            
        }
        return config;        
    }
    
    public Element findActionElement(String actionName){
        Element action=null;
        List list=getActions().getChildren("action");
        for (Iterator it=list.iterator();it.hasNext();){
    		Element e=(Element)it.next();
    		if (e.getAttributeValue("name").equalsIgnoreCase(actionName)){
    		    action=e;
    		    break;
    		}
            
        }
        return action;
    }
    private Map getMap(String analyser){
        Map map=new HashMap();
    	List elements=getServices();
    	Iterator it=elements.iterator();
    	while (it.hasNext()){
    		Element e=(Element)it.next();
    		String specId=e.getAttributeValue("name");
    		String	className=e.getChild(analyser).getAttributeValue("class");
    		map.put(specId,className);
    	}
    	return map;        
    }

    private Element getActions(){
        return getActionDoc().getRootElement().getChild("actions");
    }
    
    private List services;
    private List getServices(){
    	if (services==null){
    	Element service=getConfigDoc().getRootElement().getChild("services");
    	services=service.getChildren("service");
    	}
    	return services;
    }
    public String getSiteKeyStr(String siteId){
        String key=null;
    	List elements=getServices();
    	Iterator it=elements.iterator();
    	while (it.hasNext()){
    		Element e=(Element)it.next();
    		if (e.getAttributeValue("name").equalsIgnoreCase(siteId)){
    		    key=e.getAttributeValue("site");
    			break;
    		}
    	}    
    	return key;
    }
    
	private InputStream getResourceStream(String sourceURI)throws Exception{
	    InputStream in=getClass().getResourceAsStream(sourceURI);
	    if (in==null) in=Thread.currentThread().getContextClassLoader().getResourceAsStream(sourceURI);
	    if (in==null) throw new Exception(sourceURI+" not be found!");
	    return in;
	}
	
	private Document parse(String fileName){
        String saxDriverClass = null;
        boolean expandEntities = true;
        Document doc=null;

        // Create an instance of the tester and test
        try {
            SAXBuilder builder = null;
            if (saxDriverClass == null) {
                builder = new SAXBuilder();
            } else {
                builder = new SAXBuilder(saxDriverClass);
            }
            builder.setExpandEntities(expandEntities);
//            File file=new File(fileName);
            doc = builder.build(getResourceStream(fileName));

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }		
        return doc;
		
	}    
}
