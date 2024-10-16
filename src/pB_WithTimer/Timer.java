package pB_WithTimer;

public class Timer extends Thread {

	private ElevatorMonitor_PB monitor;
	private long time;
	private boolean end = false;
	
	public Timer (ElevatorMonitor_PB monitor, long time) {
		this.monitor = monitor;
		this.time = time;
	}
	
	public void destroy () {
		end = true;
	}
	
	public void run () {
		// Notice that timers do not necessarily run in an endless loop...
		boolean restart = true;
		while (restart && !end) {
			try {Thread.sleep(time);} catch(InterruptedException ie) {}
			if (!end) restart = monitor.timerGoesOff(); 
		}
	}
	
}
