package src.main.model;

import src.main.model.account.Account;
import src.main.model.account.Chequing;
import src.main.model.account.impl.Taxable;
import src.test.TransactionTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {

    private final ArrayList<Account> accounts;
    private final ArrayList<Transaction> transactions;

    public Bank(){
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public void addAccount(Account account){
        this.accounts.add(account.clone());
    }

    private void addTransaction(Transaction transaction){
        this.transactions.add(new Transaction(transaction));
    }

    public Transaction[] getTransactions(String accountId){
        return this.transactions.stream()
                .filter((transaction -> transaction.getId().equals(accountId))).toArray(Transaction[]::new);
    }

    public Account getAccount(String transactionId){
        return accounts.stream()
                .filter((account -> account.getId().equals(transactionId)))
                .findFirst()
                .orElse(null);
    }

    private void withdrawTransaction(Transaction transaction){
        if (getAccount(transaction.getId()).withdraw(transaction.getAmount())){
            addTransaction(transaction);
        }
    }

    public void executeTransaction(Transaction transaction){
        switch (transaction.getType()){
            case WITHDRAW: withdrawTransaction(transaction); break;
            case DEPOSIT: depositTransaction(transaction); break;
        }
    }

    private void depositTransaction(Transaction transaction){
        getAccount(transaction.getId()).deposit(transaction.getAmount());
        addTransaction(transaction);
    }

    private double getIncome(Taxable account){
        Transaction[] transactions = getTransactions(((Chequing)account).getId());
        return Arrays.stream(transactions).mapToDouble((t) -> {
            switch (t.getType()){
                case WITHDRAW: return -t.getAmount();
                case DEPOSIT: return t.getAmount();
                default: return 0;
            }
        }).sum();
    }

    public void deductTaxes(){
        for(Account account: accounts){
            if(Taxable.class.isAssignableFrom(account.getClass())){
                Taxable taxable = (Taxable) account;
                taxable.tax(getIncome(taxable));
            }
        }
    }


  
}
