package src.main.model.account;

import src.main.Trade;

public class Personal extends Account{

    private static final double SELL_FEE = 0.05;

    public Personal(double funds){
        super(funds);
    }

    @Override
    public Account clone() {
        return new Personal(this.getFundsAvailable());
    }

    @Override
    public boolean makeTrade(Trade trade) {
        return Trade.Type.MARKET_BUY == trade.getType()
                ? super.executePurchase(trade, 0)
                : super.executeSale(trade, SELL_FEE);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
