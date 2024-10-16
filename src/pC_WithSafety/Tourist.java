package pC_WithSafety;

import java.util.Random;

public class Tourist extends Thread {

	private static Random random = new Random();
	
	private int pid;
	private int weight;
	private ElevatorMonitor_PC monitor;	
	
	public Tourist (int pid, ElevatorMonitor_PC elevator) {
		this.pid = pid;
		this.monitor = elevator;
		this.weight = 75 + (random.nextInt(100)>50?10:-10);
		if (weight<75) weight+= random.nextInt(3);
	}
	
	public int getWeight() {return weight;}
	public int getPid () {return this.pid;}
	
	public void run () {
		boolean safe;
		while (true) {
			safe = false;
			while (!safe) {
				safe = monitor.getInForAscension(this);
				if (!safe) {
					complain();
					monitor.getOut(this);
				}
			}
			monitor.getOut(this);
			
			enjoyTheView();
			
			monitor.getInForDescent(this);
			monitor.getOut(this);
		}
	}
	
	private void complain () {
		System.out.println("**TOURIST["+pid+"] cries: \"what the F*CK!. What's wrong with this junk?!\"");
	}
	
	private void enjoyTheView () {
		try {Thread.sleep((long)(Math.random()*300));} catch (InterruptedException ie) {}
	}
}
