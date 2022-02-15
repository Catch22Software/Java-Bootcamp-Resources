package src.test;

import org.junit.Before;
import org.junit.Test;
import src.main.Trade;
import src.main.model.account.Account;
import src.main.model.account.Personal;
import src.main.model.account.Tfsa;

import static org.junit.Assert.assertEquals;

public class SellTests {
    
    Account[] accounts;
    Trade sellTrade;
    Trade buyTrade;

    @Before
    public void setUp(){
        accounts = new Account[]{
                new Personal(1000)
                ,new Tfsa(1000)
        };
        sellTrade = new Trade(Trade.Stock.GOOG, Trade.Type.MARKET_SELL, 1, 25.00);
        buyTrade = new Trade(Trade.Stock.GOOG, Trade.Type.MARKET_BUY, 1, 25.00);
    }

    @Test
    public void sellPersonal(){
        accounts[0].makeTrade(buyTrade);
        accounts[0].makeTrade(sellTrade);
        assertEquals((int) accounts[0].getShares(Trade.Stock.GOOG), 0);
    }

    @Test
    public void sellTfsa(){
        accounts[1].makeTrade(buyTrade);
        accounts[1].makeTrade(sellTrade);
        assertEquals((int) accounts[1].getShares(Trade.Stock.GOOG), 0);
    }

    @Test
    public void sellExcessShares(){
        accounts[0].makeTrade(sellTrade);
        assertEquals( accounts[1].getFundsAvailable(), 1000.,0);
    }

    @Test
    public void sellPersonalFundsDecrease(){
        accounts[0].makeTrade(buyTrade);
        accounts[0].makeTrade(sellTrade);
        assertEquals(accounts[0].getFundsAvailable(),998.75,0);
    }

    @Test
    public void sellTfsaIncrease(){
        accounts[1].makeTrade(buyTrade);
        accounts[1].makeTrade(sellTrade);
        assertEquals(accounts[1].getFundsAvailable(),999.5,0);
    }

}
