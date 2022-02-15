import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.*;


public class Main2 {

    static final String SALES = "data/sales.csv"; //Use backslash Windows users
    static double furniture = 0;
    static double technology = 0;
    static double supplies = 0;
    static double average = 0;

    public static void main(String[] args) {
        
        try {

            int nThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(nThreads);
            Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource(SALES).toURI());
            Future<Double> future = executor.submit(() -> average(path, "Furniture"));
            Future<Double> future1 = executor.submit(() -> average(path, "Technology"));
            Future<Double> future2 = executor.submit(() -> average(path, "Office Supplies"));
            Future<Double> future3 = executor.submit(() -> totalAverage(path));
            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter your name to access the Global Superstore dataset: ");
            String name = scan.nextLine();

            furniture = future.get();
            technology = future1.get();
            supplies = future2.get();
            average = future3.get();
            executor.shutdown();
            System.out.println("\nThank you " + name + ". The average sales for Global Superstore are:\n");
            System.out.println("Average Furniture Sales: " + furniture);
            System.out.println("Average Technology Sales: " + technology);
            System.out.println("Average Office Supplies Sales: " + supplies);
            System.out.println("Total Average: " + average);
            scan.close();

        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function name: average
     * @param path (Path)
     * @param category (String)
     * @return Double
     * 
     * Inside the function:
     *   1. Runs through every line from the CSV file as a stream.
     *   2. Maps every element in the stream to an array of three String values.
     *   3. Filters every value by the @param category
     *   4. Maps every element in the stream to a double (price * quantity).
     *   5. Applies the terminal operation average.
     *   6. Returns the average as double.
     * 
     */

    public static Double average(Path path, String category){
        try{
            return Files.lines(path)
                    .skip(1)
                    .map((line) -> line.split(","))
                    .filter((values) -> values[0].equals(category))
                    .mapToDouble((values) -> Double.parseDouble(values[1]) * Double.parseDouble(values[2]))
                    .average()
                    .getAsDouble();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return 0.0;
        }
    }



    /**
     * Function name: totalAverage
     * @param path (Path)
     * @return Double
     * 
     * Inside the function:
     *   1. Runs through every line from the CSV file as a stream.
     *   2. Maps every element in the stream to an array of three values.
     *   3. Maps every element in the stream to a double: (price * quantity).
     *   4. Applies the terminal operation average.
     *   5. Returns the average as double.
     */

    public static Double totalAverage(Path path){
        try {
            return Files.lines(path)
                    .skip(1)
                    .map((line) -> line.split(","))
                    .mapToDouble((value) -> Double.parseDouble(value[1]) * Double.parseDouble(value[2]))
                    .average()
                    .getAsDouble();
        }catch (IOException e){
            System.out.println(e.getMessage());
            return 0.0;
        }
    }
  

}
