package com.fabric.portfolio.loader;

import com.fabric.portfolio.command.AddStockCommand;
import com.fabric.portfolio.command.CurrentPortfolioCommand;
import com.fabric.portfolio.command.PortFolioOverlapCommand;
import com.fabric.portfolio.factory.CommandFactory;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.orchestrator.CommandOrchestrator;
import com.fabric.portfolio.service.FundService;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;

import static com.fabric.portfolio.utils.Constants.CommandNames.ADD_STOCK;
import static com.fabric.portfolio.utils.Constants.CommandNames.CALCULATE_OVERLAP;
import static com.fabric.portfolio.utils.Constants.CommandNames.CURRENT_PORTFOLIO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class BeanFactoryTest {


    @InjectMocks
    BeanFactory beanFactory;

    @Mock
    private FundDao fundDao;

    @Mock
    private InvestorDao investorDao;


    @Mock
    private InvestorService investorService;

    @Mock
    private FundService fundService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldCreateInverstorService() {
        InvestorService investorService = beanFactory.createInvestorService(fundDao, investorDao);

        assertNotNull(investorService);
    }

    @Test
    public void shouldCreateFundService() {
        FundService fundService = beanFactory.createFundService(fundDao);

        assertNotNull(fundService);
    }


    @Test
    public void shouldCreateCommandFactory() {
        CommandFactory commandFactory = beanFactory.createCommandFactory(fundService, investorService);

        assertNotNull(commandFactory);
        assertNotNull(commandFactory.getCommand(CURRENT_PORTFOLIO));
        assertTrue(commandFactory.getCommand(CURRENT_PORTFOLIO) instanceof CurrentPortfolioCommand);
        assertNotNull(commandFactory.getCommand(CALCULATE_OVERLAP));
        assertTrue(commandFactory.getCommand(CALCULATE_OVERLAP) instanceof PortFolioOverlapCommand);
        assertNotNull(commandFactory.getCommand(ADD_STOCK));
        assertTrue(commandFactory.getCommand(ADD_STOCK) instanceof AddStockCommand);
    }

    @Test
    public void shouldCreateCommandOrchestrator() {
        CommandFactory commandFactory = Mockito.mock(CommandFactory.class);
        CommandOrchestrator commandOrchestrator = beanFactory.createOrchestrator(commandFactory);

        assertNotNull(commandOrchestrator);
    }

    @Test
    public void shouldCreateFunds() throws IOException {
        BeanFactory beanFactory = new BeanFactory(new ObjectMapper());
        Map<String, MutualFund> funds = beanFactory.createFunds();

        assertEquals(10, funds.size());
    }
}