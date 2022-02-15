import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    static double[] targets = new double[]{0.5, 0.8, 0.3};
    static final double PRECISION = 0.0000001;
    static double result = 0.0;

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Double> future = executor.submit(() -> generateNumber(0));
        Future<Double> future1 = executor.submit(() -> generateNumber(1));
        Future<Double> future2 = executor.submit(() -> generateNumber(2));


        try {
            future.get();
            future1.get();
            future2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
        System.out.println("Finished running background operations");
    }

    /**
     * Function name: generateNumber
     * @return double
     * 
     * Inside the function:
     *   1. Generates a number close to the TARGET to a precision of PRECISION.
     * 
     */

    public static Double generateNumber(int index){
        double number = Math.random();
        double difference = difference(number, index);
        while (difference > PRECISION){
            number = Math.random();
            difference = difference(number, index);
        }
        return number;

    }

    public static double difference(double number, int index){
        return Math.abs(targets[index] - number);
    }

}
