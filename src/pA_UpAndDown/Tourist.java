package pA_UpAndDown;


public class Tourist extends Thread {

	
	private int pid;
	private ElevatorMonitor_PA monitor;	
	
	public Tourist (int pid, ElevatorMonitor_PA elevator) {
		this.pid = pid;
		this.monitor = elevator;
	}
	
	public int getPid () {return this.pid;}
	
	public void run () {
		while (true) {
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
		try {Thread.sleep((long)(Math.random()*500));} catch (InterruptedException ie) {}
	}
}
