import models.Pants;
import models.Product;
import models.Shirt;


public class Main3 {
  
    static final String FILE_NAME = "products.txt";

    public static void main(String[] args) {

        Product[] products = new Product[] {
                new Pants(32, 24.99, "Blue", "JAVA KLEIN"),
                new Pants(34, 104.99, "Red", "JANGLER"),
                new Pants(30, 119.99, "Grey", "FENDI"),
                new Pants(30, 129.99, "Red", "VERSACE"),
                new Pants(29, 99.99, "Dark", "JANGLER"),
                new Pants(26, 24.99, "Indigo", "BELSTAFF"),
                new Pants(34, 104.99, "Red", "JANGLER"),
        };

        printArray(products);

    }

    public static void printArray(Product[] array) {
        for (Product product : array) {
            System.out.println(product);
        }
    }
}
