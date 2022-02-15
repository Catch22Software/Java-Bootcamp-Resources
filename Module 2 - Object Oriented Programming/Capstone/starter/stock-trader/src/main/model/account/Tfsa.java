package src.main.model.account;

import src.main.Trade;

public class Tfsa extends Account {


    private static final double FEE = 0.01;

    public Tfsa(double funds){
        super(funds);
    }

    public Tfsa(Tfsa source){
        super(source);
    }

    @Override
    public Account clone() {
        return new Tfsa(this.getFundsAvailable());
    }

    @Override
    public boolean makeTrade(Trade trade) {
        if(trade.getType() == Trade.Type.MARKET_BUY){
            return executePurchase(trade,FEE);
        }
        else {
            return executeSale(trade, FEE);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
