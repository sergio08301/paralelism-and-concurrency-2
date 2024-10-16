package pC_WithSafety;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ElevatorMonitor_PC {

	
	
	/* COMPLETE */
    private static final int CAPACITY = 4;
    private static final int MAX_WEIGHT = 300;
    private int onElevator = 0;
	private int currentWeight = 0;
    private boolean openForBoarding = false;
    private boolean openForUnboarding = false;
	private ReentrantLock lock = new ReentrantLock();
	private Condition elevatorIsFull = lock.newCondition();
	private Condition elevatorIsEmpty = lock.newCondition();
	private Condition canBoardUp = lock.newCondition();
	private Condition canBoardDown = lock.newCondition();
	private Condition canUnboard= lock.newCondition();
	private Condition isSafe= lock.newCondition();

	
	public boolean loadForAscension () {
		/* COMPLETE */
		lock.lock();
        try {
            openForBoarding = true;
            canBoardUp.signal();
            while (onElevator != CAPACITY) {
                elevatorIsFull.await();
            }
            openForBoarding = false;
            if(currentWeight>MAX_WEIGHT) {
            	System.out.println("EMERGENCY: weight EXCESS ("+currentWeight+") Please, get out");
            }
            isSafe.signal();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
            lock.unlock();
			return currentWeight<=MAX_WEIGHT;
		
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
	
	public boolean  getInForAscension(Tourist p) {
		/* COMPLETE */
		lock.lock();
        try {
            while (!openForBoarding || onElevator == CAPACITY) {
                canBoardUp.await();
            }
            onElevator++;
            currentWeight+=p.getWeight();
            System.out.println("++Tourist["+p.getPid()+" w:"+p.getWeight()+"] is in the elevator (UPWARDS)");
            if (onElevator == CAPACITY) {
                elevatorIsFull.signal();
                System.out.println();
            } else {
                canBoardUp.signal();
            }
            while(openForBoarding) {
            	isSafe.await();
            }
            isSafe.signal();
        } catch (InterruptedException e) {}
        
        lock.unlock();
		return currentWeight<=MAX_WEIGHT;

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
                currentWeight=0;
            } else {
                canUnboard.signal();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        lock.unlock();
	}


	public int getCurrentLoad() {
		return currentWeight;
	}


	
}
