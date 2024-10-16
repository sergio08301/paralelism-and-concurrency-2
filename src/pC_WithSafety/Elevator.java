package pC_WithSafety;

public class Elevator extends Thread {

	private ElevatorMonitor_PC monitor;
	
	public Elevator (ElevatorMonitor_PC monitor) {
		this.monitor = monitor;
	}
	
	public void run () {
		boolean safe;
		while (true) {
			safe = false;
			while (!safe) {
				safe=monitor.loadForAscension();
				if (!safe) {
					monitor.unload();
				}
			}
			// when this point is reached elevator can safely ascend with it's load
			ascend(monitor.getCurrentLoad());
			monitor.ascensionComplete();
			monitor.unload();
			
			monitor.loadForDescent();
			descend();
			monitor.descentComplete();
			monitor.unload();
		}
	}
	
	private void ascend(int w) {
		System.out.print("\tASCENDING [w= "+w+"]: ");
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
