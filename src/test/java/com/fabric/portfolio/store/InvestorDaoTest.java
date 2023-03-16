package com.fabric.portfolio.store;

import com.fabric.portfolio.model.Investor;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.store.impl.InvestorDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;


public class InvestorDaoTest {

    @InjectMocks
    private InvestorDaoImpl investorDao;

    @Mock
    private Investor investor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldAddFundsToInvestor() {
        MutualFund firstMutulaFund = new MutualFund();
        MutualFund secondMutulaFund = new MutualFund();
        MutualFund newMutualFund = new MutualFund();

        List<MutualFund> mutualFunds = List.of(firstMutulaFund, secondMutulaFund);
        investorDao.calculateOverlap(mutualFunds, newMutualFund);

        Mockito.verify(investor).calculateOverlap(mutualFunds, newMutualFund);
    }


    @Test
    public void shouldGetInvestmentsAssocitatedWithInvestor() {
        investorDao.getInvestments();

        Mockito.verify(investor).getInvestments();
    }

    @Test
    public void shouldCalculateOverlap() {
        investorDao.addFund("fundName");

        Mockito.verify(investor).addFund("fundName");
    }

}