package com.fabric.portfolio.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.fabric.portfolio.utils.TestConstants.ICICI_PRU_NIFTY_NEXT_50_INDEX;
import static com.fabric.portfolio.utils.TestConstants.ICICI_PRU_NIFTY_NEXT_700_INDEX;
import static org.junit.jupiter.api.Assertions.*;


public class MutualFundTest {

    @Test
    public void shouldAddStockToMutualFund() {
        ArrayList<String> stocks = new ArrayList<>();
        MutualFund mutualFund = new MutualFund(ICICI_PRU_NIFTY_NEXT_50_INDEX, stocks);

        mutualFund.addStock("TCS");
        assertEquals(1, mutualFund.getAssetsSize());
        assertEquals(ICICI_PRU_NIFTY_NEXT_50_INDEX, mutualFund.getName());
        assertEquals("TCS", mutualFund.getStocks().get(0));
        assertEquals(stocks, mutualFund.getStocks());
    }

    @Test
    public void shouldGetCommonStockSizeWith() {
        ArrayList<String> firstStocks = new ArrayList<>();
        firstStocks.add("TCS");
        firstStocks.add("WIPRO");
        ArrayList<String> secondStocks = new ArrayList<>();
        secondStocks.add("WIPRO");
        MutualFund firstMutualFund = new MutualFund(ICICI_PRU_NIFTY_NEXT_50_INDEX, firstStocks);
        MutualFund secondMutualFund = new MutualFund(ICICI_PRU_NIFTY_NEXT_700_INDEX, secondStocks);

        int commonStocksSizeWith = firstMutualFund.getCommonStocksSizeWith(secondMutualFund);
        assertEquals(1, commonStocksSizeWith);
    }
}