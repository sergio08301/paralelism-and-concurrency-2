package pB_WithTimer;

public class Launcher {
	
	public static void main (String [] args) {
		final int INSTANCES = 4;
		
		ElevatorMonitor_PB monitor = new ElevatorMonitor_PB();
		Elevator elevator = new Elevator(monitor);
		Tourist [] people = new Tourist[INSTANCES];
		
		System.out.println("\nLaunching the \"with timer\" version\n");
		
		for (int i=0; i<INSTANCES; i++) {
			people[i] = new Tourist(i, monitor);
			people[i].start();
		}
		
		elevator.start();
		
		try {Thread.sleep(25000);} catch (InterruptedException ie) {}
		
		System.exit(0);
	}
	
}
