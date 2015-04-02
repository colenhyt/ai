/*
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cl.thread;

import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import easyshop.downloadhelper.HttpPageGetter;
import es.Constants;


/**
 * @author Allenhuang
 *
 * created on 2005-10-11
 */
public class ThreadsWorker implements Runnable{
	static Logger log=Logger.getLogger(ThreadsWorker.class.getName());
//    private SpiderContext context;
    /**
     * Download queue.
     * Thread safety: To access the queue, first synchronize on it.
     */
	protected ThreadQueue queue;
    /**
     * Set of URLs currently being downloaded by Spider threads.
     * Thread safety: To access the set, first synchronize on it.
     */
    /**
     * Number of downloads currently taking place.
     * Thread safety: To modify this value, first synchronize on
     *                the download queue.
     */
    /** Whether the spider should quit */
//    private boolean quit;
    /** Count of running Spider threads. */
    private int running;
    /** Time we last checkpointed. */
    
    int donwloadCount;
    
    private IThreadReceiver dealer;
    
    protected int threadCount;
    
    private long latestUpdate=System.currentTimeMillis();
    
    HttpPageGetter getter=new HttpPageGetter();
    
	protected static final String THREAD_GROUP_NAME = "workSpider";

	public ThreadGroup group = new ThreadGroup(THREAD_GROUP_NAME); // our group
	public ThreadsWorker(){
     	
        queue = new ThreadQueue();
    }
    
	private String specId;

	private int pageType;
    
	
	private String charSet=Constants.CHARTSET_DEFAULT;
	public void setParams(IThreadReceiver _dealer,int tCount){
        dealer=_dealer;
        threadCount=tCount;
    }
    
    private HttpClient httpClient;
    public void loadURLs(List urls){
        queue.clearAll();
        if (urls.size()==0){
            log.warn("urls to spiders are empty");
            return;
        }
        
        donwloadCount=urls.size();
        queue.queueURLs(urls);        
    }

    public void loadURLSet(Set urls){
        queue.clearAll();
        if (urls.size()==0){
            log.warn("urls to spiders are empty");
            return;
        }
        
        donwloadCount=urls.size();
        queue.queueURLs(urls);        
    }
    
    class SpiderThread extends Thread{
		public SpiderThread(String name) {
			super(group, name);
		}    	
	    public void run()
	    {
            log.debug("Starting StraightSpider thread");

	    while(queueSize() > 0)
	        {

	            synchronized(queue)
	            {
	            	dealer.add(queue.getNextInQueue());
	                log.debug("current size: "+queueSize());
	            }
	            
	            
	        }
	    
//	    System.err.println("current active threads: "+Thread.activeCount());
	     }		
    	
    } 

    public void stop()
    {
//        quit = true;
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
				break;
			}
		}		
     }

    public void start(){

//        quit = false;
        running = 0;
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
