package src.main;

import src.main.model.Bank;
import src.main.model.Transaction;
import src.main.model.account.Account;
import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainBank {

     static String ACCOUNTS_FILE = "Module 2 - Object Oriented Programming/10. Inheritance/challenge/bank-management/src/main/data/accounts.txt";
     static String TRANSACTIONS_FILE = "Module 2 - Object Oriented Programming/10. Inheritance/challenge/bank-management/src/main/data/transactions.txt";

     static Bank bank = new Bank();

    public static void main(String[] args) {

        try {
            ArrayList<Account> accounts = returnAccounts();
            loadAccounts(accounts);

            ArrayList<Transaction> transactions = returnTransactions();
            runTransactions(transactions);
            bank.deductTaxes();
            for (Account account : accounts) {
                System.out.println("\n\t\t\t\t\t ACCOUNT\n\n\t"+account+"\n\n");
                transactionHistory(account.getId());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Account createObject(String[] values) {
        try{
            return (Account) Class.forName("src.main.model.account." + values[0])
                    .getConstructor(String.class, String.class, double.class)
                    .newInstance(values[1], values[2], Double.parseDouble(values[3]));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static void loadAccounts(ArrayList<Account> accounts){
        for(Account account: accounts){
            bank.addAccount(account);
        }
    }

    public static ArrayList<Account> returnAccounts() throws Exception {
        FileInputStream fis = new FileInputStream(ACCOUNTS_FILE);
        Scanner scan = new Scanner(fis);

        ArrayList<Account> accounts = new ArrayList<>();

        while (scan.hasNextLine()){
            accounts.add(createObject(scan.nextLine().split(",")));
        }
        scan.close();
        return accounts;
    }

    public static ArrayList<Transaction> returnTransactions() throws FileNotFoundException{
        FileInputStream fis = new FileInputStream(TRANSACTIONS_FILE);
        Scanner scan = new Scanner(fis);
        ArrayList<Transaction> transactions = new ArrayList<>();

        while(scan.hasNextLine()){
            String[] values = scan.nextLine().split(",");
            transactions.add(new Transaction((Transaction.Type.valueOf(values[1]))
                    ,Long.parseLong(values[0])
                    ,values[2]
                    ,Double.parseDouble(values[3])));
        }
        Collections.sort(transactions);
        scan.close();
        return transactions;
    }

    public static void runTransactions(ArrayList<Transaction> transactions){
        for(Transaction transaction: transactions){
            bank.executeTransaction(transaction);
        }
    }

    public static void transactionHistory(String id){
        System.out.println("\t\t\t\t   TRANSACTION HISTORY\n\t");
        for(Transaction transaction: bank.getTransactions(id)){
            wait(300);
            System.out.println("\t"+transaction+"\n");
        }
        System.out.println("\n\t\t\t\t\tAFTER TAX\n");
        System.out.println("\t" +bank.getAccount(id)+"\n\n\n\n");
    }

    /**
     * Function name: wait
     * @param milliseconds
     * 
     * Inside the function:
     *  1. Makes the code sleep for X milliseconds.
     */

     public static void wait(int milliseconds) {
         try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
         } catch (InterruptedException e) {
             System.out.println(e.getMessage());
         }
     }

}
