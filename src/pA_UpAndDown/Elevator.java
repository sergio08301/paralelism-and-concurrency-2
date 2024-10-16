package pA_UpAndDown;

public class Elevator extends Thread {

	private ElevatorMonitor_PA monitor;
	
	public Elevator (ElevatorMonitor_PA monitor) {
		this.monitor = monitor;
	}
	
	public void run () {
		while (true) {
			
			monitor.loadForAscension();
			ascend();
			monitor.ascensionComplete();
			monitor.unload();
			
			monitor.loadForDescent();
			descend();
			monitor.descentComplete();
			monitor.unload();
			
		}
	}
	
	private void ascend() {
		System.out.print("\tASCENDING: ");
		try {Thread.sleep(300);} catch (InterruptedException ie) {}
		System.out.print("ground, ");
		for (int i=1; i<=7; i++) {
			try {Thread.sleep(150);} catch (InterruptedException ie) {}
			System.out.print(i*20+", ");
		}
		try {Thread.sleep(100);} catch (InterruptedException ie) {}
		System.out.println("148-sky. ");
		System.out.println();
	}
	
	private void descend() {
		System.out.print("\tDESCENDING: ");
		try {Thread.sleep(300);} catch (InterruptedException ie) {}
		System.out.print("148-sky, ");
		for (int i=7; i>=1; i--) {
			try {Thread.sleep(150);} catch (InterruptedException ie) {}
			System.out.print(i*20+", ");
		}
		try {Thread.sleep(100);} catch (InterruptedException ie) {}
		System.out.println("ground. ");
		System.out.println();
	}
	
}
