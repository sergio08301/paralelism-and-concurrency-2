package pA_UpAndDown;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ElevatorMonitor_PA {
	
	/* COMPLETE */
    private static final int CAPACITY = 4;
    private int onElevator = 0;
    private boolean openForBoarding = false;
    private boolean openForUnboarding = false;
	private ReentrantLock lock = new ReentrantLock();
	private Condition elevatorIsFull = lock.newCondition();
	private Condition elevatorIsEmpty = lock.newCondition();
	private Condition canBoardUp = lock.newCondition();
	private Condition canBoardDown = lock.newCondition();
	private Condition canUnboard= lock.newCondition();

	
	public void loadForAscension () {
		/* COMPLETE */
		lock.lock();
        try {
            openForBoarding = true;
            canBoardUp.signal();
            while (onElevator != CAPACITY) {
                elevatorIsFull.await();
            }
            openForBoarding = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
            lock.unlock();
		
	}
	
	public void loadForDescent () {
		/* COMPLETE */
		 lock.lock();
	        try {
	            openForBoarding = true;
	            canBoardDown.signal();
	            while (onElevator != CAPACITY) {
	                elevatorIsFull.await();
	            }
	            openForBoarding = false;
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	      lock.unlock();

	}
	
	public void unload () {
		/* COMPLETE */
		 lock.lock();
	        try {
	            openForUnboarding = true;
	            canUnboard.signal();
	            while (onElevator != 0) {
	                elevatorIsEmpty.await();
	            }
	            openForUnboarding = false;
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	     lock.unlock();

	}
	
	public void ascensionComplete () {
		/* COMPLETE */
	}
	
	public void descentComplete () {
		/* COMPLETE */
	}
	
	// --------------------------------------------------------------------
	
	public void  getInForAscension(Tourist p) {
		/* COMPLETE */
		lock.lock();
        try {
            while (!openForBoarding || onElevator == CAPACITY) {
                canBoardUp.await();
            }
            onElevator++;
            System.out.println("++Tourist["+p.getPid()+"] is in the elevator (UPWARDS)");
            if (onElevator == CAPACITY) {
                elevatorIsFull.signal();
                System.out.println();
            } else {
                canBoardUp.signal();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
            lock.unlock();

	}
	
	
	public void getInForDescent (Tourist p) {
		/* COMPLETE */
		lock.lock();
        try {
            while (!openForBoarding || onElevator == CAPACITY) {
                canBoardDown.await();
            }
            onElevator++;
            System.out.println("++Tourist["+p.getPid()+"] is in the elevator (DOWNWARDS)");
            if (onElevator == CAPACITY) {
                elevatorIsFull.signal();
                System.out.println();
            } else {
                canBoardDown.signal();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        lock.unlock();
        

	}
	
	public void getOut (Tourist p) {
		/* COMPLETE */
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
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        lock.unlock();
	}

}
