package cl.thread;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import easyshop.downloadhelper.URLToDownload;



/*
 ** JSO1.0, by Allen Huang,2007-6-19
 */
public class ThreadQueue  implements Serializable{
	static Logger log = Logger.getLogger("ThreadQueue.java");
	 private List urlsToDo;

    
    public ThreadQueue()
    {
        urlsToDo = Collections.synchronizedList(new ArrayList());
    }

    public void queueURL(URLToDownload url)
    {
        URL u = url.getURL();
        if(urlsToDo.contains(u))
        {
            return;
        }
        urlsToDo.add(url);

    }

    public void queueURLs(Collection urls)
    {
    	urlsToDo.addAll(urls);
    }

    public Object getNextInQueue()
    {
    	if (urlsToDo.size()==0)
    		return null;
    	Object u2d = urlsToDo.get(0);
        urlsToDo.remove(0);
//        urlsInQueue.remove(u2d.getURL());
//        log.info("queue remains urls:"+urlsToDownload.size());
        return u2d;        

    }

    public void clearAll(){
        urlsToDo.clear();
    }
    public int size()
    {
        return urlsToDo.size();
    }

    public String toString()
    {
        return size() + " URLs";
    }
	
}

