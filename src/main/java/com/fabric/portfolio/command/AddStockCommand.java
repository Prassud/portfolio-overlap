package com.fabric.portfolio.command;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import com.fabric.portfolio.service.FundService;
import com.fabric.portfolio.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AddStockCommand implements ICommand {

    private final FundService fundService;

    public AddStockCommand(FundService fundService) {
        this.fundService = fundService;
    }

    @Override
    public CommandResult execute(CommandInput commandInput) {
        List<String> inputs = commandInput.getInputs();
        List<String> results = new ArrayList<>();
        CommandResult commandResult = new CommandResult(results, Status.SUCCESS);
        try {
            String fundName = inputs.get(0);
            String stockName = inputs.get(1);
            fundService.addStockToFund(fundName, stockName);
            return commandResult;
        } catch (FundNotFoundException fundNotFoundException) {
            commandResult.markAsFailed();
            commandResult.addResult(Constants.CommandResults.FUND_NOT_FOUND);
        }
        return commandResult;
    }
}
