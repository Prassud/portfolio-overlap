package com.fabric.portfolio;

import com.fabric.portfolio.factory.CommandFactory;
import com.fabric.portfolio.loader.ApplicationLoader;
import com.fabric.portfolio.loader.BeanFactory;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.orchestrator.CommandOrchestrator;
import com.fabric.portfolio.service.FundService;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.service.impl.FundServiceImpl;
import com.fabric.portfolio.service.impl.InvestorServiceImpl;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;
import com.fabric.portfolio.store.impl.FundDaoImpl;
import com.fabric.portfolio.store.impl.InvestorDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ApplicationLoaderTest {

    @Mock
    private BeanFactory commandLoader;

    @InjectMocks
    private ApplicationLoader applicationLoader;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void shouldLoadOverlapApp() throws IOException {
        FundService fundService = Mockito.mock(FundServiceImpl.class);
        InvestorService investorService = Mockito.mock(InvestorServiceImpl.class);
        InvestorDao mockInvestorDao = Mockito.mock(InvestorDaoImpl.class);
        FundDao fundDao = Mockito.mock(FundDaoImpl.class);
        CommandFactory commandFactory = Mockito.mock(CommandFactory.class);
        HashMap<String, MutualFund> funds = new HashMap<>();
        PortfolioOverlapApp app = mock(PortfolioOverlapApp.class);
        CommandOrchestrator commandOrchestrator = mock(CommandOrchestrator.class);

        when(commandLoader.createCommandFactory(fundService, investorService)).thenReturn(commandFactory);
        when(commandLoader.createFunds()).thenReturn(funds);
        when(commandLoader.createInvestorDao()).thenReturn(mockInvestorDao);
        when(commandLoader.createApp(commandOrchestrator, "input.txt")).thenReturn(app);
        when(commandLoader.createCommandFactory(fundService, investorService)).thenReturn(commandFactory);
        when(commandLoader.createFundService(fundDao)).thenReturn(fundService);
        when(commandLoader.createFundDao(funds)).thenReturn(fundDao);
        when(commandLoader.createOrchestrator(commandFactory)).thenReturn(commandOrchestrator);
        when(commandLoader.createInvestorService(fundDao, mockInvestorDao)).thenReturn(investorService);

        PortfolioOverlapApp bootstrap = applicationLoader.bootstrap("input.txt");

        assertNotNull(bootstrap);

        verify(commandLoader).createCommandFactory(fundService, investorService);
        verify(commandLoader).createFunds();
        verify(commandLoader).createInvestorDao();
        verify(commandLoader).createApp(commandOrchestrator, "input.txt");
        verify(commandLoader).createCommandFactory(fundService, investorService);
        verify(commandLoader).createFundService(fundDao);
        verify(commandLoader).createFundDao(funds);
        verify(commandLoader).createOrchestrator(commandFactory);
        verify(commandLoader).createInvestorService(fundDao, mockInvestorDao);
    }
}