package com.fabric.portfolio.command;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class CurrentPortfolioCommand implements ICommand {

    private final InvestorService investorService;

    public CurrentPortfolioCommand(InvestorService investorService) {
        this.investorService = investorService;
    }

    @Override
    public CommandResult execute(CommandInput commandInput) {
        List<String> inputs = commandInput.getInputs();
        List<String> results = new ArrayList<>();
        CommandResult commandResult = new CommandResult(results, Status.SUCCESS);
        try {
            investorService.addFunds(inputs);
        } catch (FundNotFoundException fundNotFoundException) {
            commandResult.markAsFailed();
            commandResult.addResult(Constants.CommandResults.FUND_NOT_FOUND);
        }
        return commandResult;
    }
}
