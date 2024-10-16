package pB_WithTimer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ElevatorMonitor_PB {
	
	    private final int CAPACITY = 4;
	    private final int TIME_UP = 500;
	    private final int TIME_DOWN = 2000;
	    private int onElevator = 0;
	    private boolean openForBoardingUp = false;
	    private boolean openForBoardingDown = false;
	    private boolean openForUnboarding = false;
	    private boolean onTime = false;
	    private Lock lock = new ReentrantLock();
	    private Condition elevatorIsEmpty = lock.newCondition();
	    private Condition canBoardUp = lock.newCondition();
	    private Condition canBoardDown = lock.newCondition();
	    private Condition canUnboard = lock.newCondition();
	    private Timer timer;

	    public boolean timerGoesOff() {
	        return false;
	    }

	    public void loadForAscension() {
	        lock.lock();
	        
            openForBoardingUp = true;
            canBoardUp.signal();
            setTimer(TIME_UP);
            openForBoardingUp = false;
        
            lock.unlock();
	        
	    }

	    public void loadForDescent() {
	        lock.lock();
            openForBoardingDown = true;
            canBoardDown.signal();
            setTimer(TIME_DOWN);
            openForBoardingDown = false;
            lock.unlock();

	    }

	    public void unload() {
	        lock.lock();
	        try {
	            openForUnboarding = true;
	            canUnboard.signal();
	            while (onElevator != 0) {
	                elevatorIsEmpty.await();
	            }
	            openForUnboarding = false;
	        } catch (InterruptedException e) {} 
	            lock.unlock();
	    }

	    public void ascensionComplete() {
	        /*COMPLETE*/
	    }

	    public void descentComplete() {
	        /*COMPLETE*/
	    }
	    
	    // --------------------------------------------------------------------

	    public void getInForAscension(Tourist p) {
	    	
	        lock.lock();
	        try {
	            while (!openForBoardingUp || onElevator == CAPACITY || !onTime) {
	                canBoardUp.await();
	            }
	            onElevator++;
	            System.out.println("++Tourist["+p.getPid()+"] is the elevator (UPWARDS)");
	            if (onElevator != CAPACITY) {
	                canBoardUp.signal();
	            }
	        } catch (InterruptedException e) {} 
	        lock.unlock();
	    }

	    public void getInForDescent(Tourist p) {
	        lock.lock();
	        try {
	            while (!openForBoardingDown || onElevator == CAPACITY || !onTime) {
	                canBoardDown.await();
	            }
	            onElevator++;
	            System.out.println("++Tourist["+p.getPid()+"] is the elevator (DOWNWARDS)");
	            if (onElevator != CAPACITY) {
	                canBoardDown.signal();
	            }
	        } catch (InterruptedException e) {} finally {
	            lock.unlock();
	        }
	    }

	    public void getOut(Tourist p) {
	        lock.lock();
	        try {
	            while (!openForUnboarding) {
	                canUnboard.await();
	            }
	            onElevator--;
	            System.out.println("--Tourist["+p.getPid()+"] has left the elevator");
	            if (onElevator == 0) {
	                elevatorIsEmpty.signal();
	                System.out.println();
	            } else {
	                canUnboard.signal();
	            }
	        } catch (InterruptedException e) {} finally {
	            lock.unlock();
	        }
	    }
	    
	    private void setTimer(long time) {
	    	lock.unlock();
	    	onTime = true;
            timer = new Timer(this, time);
            timer.run();
            timer.destroy();
            System.out.println("\n-------------------> TIME OUT <-------------------\n");
            onTime = false;
            lock.lock();
	    }
	
}