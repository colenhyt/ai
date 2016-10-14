package box.main;

import org.apache.log4j.Logger;

import box.mgr.PageManager;


public class EventManager extends java.util.TimerTask{
	public static int TICK_PERIOD = 3000; //1000*60*60*5;
    private static EventManager uniqueInstance = null;  
	private Boolean isStart;
	protected Logger  log = Logger.getLogger(getClass()); 
	
    public static EventManager getInstance() {  
        if (uniqueInstance == null) {  
            uniqueInstance = new EventManager();  
        }  
        return uniqueInstance;  
     } 
    
	public EventManager(){
		isStart = false;
	}
	
	public void start(){
		if (isStart) return;
		
		log.warn("EventManager start....");
		
		isStart = true;
		
		java.util.Timer timer = new java.util.Timer(true);  
		timer.schedule(this, TICK_PERIOD,TICK_PERIOD);   		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		PageManager.getInstance().process();
	}
	
	public static void main(String[] args) {
		EventManager.getInstance().start();
//		ProcessManager.getInstance().process();
//		ProcessManager.getInstance().processSpiders();
	}	
}
