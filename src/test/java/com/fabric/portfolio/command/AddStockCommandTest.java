package com.fabric.portfolio.command;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.service.FundService;
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

public class AddStockCommandTest {
    @InjectMocks
    private AddStockCommand addStockCommand;

    @Mock
    private FundService fundService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldReturnCommandResultAsSuccessful_WhenAddStocksToFund(){
        List<String> inputs = List.of("fundName", "stockName");
        CommandInput input = new CommandInput(inputs);


        CommandResult commandResult = addStockCommand.execute(input);

        assertNotNull(commandResult);
        assertTrue(commandResult.isSuccessFul());
        assertTrue(commandResult.isEmpty());
        verify(fundService).addStockToFund("fundName", "stockName");
    }

    @Test
    public void shouldReturnCommandResultAsFailed_WhenAddStocksToFund(){
        List<String> inputs = List.of("fundName", "stockName");
        CommandInput input = new CommandInput(inputs);
        doThrow(new FundNotFoundException("Fund not found")).when(fundService).addStockToFund("fundName", "stockName");

        CommandResult commandResult = addStockCommand.execute(input);

        assertNotNull(commandResult);
        assertFalse(commandResult.isSuccessFul());
        assertFalse(commandResult.isEmpty());
        assertEquals("FUND_NOT_FOUND", commandResult.format());
        verify(fundService).addStockToFund("fundName", "stockName");
    }
}