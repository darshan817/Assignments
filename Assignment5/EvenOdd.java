package Assignment5;
class EvenOdd {
    static int n = 1;
    static final int MAX = 20;
    static final Object lock = new Object();

    static class EvenThread extends Thread {
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (n <= MAX) { // Not even, wait while(n<max){
                        if(n % 2 != 0)
                        {
                            try 
                            {
                                lock.wait();
                            } 
                            catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        } else {
                            System.out.println("Even: " + n);
                            n++;
                            lock.notify(); // Wake up the odd thread
                        } // Wake up the odd thread
                    }
                }
            }
        }
    }

    static class OddThread extends Thread {
        public void run() {
            while (true) {
                synchronized (lock) {
                    
                    while (n <= MAX) { // Not odd, wait
                        if (n % 2 == 0){
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                        } 
                        else {
                            System.out.println("Odd: " + n);
                            n++;
                            lock.notify(); // Wake up the even thread
                        }
                    
                }
            }
        }
    }
}


    public static void main(String[] args) {
        OddThread odd = new OddThread();
        EvenThread even = new EvenThread();

        odd.start();
        even.start();
    }
    
}
