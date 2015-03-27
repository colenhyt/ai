package cn.hd.util;

public class HdTimer extends java.util.TimerTask{
	public static int TICK_PERIOD = 3000;
	private Boolean isStart;
	
	public HdTimer(){
		isStart = false;
	}
	
	public void start(){
		if (isStart) return;
		System.out.println("EventManager start....");
		
		isStart = true;
		
		java.util.Timer timer = new java.util.Timer(true);  
		timer.schedule(this, TICK_PERIOD,TICK_PERIOD);   		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
