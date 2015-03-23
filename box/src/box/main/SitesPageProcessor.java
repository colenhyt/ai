/*
 * Created on 2006-7-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.main;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import es.download.finditemurls.StraightUrlStrsFinder;
import es.download.flow.APagesDownloader;
import es.download.flow.StraightPagesDownloader;
import es.simple.TAction;
import es.simple.TDownloadSite;
import es.simple.TPageTypes;
import es.simple.TSingleSpecieStage;

/**
 * Tracy&Allen.EasyShop 1.0
 *
 * @author Allenhuang
 * 
 * created on 2006-7-14
 */
public class SitesPageProcessor extends APagesDownloader {
    static Logger log = Logger.getLogger(SitesPageProcessor.class
            .getName());
    private int perCount=0, fullActiveCount=5000;
    private List downloadSites;
    private TSingleSpecieStage[] siteActions;
    private int siteRunning=0,sites=0;
    private HttpClient httpClient;
    
  	MultiThreadedHttpConnectionManager connectionManager = 
  		new MultiThreadedHttpConnectionManager();
    public final static String HTTP_USER_AGENT="Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)";
    private static final String THREAD_GROUP_NAME = "downloadspider";
	private ThreadGroup group = new ThreadGroup(THREAD_GROUP_NAME); // our group    

    /**
     * @param tc
     */
    
    private int maxConnPerHost=2;
    private int timeOut=6000;
    private int maxTotalConn=20;
    public SitesPageProcessor(TAction tc) {
        super(tc);
//        TMultiSiteStage stage=(TMultiSiteStage)ac.getStage();
      	httpClient = new HttpClient(connectionManager);
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, HTTP_USER_AGENT);  //让服务器认为是IE
      	connectionManager.getParams().setConnectionTimeout(6000);
      	connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnPerHost);
      	connectionManager.getParams().setMaxTotalConnections(maxTotalConn);
//        downloadSites=stage.getDownloadSites();
      	
      	if (action.getStages()!=null)
      		siteActions=(TSingleSpecieStage[])action.getStages();        
    }

    public SitesPageProcessor(int[] _types,String[] siteIds){
    	super(siteIds);
    	TPageTypes types=new TPageTypes();
    	for (int i=0;i<_types.length;i++){
    		types.add(String.valueOf(_types[i]));
    	}    	
    	if (siteIds!=null&&siteIds.length>0){
    		siteActions=new TSingleSpecieStage[siteIds.length];
    		for (int i=0;i<siteActions.length;i++){
    			siteActions[i]=new TSingleSpecieStage(types,"-1",siteIds[i],null);
    		}
    	}
    }
    public void goToReady4Urls(){
        for (Iterator it=downloadSites.iterator();it.hasNext();){
            TDownloadSite dSite=(TDownloadSite)it.next();
            StraightUrlStrsFinder downloader=new StraightUrlStrsFinder(ac,dSite);
            downloader.addObserver(this);
            Thread t=new Thread(downloader);
            t.start();
            siteRunning++;
            
        }
    }
    
    public void goToReady(){
    	if (siteActions!=null&&siteActions.length>0){
            for (int i=0;i<siteActions.length;i++){
        	bStartUpStraightDownloader(siteActions[i],siteActions[i].getTypes(),siteActions[i].getSpecId(),null,i);            
            }
        }
        run();  
    }
    
    public boolean spidersSetOut(){
        
        return true;
    }

    public TAction getAction(){
        return ac;
    }

    public void update(Observable o, Object arg) {
        siteRunning--;
        log.info("one site download finished:");
        if (siteRunning<=0){
            log.info("-------所有的蜘蛛已运行完毕!!!-----");
            setChanged();
            notifyObservers();            
        }
    }

	private void bStartUpStraightDownloader(TSingleSpecieStage siteAction,TPageTypes types,String specId,String oriStore,int id){
	    StraightPagesDownloader downloader=new StraightPagesDownloader(this,siteAction,siteAction.getTypes(),specId,null,group,THREAD_GROUP_NAME +id);
	    downloader.inHttpClient(httpClient);
	    downloader.start();
	    siteRunning++;    
	    sites++;
	}

	private void run(){
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
	
			int n = group.activeCount();
			Thread[] list = new Thread[n];
			group.enumerate(list);
			boolean noMoreFetcherThread = true; // assumption
			for (int i = 0; i < n; i++) {
				// this thread may have gone away in the meantime
				if (list[i] == null)
					continue;
				String tname = list[i].getName();
				if (tname.startsWith(THREAD_GROUP_NAME)) // prove it
					noMoreFetcherThread = false;
			}
			
			if (noMoreFetcherThread) {
	            log.info("Now all sites pages' download have finished:");
	            setChanged();
	            notifyObservers(); 
				break;
			}
		}    	
	}

}
