package src.test;

import org.junit.Before;
import org.junit.Test;
import src.main.Trade;
import src.main.model.account.Account;
import src.main.model.account.Personal;
import src.main.model.account.Tfsa;

import static org.junit.Assert.assertEquals;

public class BuyTests {
    
    Account[] accounts;
    Trade buyTrade;

    @Before
    public void setUp(){
        accounts = new Account[]{
                new Personal(1000)
                ,new Tfsa(1000)
        };
        buyTrade = new Trade(Trade.Stock.GOOG, Trade.Type.MARKET_BUY, 1, 25.00);
    }

    @Test
    public void purchaseTestPersonal(){
        accounts[0].makeTrade(buyTrade);
        assertEquals((int) accounts[0].getShares(Trade.Stock.GOOG),1);
    }

    @Test
    public void purchaseTestTfsa(){
        accounts[1].makeTrade(buyTrade);
        assertEquals((int) accounts[1].getShares(Trade.Stock.GOOG),1);
    }

    @Test
    public void invalidPurchase(){
        accounts[0].setFundsAvailable(0.);
        accounts[1].setFundsAvailable(0.);
        accounts[0].makeTrade(buyTrade);
        accounts[1].makeTrade(buyTrade);
        assertEquals((int)accounts[0].getShares(Trade.Stock.GOOG), 0);
        assertEquals((int)accounts[1].getShares(Trade.Stock.GOOG), 0);
    }

    @Test
    public void fundsDecrease(){
        accounts[0].makeTrade(buyTrade);
        assertEquals((double) accounts[0].getFundsAvailable(),975.00,0);
    }

    @Test
    public void fundsDecreaseTfsa(){
        accounts[1].makeTrade(buyTrade);
        assertEquals(accounts[1].getFundsAvailable(),(1000 - 25 - (25. * .01)),0);
    }


}
