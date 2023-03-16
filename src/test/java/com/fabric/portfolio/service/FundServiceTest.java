package com.fabric.portfolio.service;

import com.fabric.portfolio.service.impl.FundServiceImpl;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class FundServiceTest {

    @InjectMocks
    private FundServiceImpl assetService;

    @Mock
    private FundDao fundDao;
    @Mock
    private InvestorDao investorDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldAddStocksToMutualFund(){
        assetService.addStockToFund("fundName", "stockName");

        Mockito.verify(fundDao).addStock("fundName", "stockName");
    }
}