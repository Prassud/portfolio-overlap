package com.fabric.portfolio.command;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.PortfolioOverlap;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

public class PortFolioOverlapCommand implements ICommand {
    private final InvestorService investorService;

    private final DecimalFormat decimalFormat;

    public PortFolioOverlapCommand(InvestorService investorService, DecimalFormat decimalFormat) {
        this.investorService = investorService;
        this.decimalFormat = decimalFormat;
    }

    @Override
    public CommandResult execute(CommandInput commandInput) {
        String newFundName = commandInput.getInputs().get(0);
        try {
            PortfolioOverlap portfolioOverlap = investorService.calculateOverLap(newFundName);
            List<String> overlapResult = portfolioOverlap.getOverlapResult(decimalFormat);
            return new CommandResult(overlapResult, Status.SUCCESS);
        } catch (FundNotFoundException fundNotFoundException) {
            return new CommandResult(List.of(Constants.CommandResults.FUND_NOT_FOUND), Status.FAILED);
        }
    }
}
