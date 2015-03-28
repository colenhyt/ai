/*
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.HttpPageGetter;
import easyshop.downloadhelper.URLToDownload;
import es.download.Constants;
import es.download.DownloadQueue;
import es.download.SpiderContext;
import es.download.finditemurls.NewOriginalUrlsSaver;
import es.download.helper.OriginalsHelper;
import es.simple.TSiteConfigs;
import es.webref.model.PageRef;

/**
 * @author Allenhuang
 *
 * created on 2005-10-11
 */
public class UrlThreadWorker implements Runnable, Constants{
	static Logger log=Logger.getLogger(UrlThreadWorker.class.getName());
    private SpiderContext context;
    /**
     * Download queue.
     * Thread safety: To access the queue, first synchronize on it.
     */
    private DownloadQueue queue;
    /**
     * Set of URLs currently being downloaded by Spider threads.
     * Thread safety: To access the set, first synchronize on it.
     */
    private boolean downloading=true;
    /**
     * Number of downloads currently taking place.
     * Thread safety: To modify this value, first synchronize on
     *                the download queue.
     */
    private int downloadsInProgress;
    /** Whether the spider should quit */
    private boolean quit;
    /** Count of running Spider threads. */
    private int running;
    /** Time we last checkpointed. */
    private long lastCheckpoint;
    
    private boolean hasPageDownloaded;
    
    private Set existURLs;
    
    private Set URLsSet;
    
    private Set missingURLs;
    
    int donwloadCount;
    
    private String pageSP,pageStore,urlStore;
    
    private OriginalsHelper saveHelper;
    
    private NewOriginalUrlsSaver saver;
    
    private int threadCount;
    
    private long latestUpdate=System.currentTimeMillis();
    
    private HttpClient httpClient;
    
	private static final String THREAD_GROUP_NAME = "urlspider";

	private ThreadGroup group = new ThreadGroup(THREAD_GROUP_NAME); // our group
    
    public UrlThreadWorker(SpiderContext context){
        //设置网络连接超时和数据读取超时为半分钟:
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");        
        this.context = context;
        httpClient=new HttpClient();
        queue = new DownloadQueue(context);
        missingURLs=new HashSet();
        URLsSet=new HashSet();
        saveHelper=new OriginalsHelper();
    }
    
	private String siteId;

	private int pageType;
    
    public void setParams(String sId,int pType,int tCount){
		siteId = sId;
		pageType = pType;
		urlStore=TSiteConfigs.getSiteConfig(siteId).getStore(pageType);
        saver=new NewOriginalUrlsSaver(siteId,pageType);
        threadCount=tCount;
    }
    
    public void loadURLs(Set urls){
        missingURLs.clear();
        queue.clearAll();
        if (urls.size()==0){
            log.warn("urls to spiders are empty");
            return;
        }
        
        log.info("get urls :"+urls.size()+" from urlStore:"+urlStore);
        ArrayList u2dsToQueue = new ArrayList();
//        
        for (Iterator it=urls.iterator();it.hasNext();){
            PageRef refer=(PageRef)it.next();
            try {
                URLToDownload url=new URLToDownload(new URL(refer.getUrlStr()), null, refer.getRefId(),refer.getUrlKey());
                u2dsToQueue.add(url);
            } catch (MalformedURLException e) {
                missingURLs.add(refer.getUrlStr());
                e.printStackTrace();
            }
            
        }
        donwloadCount=u2dsToQueue.size();
        queue.queueURLs(u2dsToQueue);        
    }

    class SpiderThread extends Thread{
		public SpiderThread(String name) {
			super(group, name);
		}    	
	    public void run()
	    {
            log.info("Starting StraightSpider thread");

	    while(queueSize() > 0)
	        {

	    	PageRef nextURL;
	            synchronized(queue)
	            {
	                nextURL = queue.getNextInQueue();
	            }

	            HttpPage obj=new HttpPageGetter(context.getUserAgent()).getURLOnly(nextURL);
//	            obj.setUrlKey(nextURL.getUrlKey());
	            System.out.println(obj.getResponse().getCode());
//	          
	                saver.add(obj);
	            
	            hasPageDownloaded=true;
	        }
	    
	    System.err.println("current active threads: "+Thread.activeCount());
	     }		
    	
    } 

    public void stop()
    {
        quit = true;
    }    
    
    public boolean isRunning()
    {
        return running>0;
    }
    
    public long getLatestUpdate(){
        return latestUpdate;
    }
    
    public void run()
    {
            
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
				saver.validateSave();
				break;
			}
		}		
     }

    public void start(){

        quit = false;
        running = 0;
        existURLs=new HashSet();
        if (queueSize()<=0){
            log.warn("before spiders work, their queue size is 0");
            return;
        }
        
		for (int i = 0; i < threadCount; i++) { // spawn threads
			SpiderThread thread = new SpiderThread(THREAD_GROUP_NAME + i);
			thread.start();
		}
    	
		run();
    }
    public Set getMissingURLs(){
        return missingURLs;
    }
    
    public boolean hasPageArrived(){
        return hasPageDownloaded;
    }
    /**
     * Get the size of the download queue in a thread-safe manner.
     */
    private int queueSize()
    {
        synchronized(queue)
        {
             return queue.size();
        }
    }

   
}
