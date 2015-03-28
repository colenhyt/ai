/*
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.util;

import java.util.Iterator;
import java.util.Observable;

import org.apache.log4j.Logger;

import es.download.finditemurls.UrlStrsSimulator;
import es.download.flow.DownloadContext;
import es.download.helper.OriginalsHelper;
import es.simple.TAction;
import es.simple.TDownloadSite;
import es.simple.TPageTypes;
import es.simple.TSingleSiteStage;
import es.simple.TSiteConfig;
import es.simple.TSiteConfigs;

/**
 * @author Allenhuang
 *
 * created on 2005-10-11
 */
public class UrlsFinder extends Observable implements Runnable{
    static Logger log = Logger.getLogger(UrlsFinder.class
            .getName());

     OriginalsHelper helper=new OriginalsHelper();
    private int perCount=0, fullActiveCount=5000,threadCount=0;
    private int currentPageType=-1;
    private String siteId;
    TSiteConfig siteConfig;
    TPageTypes types;
    
    public UrlsFinder(TDownloadSite dSite){
        siteId=dSite.getSiteId();
        siteConfig=TSiteConfigs.getSiteConfig(siteId);
        threadCount=new Integer(dSite.getParam("thread")).intValue();
        perCount=new Integer(dSite.getParam("perCount")).intValue();
        fullActiveCount=new Integer(dSite.getParam("maxCount")).intValue();
        types=dSite.getTypes();       
    }
    
    public void goToReady(){
        for (Iterator it=types.list().iterator();it.hasNext();){
            String str=(String)it.next();
            int pType=new Integer(str).intValue();
            int count=helper.findNewURLsCount(siteConfig.getStore(pType),"status",-1);
            if (count<=0)
               types.noteUseless(pType);
        }
        types.removeUseless();
    }    
    
    public boolean spidersSetOut(){
        if (!types.hasNext()){
            log.warn("no pages to download!");
            setChanged();
            notifyObservers();
            return true;
        }
        
        currentPageType=types.pushPageType();
        setOut();
        return true;
    }

    private void startSpiders(int cCount){
        
    	UrlThreadWorker spider=new UrlThreadWorker(DownloadContext.getSpiderContext());
        spider.setParams(siteId,currentPageType,threadCount);
        UrlStrsSimulator simulator=new UrlStrsSimulator();
        simulator.setSiteId(siteId);
        simulator.setCount(perCount);
        spider.loadURLs(simulator.getUrls(currentPageType,cCount));
        log.info("download pages start for site:"+siteId);
        spider.start();
        
        try {
            //还有new urls ,继续download:
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("find start again:");
        System.err.println("find start again:");
        currentCount=simulator.getCurrentCount()+1;
        setOut();
    }

    public void setStartCount(int c){
    	currentCount=c;
    }
    private int currentCount=1;
    public void setOut() {       
        if (currentCount>=fullActiveCount){
            
            if (!types.hasNext()){
            log.info("current site urlstr finding finish!!!!, now new count is "+currentCount);
            DownloadContext.getDownloadLogger().setNeedSynchURLs(false);
            setChanged();
            notifyObservers();
            return;
            }else{
                //找下一个类型的下载
                currentPageType=types.pushPageType();
                currentCount=1;
                startSpiders(currentCount);
            }
                
        }else{
        	startSpiders(currentCount);
        }           
    }

    public void run() {
        goToReady();
        spidersSetOut();
    }

}
