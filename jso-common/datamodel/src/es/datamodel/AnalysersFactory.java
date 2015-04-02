/*
 * Created on 2005-9-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.cfg.Configuration;



/**
 * @author Allenhuang
 * 
 * created on 2005-9-24
 */
public class AnalysersFactory {
    private static AnalysersFactory factory;

    public static AnalysersFactory get() {
        if (factory == null)
            factory = new AnalysersFactory();
        return factory;
    }

    private Map classifyMap;
    public Map findClassifyMap(){
    	if (classifyMap==null){    		
    		classifyMap=new HashMap();
       Map regMap=Configuration.get().getClassifyAnalyserMap();
       for (Iterator it=regMap.keySet().iterator();it.hasNext();){
           String siteId=(String)it.next();
           String className=(String)regMap.get(siteId);
           classifyMap.put(siteId,newInstance(className));
       }
    	}
       return classifyMap;
    }

    private Map varietyMap;
    public Map findVarietyMap(){
    	if (varietyMap==null){
    		varietyMap=new HashMap();
        Map regMap=Configuration.get().getVarietyAnalyserMap();
        for (Iterator it=regMap.keySet().iterator();it.hasNext();){
            String siteId=(String)it.next();
            String className=(String)regMap.get(siteId);
            varietyMap.put(siteId,newInstance(className));
        }
    	}
        return varietyMap;
     }    

    private Map directMap;
    public Map findDirectMap(){
    	if (directMap==null){
    		directMap=new HashMap();
        Map regMap=Configuration.get().getSortAnalyserMap();
        for (Iterator it=regMap.keySet().iterator();it.hasNext();){
            String siteId=(String)it.next();
            String className=(String)regMap.get(siteId);
            directMap.put(siteId,newInstance(className));
        }
    	}
        return directMap;
     }
    
    public PageContentAnalyser getPageContentAnalyser(String siteId){
    	Map regMap=Configuration.get().getContentAnalyserMap();
        return (PageContentAnalyser)newInstance((String)regMap.get(siteId));
    }
    
    private Map contentMap;
    public Map findContentMap(){
    	if (contentMap==null){
    		contentMap=new HashMap();
       Map regMap=Configuration.get().getContentAnalyserMap();
       for (Iterator it=regMap.keySet().iterator();it.hasNext();){
           String siteId=(String)it.next();
           String className=(String)regMap.get(siteId);
           contentMap.put(siteId,newInstance(className));
       }
    	}
       return contentMap;
    }
    

    private Map reviseMap;
    public Map findReviseMap(){
    	if (reviseMap==null){
    		reviseMap=new HashMap();
       Map regMap=Configuration.get().getReviseAnalyserMap();
       for (Iterator it=regMap.keySet().iterator();it.hasNext();){
           String siteId=(String)it.next();
           String className=(String)regMap.get(siteId);
           reviseMap.put(siteId,newInstance(className));
       }
    	}
       return reviseMap;
    }    

    
    public PageClassifyAnalyser findURLAnalyser(DataURLInfo info) {
        return (PageClassifyAnalyser)newInstance(Configuration.get().getClassifyAnalyserClass(info.getSiteId()));
    }

	private Object  newInstance(String className){
		Class c=null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return c.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}}