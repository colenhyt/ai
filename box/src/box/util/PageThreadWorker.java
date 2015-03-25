/*
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import easyshop.downloadhelper.HttpPageGetter;
import easyshop.downloadhelper.OriHttpPage;
import es.Constants;
import es.download.DownloadQueue;
import es.download.INewOriginalPagesDealer;
import es.download.NewOriginalPagesCommonSaver;
import es.download.SpiderContext;
import es.download.URLsConverter;
import es.download.helper.OriginalsHelper;
import es.simple.TSiteConfigs;
import es.webref.model.PageRef;

/**
 * @author Allenhuang
 *
 * created on 2005-10-11
 */
public class PageThreadWorker implements Runnable, Constants{
	static Logger log=Logger.getLogger(PageThreadWorker.class.getName());
    private SpiderContext context;
    URLsConverter urlConver=new URLsConverter();
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
    
    private int pageActionType=-1;
    
    private Set missingURLs;
    
    int donwloadCount;
    
    private String pageSP,pageStore,urlStore;
    
    private OriginalsHelper saveHelper;
    
    private INewOriginalPagesDealer dealer;
    
    private int threadCount;
    
    private long latestUpdate=System.currentTimeMillis();
    
	private static final String THREAD_GROUP_NAME = "downloadspider";

	private ThreadGroup group = new ThreadGroup(THREAD_GROUP_NAME); // our group
    public PageThreadWorker(SpiderContext context){
        //设置网络连接超时和数据读取超时为半分钟:
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");        
        this.context = context;
        
//      httpClient.getHostConfiguration().setProxy("192.0.0.88", 80);
      	
        queue = new DownloadQueue(context);
        missingURLs=new HashSet();
        URLsSet=new HashSet();
        saveHelper=new OriginalsHelper();
    }
    
	private String siteId;

	private int pageType;
    
	private boolean saveFile=false;
	public void isSaveFile(boolean save){
		saveFile=save;
	}
    public void setParams(String sId,int pType,int tCount,int downloadType,int pageActionType,int perCount){
		siteId = sId;
		pageType = pType;
		urlStore=TSiteConfigs.getSiteConfig(siteId).getStore(pageType);
        dealer=new PageDealing(sId,pType,pageActionType,perCount);
        this.pageActionType=pageActionType;
        dealer.setDoDeal(true);
        threadCount=tCount;
        
        
    }
    private HttpClient httpClient;
    public void setHttpClient(HttpClient _httpClient){
    	httpClient=_httpClient;
    }
   
    public void pushInitUrls(List urls){
        missingURLs.clear();
        queue.clearAll();
        if (urls.size()==0){
            log.warn("urls to spiders are empty");
            return;
        }
        
        log.info("get urls :"+urls.size()+" from urlStore:"+urlStore);
        donwloadCount=urls.size();
        queue.queueURLs(urls);        
    }

    public void addUrls(List<PageRef> urls)
    {
    	synchronized(queue)
    	{
    		queue.queueURLs(urls);
    	}
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
	            PageRef dRef=new PageRef(urlConver.encodeDownloadUrl(nextURL.getUrlStr(), siteId, pageType, pageActionType));
	            OriHttpPage obj=new HttpPageGetter(context.getUserAgent()).getOriHttpPage(dRef);
//	          
	            if (obj.getResponse().getCode()==200){
	            	obj.setClassId(pageType);
	            	obj.setSiteId(siteId);
	            	obj.setUrlKey(nextURL.getUrlKey());
	            	obj.setTypeId(nextURL.getTypeId());
	            	obj.setRootUrlStr(nextURL.getRootUrlStr());
	            	obj.setRefWord(nextURL.getRefWord());
	            	obj.setRelPaging(nextURL.getPaging());
	            	obj.setPageActionType(pageActionType);
	            	obj.setRelWord(nextURL.getRelWord());
	            	obj.setRefId(nextURL.getRefId());
	            	obj.setSaveFile(saveFile);
	            	log.debug("get a page....");
	            	dealer.add(obj);
	            }
	            else{
	            	obj.setClassId(pageType);
	            	obj.setSiteId(siteId);
	            	obj.setUrlKey(nextURL.getUrlKey());
	            	obj.setTypeId(nextURL.getTypeId());
	            	log.error("could not get this page:"+obj.getResponse().getCode()+";"+obj.getUrlStr());
	                missingURLs.add(obj);
	            }
	            
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
				dealer.validate();
            	log.error("labeling error pages, count="+getMissingURLs().size());
				new OriginalsHelper().labelMissingURLs(getMissingURLs(),
						TSiteConfigs.getSiteConfig(siteId)
								.getStore(pageType));
				getMissingURLs().clear();
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
