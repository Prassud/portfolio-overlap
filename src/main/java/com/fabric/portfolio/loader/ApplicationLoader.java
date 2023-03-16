package com.fabric.portfolio.loader;

import com.fabric.portfolio.PortfolioOverlapApp;
import com.fabric.portfolio.orchestrator.CommandOrchestrator;
import com.fabric.portfolio.factory.CommandFactory;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.service.FundService;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;

import java.io.IOException;
import java.util.Map;

public class ApplicationLoader {

    private final BeanFactory beanFactory;

    public ApplicationLoader(BeanFactory commandLoader) {
        this.beanFactory = commandLoader;
    }

    public PortfolioOverlapApp bootstrap(String inputFileName) throws IOException {
        Map<String, MutualFund> funds = beanFactory.createFunds();
        InvestorDao investorDao = beanFactory.createInvestorDao();
        FundDao fundDao = beanFactory.createFundDao(funds);
        FundService fundService = beanFactory.createFundService(fundDao);
        InvestorService investorService = beanFactory.createInvestorService(fundDao, investorDao);
        CommandFactory commandFactory = beanFactory.createCommandFactory(fundService, investorService);
        CommandOrchestrator commandOrchestrator = beanFactory.createOrchestrator(commandFactory);
        return beanFactory.createApp(commandOrchestrator, inputFileName);
    }
}
