package Assignment6;
import java.util.concurrent.CountDownLatch;

class worker implements Runnable {
    private CountDownLatch latch ;
    public worker(CountDownLatch latch){
        this.latch =  latch;
    }

    @Override
    public void run(){
        try{
            System.out.println(Thread.currentThread().getName() + " doing work...");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " finished.");
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        finally{
            latch.countDown();
        }

    }
}
public class Countdownlatch {
    public static void main(String [] args){
        int n = 3;
        CountDownLatch latch = new CountDownLatch(n);

        for (int i = 0; i<n; i++){
            new Thread(new worker(latch), "Worker-" + i).start();
        }
        System.out.println("Main thread start..."+ Thread.currentThread().getName());
        try{
            latch.await(); // Wait until count reaches 0
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        System.out.println("All workers finished "+Thread.currentThread().getName()+" thread continues.");
    }
}
