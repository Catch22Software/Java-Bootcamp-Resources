package src.main;

public class Trade {

    public enum Stock{AAPL,FB,GOOG,TSLA}
    public enum Type{MARKET_BUY, MARKET_SELL}

    private Stock stock;
    private Type type;
    private int amountOfShares;
    private double stockPrice;

    public Trade(Stock stock, Type type, int amountOfShares, double stockPrice){
        this.stock = stock;
        this.type = type;
        this.amountOfShares = amountOfShares;
        this.stockPrice = stockPrice;
    }

    public Trade(Trade source){
        this.stock = source.stock;
        this.type = source.type;
        this.amountOfShares = source.amountOfShares;
        this.stockPrice = source.stockPrice;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getAmountOfShares() {
        return amountOfShares;
    }

    public void setAmountOfShares(int amountOfShares) {
        this.amountOfShares = amountOfShares;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }
}
