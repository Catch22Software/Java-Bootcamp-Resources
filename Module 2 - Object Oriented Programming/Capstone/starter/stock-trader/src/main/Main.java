package src.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

import src.main.model.account.Account;
import src.main.model.account.Personal;
import src.main.model.account.Tfsa;
import src.main.utils.Color;

public class Main {

    static Account account; 
    static final double INITIAL_DEPOSIT = 4000;
    static Scanner scanner = new Scanner(System.in);
  
    public static void main(String[] args) {    
        explainApp();
        String choose = accountChoice();
        if(choose.equals("a"))
            account = new Personal(INITIAL_DEPOSIT);
        else
            account = new Tfsa(INITIAL_DEPOSIT);
        initialBalance();
        for (int day = 1; day <= 2160; day++) {
            displayPrices(day);

            String nextChoice = buyOrSell();
            String stock = chooseStock();
            int share = numShares(nextChoice);
            String result = account.makeTrade(new Trade(
                    Trade.Stock.valueOf(stock),
                    nextChoice.equals("buy") ? Trade.Type.MARKET_BUY : Trade.Type.MARKET_SELL,
                    share,
                    Double.parseDouble(getPrice(Trade.Stock.valueOf(stock), day))
            )) ? "successful" : "unsuccessful";
            tradeStatus(result);

        }

        scanner.close();

    }

    public static void explainApp() {
        System.out.print(Color.BLUE + "\n - PERSONAL: ");
        System.out.println(Color.YELLOW + "Every sale made in a personal account is charged a 5% fee.");
        System.out.print(Color.BLUE + "\n - TFSA: ");
        System.out.println(Color.YELLOW + "Every trade (buy/sell) made from a TFSA is charged a 1% fee.\n");
        System.out.println(Color.BLUE + " - Neither account has a limit on the amount of trades that can be made." + Color.RESET);
    }
    
    public static void initialBalance() {
        System.out.print("\n\n  You created a " + Color.YELLOW + account.getClass().getSimpleName() + Color.RESET + " account.");
        System.out.println(" Your account balance is " + Color.GREEN + "$" + account.getFundsAvailable() + Color.RESET);
        System.out.print("\n  Enter anything to start trading: ");
        scanner.nextLine();
    }


    public static String accountChoice() {
        System.out.print("\n  Respectively, type 'a' or 'b' to create a Personal account or TFSA: ");
        String choice = scanner.nextLine();
        while (!choice.equals("a") && !choice.equals("b")) {
            System.out.print("  Respectively, type 'a' or 'b' to create a Personal account or TFSA: ");
            choice = scanner.nextLine();
        }
        return choice;
    }
    
    
    public static String buyOrSell() {
        System.out.print("\n\n  Would you like to 'buy' or 'sell': ");
        String choice = scanner.nextLine();
        while (!choice.equals("buy") && !choice.equals("sell")) {
            System.out.print("  Would you like to 'buy' or 'sell': ");
            choice = scanner.nextLine();
        }
        return choice;
    }

    public static String chooseStock() {
        System.out.print("  Choose a stock: ");
        String stock = scanner.nextLine(); 
        while (!stock.equals("AAPL") && !stock.equals("FB") && !stock.equals("GOOG") && !stock.equals("TSLA") ) {
            System.out.print("  Choose a stock: ");
            stock = scanner.nextLine();
        }
        return stock;
    }

    public static int numShares(String choice) {
        System.out.print("  Enter the number of shares you'd like to " + choice + ": ");
        int shares = scanner.nextInt(); 
        scanner.nextLine(); //throwaway nextLine
        while (shares <= 0) {
            System.out.print("  Enter the number of shares you'd like to " + choice + ": ");
            shares = scanner.nextInt();
            scanner.nextLine(); //throwaway nextLine

        }
        return shares;
    }

    public static void displayPrices(int day) {
        System.out.println("\n\n\t  DAY " + day + " PRICES\n");

        System.out.println("  " + Color.BLUE + Trade.Stock.AAPL + "\t\t" + Color.GREEN + getPrice(Trade.Stock.AAPL, day));
        System.out.println("  " + Color.BLUE + Trade.Stock.FB + "\t\t" + Color.GREEN + getPrice(Trade.Stock.FB, day));
        System.out.println("  " + Color.BLUE + Trade.Stock.GOOG + "\t\t" + Color.GREEN + getPrice(Trade.Stock.GOOG, day));
        System.out.println("  " + Color.BLUE + Trade.Stock.TSLA + "\t\t" + Color.GREEN + getPrice(Trade.Stock.TSLA, day) + Color.RESET);

    }
    public static void tradeStatus(String result) {
        System.out.println("\n  The trade was " + (result.equals("successful") ? Color.GREEN : Color.RED) + result + Color.RESET + ". Here is your portfolio:");
        System.out.println(account);
        System.out.print("\n  Press anything to continue");
        scanner.nextLine();
    }
    

    public static String getPrice(Trade.Stock stock, int day) {
        Path path = getPath(stock.toString());
        try {
            return Files.lines(path)
                    .skip(1)
                    .filter((line) -> Integer.valueOf(line.split(",")[0]) == day)
                    .map((line) -> line.split(",")[1])
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static Path getPath(String stock) {
        try {
            return Paths.get(Thread.currentThread().getContextClassLoader().getResource("src/main/data/"+stock+".csv").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


}
