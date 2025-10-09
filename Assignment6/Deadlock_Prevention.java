package Assignment6;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock_Prevention {
    
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void methodOne() {
        boolean gotLock1 = false;
        boolean gotLock2 = false;
        try {
        gotLock1 = lock1.tryLock(500, TimeUnit.MILLISECONDS);
        if (gotLock1) {
            System.out.println("Thread 1: Locked lock1");
            Thread.sleep(100);

            gotLock2 = lock2.tryLock(500, TimeUnit.MILLISECONDS);
            if (gotLock2) {
                System.out.println("Thread 1: Locked lock2");
                System.out.println("Inside Thread 1");
            } else {
                System.out.println("Thread 1: Could not acquire lock2, avoiding deadlock");
            }
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        if (gotLock2) lock2.unlock();
        if (gotLock1) lock1.unlock();
    }
}

    public void methodTwo() {
        boolean gotLock1 = false;   
        boolean gotLock2 = false;
        try {
            gotLock1 = lock1.tryLock(500, TimeUnit.MILLISECONDS);
            if (gotLock1) {
                System.out.println("Thread 2: Locked lock1");
                Thread.sleep(100);

                gotLock2 = lock2.tryLock(500, TimeUnit.MILLISECONDS);
                if (gotLock2) {
                    System.out.println("Thread 2: Locked lock2");
                    System.out.println("Inside Thread 1");
                } else {
                    System.out.println("Thread 2: Could not acquire lock2, avoiding deadlock");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (gotLock2) lock2.unlock();
            if (gotLock1) lock1.unlock();
        }
    }

    public static void main(String[] args) {
        Deadlock_Prevention example = new Deadlock_Prevention();

        Thread t1 = new Thread(example::methodOne);
        Thread t2 = new Thread(example::methodTwo);

        t1.start();
        t2.start();
    }
}
