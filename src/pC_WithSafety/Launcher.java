package pC_WithSafety;

public class Launcher {
	
	public static void main (String [] args) {
		final int INSTANCES = 30;
		
		ElevatorMonitor_PC monitor = new ElevatorMonitor_PC();
		Elevator elevator = new Elevator(monitor);
		Tourist [] people = new Tourist[INSTANCES];
		
		System.out.println("\nLaunching the \"with safety\" version\n");
		
		for (int i=0; i<INSTANCES; i++) {
			people[i] = new Tourist(i, monitor);
			people[i].start();
		}
		
		elevator.start();
		
		try {Thread.sleep(25000);} catch (InterruptedException ie) {}
		
		System.exit(0);
	}
	
}
