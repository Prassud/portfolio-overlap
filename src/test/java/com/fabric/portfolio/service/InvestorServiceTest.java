package com.fabric.portfolio.service;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.FundOverlap;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.model.PortfolioOverlap;
import com.fabric.portfolio.service.impl.InvestorServiceImpl;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvestorServiceTest {

    @InjectMocks
    private InvestorServiceImpl investorService;

    @Mock
    private FundDao fundDao;
    @Mock
    private InvestorDao investorDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldAddFundsToInvestor() {
        List<String> fundNames = List.of("firstFund", "secondFund");
        when(fundDao.isExists("firstFund")).thenReturn(true);
        when(fundDao.isExists("secondFund")).thenReturn(true);
        when(investorDao.addFund("firstFund")).thenReturn(true);
        when(investorDao.addFund("secondFund")).thenReturn(true);

        investorService.addFunds(fundNames);

        verify(fundDao).isExists("firstFund");
        verify(fundDao).isExists("secondFund");
        verify(investorDao).addFund("firstFund");
        verify(investorDao).addFund("secondFund");
    }

    @Test
    public void shouldThrowFundNotFoundException_whenFundIsNotFund() {
        List<String> fundNames = List.of("firstFund", "secondFund");
        try {
            investorService.addFunds(fundNames);
            fail("Failed to throw fund not found Exception");
        } catch (FundNotFoundException fundNotFoundException) {
            verify(fundDao).isExists("firstFund");
            verify(fundDao, never()).isExists("secondFund");
            verify(investorDao, never()).addFund("firstFund");
            verify(investorDao, never()).addFund("secondFund");
        }
    }

    @Test
    public void shouldCalculateOverlap() {
        List<String> investedFunds = List.of("firstFund", "secondFund");
        MutualFund firstFund = Mockito.mock(MutualFund.class);
        MutualFund secondFund = Mockito.mock(MutualFund.class);
        MutualFund thirdFund = Mockito.mock(MutualFund.class);
        FundOverlap fundOverlap = Mockito.mock(FundOverlap.class);
        List<FundOverlap> fundOverlaps = List.of(fundOverlap);
        PortfolioOverlap expectedPortfolioOverlap = new PortfolioOverlap(fundOverlaps);
        List<MutualFund> expectedMutualFunds = List.of(firstFund, secondFund);

        when(investorDao.getInvestments()).thenReturn(investedFunds);
        when(fundDao.findFundsByNames(investedFunds)).thenReturn(expectedMutualFunds);
        when(fundDao.findFundByName("thirdFund")).thenReturn(thirdFund);
        when(investorDao.calculateOverlap(expectedMutualFunds, thirdFund)).thenReturn(expectedPortfolioOverlap);

        PortfolioOverlap portfolioOverlap = investorService.calculateOverLap("thirdFund");

        verify(investorDao).calculateOverlap(expectedMutualFunds, thirdFund);
        verify(fundDao).findFundsByNames(List.of("firstFund", "secondFund"));
        verify(fundDao).findFundByName("thirdFund");
        verify(investorDao).calculateOverlap(expectedMutualFunds, thirdFund);
        assertEquals(expectedPortfolioOverlap, portfolioOverlap);
    }


    @Test
    public void shouldCalculateOverlap_WhenThirdFundIsNotFound() {
        List<String> investedFunds = List.of("firstFund", "secondFund");
        MutualFund firstFund = Mockito.mock(MutualFund.class);
        MutualFund secondFund = Mockito.mock(MutualFund.class);
        MutualFund thirdFund = Mockito.mock(MutualFund.class);
        FundOverlap fundOverlap = Mockito.mock(FundOverlap.class);
        List<FundOverlap> fundOverlaps = List.of(fundOverlap);
        List<MutualFund> expectedMutualFunds = List.of(firstFund, secondFund);

        when(investorDao.getInvestments()).thenReturn(investedFunds);
        when(fundDao.findFundsByNames(investedFunds)).thenReturn(expectedMutualFunds);
        when(fundDao.findFundByName("thirdFund")).thenThrow(new FundNotFoundException("Fund not found"));

        try {
            investorService.calculateOverLap("thirdFund");
            fail("Failed to throw fund not found Exception");
        } catch (FundNotFoundException fundNotFoundException) {
            verify(investorDao, never()).calculateOverlap(expectedMutualFunds, thirdFund);
            verify(fundDao).findFundsByNames(List.of("firstFund", "secondFund"));
            verify(fundDao).findFundByName("thirdFund");
        }
    }


    @Test
    public void shouldCalculateOverlap_WhenInvestedFundsAreNotFound() {
        List<String> investedFunds = List.of("firstFund", "secondFund");
        when(investorDao.getInvestments()).thenReturn(investedFunds);
        when(fundDao.findFundsByNames(investedFunds)).thenThrow(new FundNotFoundException("Fund not found"));

        try {
            investorService.calculateOverLap("thirdFund");
            fail("Failed to throw fund not found Exception");
        } catch (FundNotFoundException fundNotFoundException) {
            verify(investorDao, never()).calculateOverlap(any(List.class), any(MutualFund.class));
            verify(fundDao).findFundsByNames(List.of("firstFund", "secondFund"));
            verify(fundDao, never()).findFundByName(any(String.class));
        }
    }
}