package box.site.parser.sites;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImgGetterThread implements Runnable{
	private boolean running = true;
	ImgGetter getter = new ImgGetter();
	Map<String,String> urls = Collections.synchronizedMap(new HashMap<String,String>());

	public synchronized void push(String url,String context){
		if (!urls.containsKey(url))
			urls.put(url, context);
	}
	
	@Override
	public synchronized void run() {
		while (running){
				for (String url:urls.keySet()){
					getter.get(url, urls.get(url));
				}
				urls.clear();
			try {
//				System.out.println("img getter wait...");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
