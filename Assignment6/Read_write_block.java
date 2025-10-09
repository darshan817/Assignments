package Assignment6;

import java.util.concurrent.locks.*;

public class Read_write_block {
    private int count = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    // Writer method
    public void increment() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " [WRITE] Acquired write lock");
            count++;
            System.out.println(Thread.currentThread().getName() + " [WRITE] Incremented count to: " + count);
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + " [WRITE] Released write lock");
        }
    }

    // Reader method
    public int getCount() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " [READ] Acquired read lock");
            System.out.println(Thread.currentThread().getName() + " [READ] Read count: " + count);
            return count;
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " [READ] Released read lock");
        }
    }

    public static void main(String[] args) {
        Read_write_block obj = new Read_write_block();

        // Reader task
        Runnable readTask = () -> {
            for (int i = 0; i < 2; i++) {
                obj.getCount();
                try {
                    Thread.sleep(100); // slight delay for better interleaving
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Writer task
        Runnable writeTask = () -> {
            for (int i = 0; i < 2; i++) {
                obj.increment();
                try {
                    Thread.sleep(150); // slight delay for better interleaving
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Launch reader and writer threads
        for (int i = 0; i < 2; i++) {
            Thread readerThread = new Thread(readTask, "Reader-" + (i + 1));
            Thread writerThread = new Thread(writeTask, "Writer-" + (i + 1));

            readerThread.start();
            writerThread.start();
        }
    }
}
