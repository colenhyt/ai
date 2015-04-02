/*
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import box.dianshang.DsPageDealing;
import box.util.IPageDealing;
import box.util.PageThreadWorker;
import box.util.TSite;
import es.download.flow.DownloadContext;
import es.download.helper.OriginalsHelper;
import es.simple.ICircleDoJudger;
import es.simple.TAction;
import es.simple.TDownloadSite;
import es.simple.TPageTypes;
import es.simple.TSiteConfig;
import es.simple.Tracy;
import es.util.url.URLStrFormattor;
import es.webref.model.PageRef;

/**
 * @author Allenhuang
 *
 * created on 2005-10-11
 */
public class PagesThread extends Thread implements Runnable{
    static Logger log = Logger.getLogger(PagesThread.class
            .getName());

     OriginalsHelper helper=new OriginalsHelper();
     private int perCount=1000, fullActiveCount=0,threadCount=0,maxUrlCount=0;
    private int currentPageType=-1;
    TAction action;
    TSiteConfig siteConfig;
    TPageTypes types;
    TDownloadSite dSite;
    IPageDealing pageDealing;
    private int insertCount=Tracy.DEFAULT_INSERT_COUNT;
    private int downloadType=-1,biztype=-1;
    private final String specId,siteId;
    private boolean saveFile=false;
    final String oriStore,column;
    private final TSite siteAction;
    ICircleDoJudger continueJudger;
    int[] biztypes;
	boolean quit=false;
	private long duration=-1;
	private long startTime=System.currentTimeMillis();
	private Set successList=new HashSet();
	private int pageActionType=-2,storeStatus=-1;
    
    private HttpClient httpClient;
    public void setHttpClient(HttpClient _httpClient){
    	httpClient=_httpClient;
    }
    public void goToReady(){
//        for (Iterator it=types.list().iterator();it.hasNext();){
//            String str=(String)it.next();
//            int pType=new Integer(str).intValue();
//            int count=0;
//                count=helper.findNewURLsCount(siteConfig.getUrlStore(pType));
//            if (count<=0)
//               types.noteUseless(pType);
//        }
//        types.removeUseless();
    }
   
//    private boolean fetcherSetOut(){
//        
//        int count=helper.findNewURLsCount(siteConfig.getUrlStore(currentPageType));
//        int aCount=helper.findActiveOriginalPagesCount(siteConfig.getPageStore(currentPageType));
//        if (count<=0||aCount>=fullActiveCount){
//            
//            if (!types.hasNext()||aCount>=fullActiveCount){
//            log.info("current site download pages finish!!!!, now new count is "+count+", and active pages count is "+aCount);
//            DownloadContext.getDownloadLogger().setNeedSynchURLs(false);
//            setChanged();
//            notifyObservers();
//            return true;
//            }else{
//                //找下一个类型的下载
//                currentPageType=types.pushPageType();
//                startFetch();
//            }
//                
//        }else{
//            startFetch();
//        }        
//        return true;
//        
//    }
    
//    public boolean spidersSetOut3(){
//        if (!types.hasNext()){
//            log.warn("no pages to download!");
//            setChanged();
//            notifyObservers();
//            return true;
//        }
//        
//        currentPageType=types.pushPageType();
//        fetcherSetOut();
//        return true;
//    }
    
    public void inHttpClient(HttpClient _httpClient){
    	httpClient=_httpClient;
    }
    
    public TAction getAction(){
        return action;
    }
//     
//    public boolean newPagesArrived(){
//        return true;
//    }
  
    
//    private void startFetch(){
//        log.info("download start:");
//        SingleFetcher fetcher=new SingleFetcher(true);
//        fetcher.setParams(siteId,currentPageType,threadCount);
//        Set newurls=helper.getNewOriginalURLs(perCount,siteConfig.getUrlStore(currentPageType));
//        fetcher.loadURLs(newurls);
//        log.info("download pages start for site:"+siteId+" and for type: "+currentPageType);
//        try {
//            fetcher.run();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }        
//
//        DownloadContext.getDownloadLogger().setNeedSynchURLs(true);
//        try {
//            //还有new urls ,继续download:
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        fetcherSetOut();
//        
//    }

    private void b2RunSpiders(int dType){
        
    	PageThreadWorker spider=new PageThreadWorker(DownloadContext.getSpiderContext());
        spider.setHttpClient(httpClient);
        spider.setParams(siteId,dType,threadCount,downloadType,pageActionType,insertCount,pageDealing);
        List newurls= new ArrayList();
        List<PageRef> refs = pageDealing.getFirstRefs(siteAction.getSiteId());
        newurls.addAll(refs);
        spider.pushInitUrls(newurls);
        
        newurls.clear();
        log.info("download pages start for site:"+siteId);
        spider.start();
        
        try {
            //还有new urls ,继续download:
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // b1Setup(dType);
    }

    public void run() {
    	while(!quit){
    		
            if (types!=null&&!types.hasNext()){
                log.warn("no pages to download!");
                quit=true;
                return;
            }
            
            int cpType=-1;
            if (types!=null&&oriStore==null)
            	cpType=types.pushPageType();
            
            b1Setup(cpType);
    	}
    }

	private void bInit(){    	
		siteConfig=new TSiteConfig(siteId);
	}

	private void b1Setup(int currentType) {    
		boolean notContinue=false;
	    int count=0,aCount=0,urlCount;
	
	    b2RunSpiders(currentType);
	    
//	    String store=oriStore;
//	    if (store==null){
//	    	log.info("download for "+siteConfig.getStoreDesc(currentType));
//	    	store=siteConfig.getStore(currentType);
//	    }
//	     
//	        count=helper.findNewURLsCount(store,column,storeStatus);
//	    
//	    aCount=helper.findPageCount(store,StatusConstants.ITEM_STATUS_NORMAL);
//	    urlCount=helper.findPageCount(store,StatusConstants.ITEM_STATUS_NORMAL);
//	    
//	    if (quit) notContinue=true;
//	    
//	    if (count<=0||aCount>=fullActiveCount||urlCount>=maxUrlCount||(continueJudger!=null&&continueJudger.goHere())) notContinue=true;
//	    
//	    
//	    long currentTime=System.currentTimeMillis();
//	    if (duration>0&&((currentTime-startTime)>=duration)){
//	    	log.info("已运行至规定时间段,停止!");
//	    	notContinue=true;
//	    }
//	    
//	    //select count(*) as c from "+pStore+" where status=2 or status=3
//	    if (notContinue){
//	        
//	        if ((types!=null&&!types.hasNext())||aCount>=fullActiveCount){
//	        	log.info("current site download pages finish!!!!, now new count is "+count+", and active pages count is "+aCount);
//	        	quit=true;
//	        	return;
//	        }else{
//	            //找下一个类型的下载
//	        	if (types!=null)
//	        		b2RunSpiders(types.pushPageType());
//	        }
//	            
//	    }else{
//	    	b2RunSpiders(currentType);
//	    }           
	}
	public boolean spidersSetOut(){
	    if (types!=null&&!types.hasNext()){
	        log.warn("no pages to download!");
	        quit=true;
	        return true;
	    }
	    
	    int cpType=-1;
	    if (types!=null&&oriStore==null)
	    	cpType=types.pushPageType();
	    
	    b1Setup(cpType);
	    return true;
	}
	public PagesThread(TSite _siteAction,String _specId,String _oriStore,ThreadGroup group,String nameId,IPageDealing _pageDealing){
		super(group,nameId);
		pageDealing = _pageDealing;
		    specId=_specId;
		siteAction=_siteAction;
	    siteId=_siteAction.getSiteId();
	    perCount=_siteAction.getPerDownloadCount();
	    insertCount=_siteAction.getInsertCount();
	    downloadType=0;
	    biztype=_siteAction.getBiztype();
	    threadCount=_siteAction.getThreads();
	    saveFile=_siteAction.isSaveFile();
	    continueJudger=_siteAction.getContinueJudger();
	    biztypes=_siteAction.getBiztypes();
	    duration=_siteAction.getDuration();
	    pageActionType=_siteAction.getCatType();
	    storeStatus=_siteAction.getStatus();
	    column=_siteAction.getColumn();
	    
	    fullActiveCount=((Integer)_siteAction.getParamValue(Tracy.P_N_MAXCOUNT)).intValue();
	    maxUrlCount=((Integer)_siteAction.getParamValue(Tracy.P_N_MAX_URLS_COUNT)).intValue();
	    oriStore=_oriStore;
	    bInit();
	}

	public PagesThread(int[] _types,String _siteId,String _oriStore){
		types=new TPageTypes();
		for (int i=0;i<_types.length;i++){
			types.add(String.valueOf(_types[i]));
		}
		
		threadCount=2;
		fullActiveCount=500000;
		downloadType=0;
	    siteId=_siteId;
	    oriStore=_oriStore;   
	    siteAction=null;
	    specId=null;
	    column="status";
	    bInit();
	}

}
