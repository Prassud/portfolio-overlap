package com.fabric.portfolio.model;

import com.fabric.portfolio.strategy.OverlapCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.fabric.portfolio.utils.TestConstants.ICICI_PRU_NIFTY_NEXT_50_INDEX;
import static com.fabric.portfolio.utils.TestConstants.ICICI_PRU_NIFTY_NEXT_700_INDEX;
import static com.fabric.portfolio.utils.TestConstants.ICICI_PRU_NIFTY_NEXT_750_INDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PortfolioTest {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    @Mock
    private OverlapCalculator overlapCalculator;
    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        initMocks(this);
        portfolio = new Portfolio(new LinkedHashSet<>(), overlapCalculator);
    }

    @Test
    public void shouldAddFundToPortfolio() {
        assertTrue(portfolio.addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX));
        assertTrue(portfolio.addFund(ICICI_PRU_NIFTY_NEXT_700_INDEX));
    }

    @Test
    public void shouldNotDuplicateFundToPortfolio() {
        assertTrue(portfolio.addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX));
        assertFalse(portfolio.addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX));
    }

    @Test
    public void shouldCalculateOverlap() {
        List<MutualFund> mutualFunds = buildMutualFunds();
        ArrayList<String> thirdStocks = new ArrayList<>();
        thirdStocks.add("WIPRO");
        MutualFund thirdMutualFund = new MutualFund(ICICI_PRU_NIFTY_NEXT_750_INDEX, thirdStocks);
        FundOverlap firstOverlap = new FundOverlap(ICICI_PRU_NIFTY_NEXT_750_INDEX, ICICI_PRU_NIFTY_NEXT_50_INDEX, 98.6);
        FundOverlap secondOverlap = new FundOverlap(ICICI_PRU_NIFTY_NEXT_750_INDEX, ICICI_PRU_NIFTY_NEXT_700_INDEX, 95.5);
        when(overlapCalculator.calculateOverlap(thirdMutualFund, mutualFunds.get(0))).thenReturn(firstOverlap);
        when(overlapCalculator.calculateOverlap(thirdMutualFund, mutualFunds.get(1))).thenReturn(secondOverlap);

        PortfolioOverlap portfolioOverlap = portfolio.calculateOverlap(mutualFunds, thirdMutualFund);

        verify(overlapCalculator).calculateOverlap(thirdMutualFund, mutualFunds.get(0));
        verify(overlapCalculator).calculateOverlap(thirdMutualFund, mutualFunds.get(1));
        assertEquals(2, portfolioOverlap.getOverlapResult(DECIMAL_FORMAT).size());
        assertEquals("ICICI_PRU_NIFTY_NEXT_750_INDEX ICICI_PRU_NIFTY_NEXT_50_INDEX 98.60%", portfolioOverlap.getOverlapResult(DECIMAL_FORMAT).get(0));
        assertEquals("ICICI_PRU_NIFTY_NEXT_750_INDEX ICICI_PRU_NIFTY_NEXT_700_INDEX 95.50%", portfolioOverlap.getOverlapResult(DECIMAL_FORMAT).get(1));

    }

    private static List<MutualFund> buildMutualFunds() {
        ArrayList<String> firstStocks = new ArrayList<>();
        firstStocks.add("TCS");
        firstStocks.add("WIPRO");
        ArrayList<String> secondStocks = new ArrayList<>();
        secondStocks.add("WIPRO");
        MutualFund firstMutualFund = new MutualFund(ICICI_PRU_NIFTY_NEXT_50_INDEX, firstStocks);
        MutualFund secondMutualFund = new MutualFund(ICICI_PRU_NIFTY_NEXT_700_INDEX, secondStocks);
        List<MutualFund> mutualFunds = List.of(firstMutualFund, secondMutualFund);
        return mutualFunds;
    }

    @Test
    public void shouldGetInvestmentNames() {
        portfolio.addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX);
        portfolio.addFund(ICICI_PRU_NIFTY_NEXT_700_INDEX);

        List<String> investmentNames = portfolio.getInvestmentNames();

        assertEquals(2, investmentNames.size());
        assertEquals(ICICI_PRU_NIFTY_NEXT_50_INDEX, investmentNames.get(0));
        assertEquals(ICICI_PRU_NIFTY_NEXT_700_INDEX, investmentNames.get(1));
    }
}