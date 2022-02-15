package src.main.model.account;

import src.main.Trade;
import src.main.utils.Color;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Account {

    private final Map<Trade.Stock, Integer> portfolio;
    private Double fundsAvailable;

    public Account(double fundsAvailable){
        this.fundsAvailable = fundsAvailable;
        this.portfolio = new HashMap<>();
        this.portfolio.put(Trade.Stock.AAPL, 0);
        this.portfolio.put(Trade.Stock.FB, 0);
        this.portfolio.put(Trade.Stock.GOOG, 0);
        this.portfolio.put(Trade.Stock.TSLA, 0);
    }

    public Account(Account source){
        this.portfolio = source.portfolio;
        this.fundsAvailable = source.fundsAvailable;
    }
    private Map<Trade.Stock, Integer> copyMap(Map<Trade.Stock, Integer> map) {
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public abstract Account clone();

    public Integer getShares(Trade.Stock stock) {
         return this.portfolio.get(stock);
    }

    public void setShares(Trade.Stock stock, Integer shares) {
        this.portfolio.put(stock, shares);
    }

    public Double getFundsAvailable() {
        return fundsAvailable;
    }

    public void setFundsAvailable(Double fundsAvailable) {
        this.fundsAvailable = fundsAvailable;
    }

    public abstract boolean makeTrade(Trade trade);

    protected boolean executePurchase(Trade trade, double fee){
        double total = trade.getAmountOfShares() * trade.getStockPrice();
        if(total <= this.getFundsAvailable()){
            this.setFundsAvailable(this.getFundsAvailable() - total - total * fee);
            addShares(trade);
            return true;
        }
        return false;
    }

    protected boolean executeSale(Trade trade, double fee){
        if(trade.getAmountOfShares() <= this.getShares(trade.getStock())){
            this.setShares(trade.getStock(), getCurrentShares(trade.getStock()) - trade.getAmountOfShares());
            addFunds(trade, fee);
            return true;
    }
        return false;
    }

    private String displayPortofolio() {
        StringBuilder string = new StringBuilder();
        for (Map.Entry<Trade.Stock, Integer> entry : portfolio.entrySet()) {
            string.append("  " + Color.BLUE).append(entry.getKey()).append("\t\t");
            string.append(Color.GREEN).append(entry.getValue());
            string.append("\n");
        }
        return string.toString();
    }


    private void addFunds(Trade trade, double fee) {
        double total = trade.getAmountOfShares() * trade.getStockPrice();
        this.setFundsAvailable(this.getFundsAvailable() + total - total * fee);
    }

    private int getCurrentShares(Trade.Stock stock){
        return this.portfolio.get(stock) == null ? 0 : this.portfolio.get(stock);
    }

    private void addShares(Trade trade) {
        int currentShares = getCurrentShares(trade.getStock());
        this.setShares(trade.getStock(), currentShares + trade.getAmountOfShares());
    }

    private double round(double amount){
        DecimalFormat format = new DecimalFormat("#.##");
        return Double.parseDouble(format.format(amount));
    }

    @Override
    public String toString() {
        return "\n  Stock\t\t"  + Color.RESET + "Shares" +
                "\n\n" + displayPortofolio() + Color.RESET +
                "\n  Funds Left\t" + Color.GREEN + "$" + round(this.getFundsAvailable()) + Color.RESET;
    }
}
