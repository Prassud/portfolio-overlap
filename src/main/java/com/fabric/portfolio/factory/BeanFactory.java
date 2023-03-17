package com.fabric.portfolio.factory;

import com.fabric.portfolio.PortfolioOverlapApp;
import com.fabric.portfolio.command.AddStockCommand;
import com.fabric.portfolio.command.CurrentPortfolioCommand;
import com.fabric.portfolio.command.ICommand;
import com.fabric.portfolio.command.PortFolioOverlapCommand;
import com.fabric.portfolio.io.ConsoleOutputHandler;
import com.fabric.portfolio.io.FileInputHandler;
import com.fabric.portfolio.model.Investment;
import com.fabric.portfolio.model.Investor;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.model.Portfolio;
import com.fabric.portfolio.orchestrator.CommandOrchestrator;
import com.fabric.portfolio.service.FundService;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.service.impl.FundServiceImpl;
import com.fabric.portfolio.service.impl.InvestorServiceImpl;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;
import com.fabric.portfolio.store.impl.FundDaoImpl;
import com.fabric.portfolio.store.impl.InvestorDaoImpl;
import com.fabric.portfolio.strategy.OverlapCalculator;
import com.fabric.portfolio.strategy.OverlapCalculatorImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.fabric.portfolio.utils.Constants.CommandNames.ADD_STOCK;
import static com.fabric.portfolio.utils.Constants.CommandNames.CALCULATE_OVERLAP;
import static com.fabric.portfolio.utils.Constants.CommandNames.CURRENT_PORTFOLIO;

public class BeanFactory {
    private static final String FUNDS_JSON = "funds.json";
    private final ObjectMapper objectMapper;

    public BeanFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CommandFactory createCommandFactory(FundService fundService, InvestorService investorService) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        AddStockCommand addStockCommand = new AddStockCommand(fundService);
        CurrentPortfolioCommand currentPortfolioCommand = new CurrentPortfolioCommand(investorService);
        PortFolioOverlapCommand portfolioOverlapCommand = new PortFolioOverlapCommand(investorService, decimalFormat);
        Map<String, ICommand> commandMap = new HashMap<>();
        commandMap.put(CURRENT_PORTFOLIO, currentPortfolioCommand);
        commandMap.put(CALCULATE_OVERLAP, portfolioOverlapCommand);
        commandMap.put(ADD_STOCK, addStockCommand);
        return new CommandFactory(commandMap);
    }

    public CommandOrchestrator createOrchestrator(CommandFactory commandFactory){
        return new CommandOrchestrator(commandFactory);
    }

    public Map<String, MutualFund> createFunds() throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(FUNDS_JSON);
        List<MutualFund> funds = objectMapper.readValue(resourceAsStream, new TypeReference<List<MutualFund>>() {
        });
        return funds.stream().collect(Collectors.toMap(MutualFund::getName, Function.identity()));
    }

    public PortfolioOverlapApp createApp(CommandOrchestrator commandOrchestrator, String inputFileName) {
        FileInputHandler consoleInputHandler = new FileInputHandler(commandOrchestrator, inputFileName);
        ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
        return new PortfolioOverlapApp(consoleInputHandler, consoleOutputHandler);
    }

    public InvestorDao createInvestorDao() {
        Set<Investment> investments = new LinkedHashSet<>();
        OverlapCalculator overlapCalculator = new OverlapCalculatorImpl();
        Portfolio portfolio = new Portfolio(investments, overlapCalculator);
        Investor investor = new Investor(portfolio);
        return new InvestorDaoImpl(investor);
    }

    public FundService createFundService(FundDao fundDao){
        return new FundServiceImpl(fundDao);
    }

    public FundDao createFundDao(Map<String, MutualFund> funds){
        return new FundDaoImpl(funds);
    }

    public InvestorService createInvestorService(FundDao fundDao, InvestorDao investorDao){
        return new InvestorServiceImpl(fundDao, investorDao);
    }
}
