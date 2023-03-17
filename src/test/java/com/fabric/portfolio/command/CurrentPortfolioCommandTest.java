package com.fabric.portfolio.command;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CurrentPortfolioCommandTest {
    @InjectMocks
    private CurrentPortfolioCommand currentPortfolioCommand;

    @Mock
    private InvestorService investorService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldReturnCommandResultAsSuccessful_OnCurrentPortfolioCommand(){
        List<String> inputs = List.of("firstFund", "secondFund");
        CommandInput input = new CommandInput(inputs);

        CommandResult commandResult = currentPortfolioCommand.execute(input);

        assertNotNull(commandResult);
        assertTrue(commandResult.isSuccessFul());
        assertTrue(commandResult.isEmpty());
        verify(investorService).addFunds(inputs);
    }

    @Test
    public void shouldReturnCommandResultAsFailed_OnCurrentPortfolioCommand(){
        List<String> inputs = List.of("firstFund", "secondFund");
        CommandInput input = new CommandInput(inputs);
        doThrow(new FundNotFoundException("Fund not found")).when(investorService).addFunds(inputs);

        CommandResult commandResult = currentPortfolioCommand.execute(input);

        assertNotNull(commandResult);
        assertFalse(commandResult.isSuccessFul());
        assertFalse(commandResult.isEmpty());
        assertEquals("FUND_NOT_FOUND", commandResult.format());
    }
}