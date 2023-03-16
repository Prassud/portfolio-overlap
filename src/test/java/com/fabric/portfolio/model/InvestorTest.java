package com.fabric.portfolio.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.fabric.portfolio.utils.TestConstants.ICICI_PRU_NIFTY_NEXT_50_INDEX;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvestorTest {

    @Mock
    private Portfolio portfolio;

    private Investor investor;

    @BeforeEach
    void setUp() {
        initMocks(this);
        investor = new Investor(portfolio);
    }

    @Test
    public void shouldAddFundToInvest(){
        investor.addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX);

        verify(portfolio).addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX);
    }

    @Test
    public void shouldGetInvestments(){
        investor.addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX);

        verify(portfolio).addFund(ICICI_PRU_NIFTY_NEXT_50_INDEX);
    }
}