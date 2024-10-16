package pA_UpAndDown;

public class Launcher {
	
	public static void main (String [] args) {
		final int INSTANCES = 10;
		
		ElevatorMonitor_PA monitor = new ElevatorMonitor_PA();
		Elevator elevator = new Elevator(monitor);
		Tourist [] people = new Tourist[INSTANCES];
		
		System.out.println("\nLaunching the \"simple up and down\" version\n");
		
		for (int i=0; i<INSTANCES; i++) {
			people[i] = new Tourist(i, monitor);
			people[i].start();
		}
		
		elevator.start();
		
		try {Thread.sleep(25000);} catch (InterruptedException ie) {}
		
		System.exit(0);
	}
	
}
