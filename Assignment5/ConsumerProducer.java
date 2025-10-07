package Assignment5;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConsumerProducer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Input: Buffer size and number of iterations
        System.out.print("Enter the buffer size: ");
        int bufferSize = scanner.nextInt();

        System.out.print("Enter number of iterations: ");
        int iterations = scanner.nextInt();

        // 2. Create BlockingQueue with user-defined size
        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(bufferSize);

        // 3. Create and start producer and consumer threads
        Producer producer = new Producer(buffer, iterations);
        Consumer consumer = new Consumer(buffer, iterations);

        producer.start();
        consumer.start();
    }

    // Producer thread
    static class Producer extends Thread {
        private final BlockingQueue<Integer> buffer;
        private final int iterations;

        public Producer(BlockingQueue<Integer> buffer, int iterations) {
            this.buffer = buffer;
            this.iterations = iterations;
        }

        public void run() {
            for (int i = 0; i < iterations; i++) {
                try {
                    buffer.put(i);  // waits if buffer is full
                    System.out.println("Produced: " + i);
                    System.out.println("Queue size after producing: " + buffer.size());
                    Thread.sleep(100); // simulate some delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Consumer thread
    static class Consumer extends Thread {
        private final BlockingQueue<Integer> buffer;
        private final int iterations;

        public Consumer(BlockingQueue<Integer> buffer, int iterations) {
            this.buffer = buffer;
            this.iterations = iterations;
        }

        public void run() {
            for (int i = 0; i < iterations; i++) {
                try {
                    int val = buffer.take();  // waits if buffer is empty
                    System.out.println("Consumed: " + val);
                    System.out.println("Queue size after consuming: " + buffer.size());
                    Thread.sleep(150); // simulate some delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
