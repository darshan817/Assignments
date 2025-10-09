package Assignment6;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock_Simulation {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void methodOne() {
        lock1.lock();
        System.out.println("Thread 1: Locked lock1");

        try {
            // Simulate work
            Thread.sleep(100);

            lock2.lock();
            System.out.println("Thread 1: Locked lock2");

            try {
                // Do something with both locks
                System.out.println("Inside thread 1");
            } finally {
                lock2.unlock();
            }

        } catch (InterruptedException e) {
            // e.printStackTrace();
        } finally {
            lock1.unlock();
        }
    }

    public void methodTwo() {
        lock2.lock();
        System.out.println("Thread 2: Locked lock2");

        try {
            // Simulate work
            Thread.sleep(100);

            lock1.lock();
            System.out.println("Thread 2: Locked lock1");

            try {
                // Do something with both locks
                System.out.println("Inside Thread 2");
            } finally {
                lock1.unlock();
            }

        } catch (InterruptedException e) {
            // e.printStackTrace();
        } finally {
            lock2.unlock();
        }
    }

    public static void main(String[] args) {
        Deadlock_Simulation example = new Deadlock_Simulation();

        Thread t1 = new Thread(example::methodOne);
        Thread t2 = new Thread(example::methodTwo);

        t1.start();
        t2.start();
    }
}
