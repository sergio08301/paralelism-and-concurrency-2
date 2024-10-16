package pB_WithTimer;

public class Tourist extends Thread {

	
	private int pid;
	private ElevatorMonitor_PB monitor;	
	
	public Tourist (int pid, ElevatorMonitor_PB elevator) {
		this.pid = pid;
		this.monitor = elevator;
	}
	
	public int getPid () {return this.pid;}
	
	public void run () {
		while (true) {
			try {Thread.sleep((long)(Math.random()*1500+750));} catch (InterruptedException ie) {}
			monitor.getInForAscension(this);
			try {Thread.sleep((long)(Math.random()*50));} catch (InterruptedException ie) {}
			monitor.getOut(this);
			
			enjoyTheView();
			
			monitor.getInForDescent(this);
			try {Thread.sleep((long)(Math.random()*50));} catch (InterruptedException ie) {}
			monitor.getOut(this);
		}
	}
	
	
	private void enjoyTheView () {
		try {Thread.sleep((long)(Math.random()*750));} catch (InterruptedException ie) {}
	}
}
