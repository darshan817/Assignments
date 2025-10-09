package Assignment6;
import java.util.concurrent.Semaphore;

public class ParkingLotSimulation {
    // Semaphore with 5 permits (5 parking spots)
    private static final Semaphore parkingSpots = new Semaphore(5);

    static class Car extends Thread {
        private int carId;

        public Car(int carId) {
            this.carId = carId;
        }

        @Override
        public void run() {
            try {
                System.out.println("Car " + carId + " is trying to park.");
                
                // Acquire a parking spot
                parkingSpots.acquire();
                System.out.println("Car " + carId + " has parked.");

                // Simulate parking time
                Thread.sleep((long)(Math.random() * 5000));

                System.out.println("Car " + carId + " is leaving.");
                
                // Release the parking spot
                parkingSpots.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Create 10 cars
        for (int i = 1; i <= 10; i++) {
            Car car = new Car(i);
            car.start();
        }
    }
}
