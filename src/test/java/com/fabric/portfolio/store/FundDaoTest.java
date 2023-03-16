package com.fabric.portfolio.store;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.store.impl.FundDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FundDaoTest {

    @InjectMocks
    private FundDaoImpl assetDao;

    @Mock
    private Map<String, MutualFund> fundMap;

    @Mock
    MutualFund mockFund;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldAddStocksToFundSuccessfully() {
        when(fundMap.get("newFund")).thenReturn(mockFund);
        assetDao.addStock("newFund", "stock");

        verify(mockFund).addStock("stock");
        verify(fundMap).get("newFund");
    }

    @Test
    public void shouldThrowFundNotFoundException_WhenFundIsNotAvailable() {
        try {
            assetDao.addStock("newFund", "stock");
            fail("Expected FundNotFoundException");
        } catch (FundNotFoundException fundNotFoundException) {
            assertEquals("Fund not found for newFund", fundNotFoundException.getMessage());
            verify(fundMap).get("newFund");
            verify(mockFund, never()).addStock("stock");
        }
    }

    @Test
    public void shouldReturnTrueWhenFundExistsInFunds() {
        MutualFund mockFund = Mockito.mock(MutualFund.class);
        when(fundMap.get("newFund")).thenReturn(mockFund);

        assertTrue(assetDao.isExists("newFund"));

        verify(fundMap).get("newFund");
    }

    @Test
    public void shouldReturnFalseWhenFundDoesNotExistsInFunds() {
        assertFalse(assetDao.isExists("newFund"));

        verify(fundMap).get("newFund");
    }

    @Test
    public void shouldFindFundsByNames() {
        MutualFund firstFund = Mockito.mock(MutualFund.class);
        MutualFund secondFund = Mockito.mock(MutualFund.class);
        when(fundMap.get("firstFund")).thenReturn(firstFund);
        when(fundMap.get("secondFund")).thenReturn(secondFund);

        List<MutualFund> expectedFunds = assetDao.findFundsByNames(List.of("firstFund", "secondFund"));

        assertEquals(expectedFunds, List.of(firstFund, secondFund));
        verify(fundMap).get("firstFund");
        verify(fundMap).get("secondFund");
    }

    @Test
    public void shouldFilterFundsIfNotFound() {
        MutualFund firstFund = Mockito.mock(MutualFund.class);
        MutualFund secondFund = Mockito.mock(MutualFund.class);
        when(fundMap.get("firstFund")).thenReturn(firstFund);
        when(fundMap.get("secondFund")).thenReturn(secondFund);

        try {
            assetDao.findFundsByNames(List.of("firstFund", "secondFund", "thirdFund"));
            fail("Failed to throw Fund not Found exception");
        } catch (FundNotFoundException fundNotFoundException) {
            verify(fundMap).get("firstFund");
            verify(fundMap).get("secondFund");
            verify(fundMap).get("thirdFund");
        }
    }
}