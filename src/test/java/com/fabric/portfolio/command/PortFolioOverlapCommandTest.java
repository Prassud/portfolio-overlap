package com.fabric.portfolio.command;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.PortfolioOverlap;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.service.InvestorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.text.DecimalFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PortFolioOverlapCommandTest {
    @InjectMocks
    private PortFolioOverlapCommand portFolioOverlapCommand;

    @Mock
    private InvestorService investorService;

    @Mock
    private DecimalFormat decimalFormat;


    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldReturnCommandResultAsSuccessful(){
        List<String> inputs = List.of("firstFund");
        CommandInput input = new CommandInput(inputs);
        PortfolioOverlap portfolioOverlap = Mockito.mock(PortfolioOverlap.class);
        when(portfolioOverlap.getOverlapResult(decimalFormat)).thenReturn(List.of("DUMMY","DUMMY"));
        when(investorService.calculateOverLap("firstFund")).thenReturn(portfolioOverlap);

        CommandResult commandResult = portFolioOverlapCommand.execute(input);

        assertNotNull(commandResult);
        assertTrue(commandResult.isSuccessFul());
        assertFalse(commandResult.isEmpty());
        assertEquals("DUMMY\n" +
                "DUMMY", commandResult.format());
        verify(investorService).calculateOverLap("firstFund");
        verify(portfolioOverlap).getOverlapResult(decimalFormat);
    }

    @Test
    public void shouldReturnCommandResultWithEmptyResults_WhenPortfolioOverlapResultsReturnEmptyLists(){
        List<String> inputs = List.of("firstFund");
        CommandInput input = new CommandInput(inputs);
        PortfolioOverlap portfolioOverlap = Mockito.mock(PortfolioOverlap.class);
        when(portfolioOverlap.getOverlapResult(decimalFormat)).thenReturn(List.of());
        when(investorService.calculateOverLap("firstFund")).thenReturn(portfolioOverlap);

        CommandResult commandResult = portFolioOverlapCommand.execute(input);

        assertNotNull(commandResult);
        assertTrue(commandResult.isSuccessFul());
        assertTrue(commandResult.isEmpty());
        assertEquals("", commandResult.format());
        verify(investorService).calculateOverLap("firstFund");
        verify(portfolioOverlap).getOverlapResult(decimalFormat);
    }

    @Test
    public void shouldReturnCommandResultAsFailed(){
        List<String> inputs = List.of("firstFund", "secondFund");
        CommandInput input = new CommandInput(inputs);
        when(investorService.calculateOverLap("firstFund")).thenThrow(new FundNotFoundException("Fund not found"));

        CommandResult commandResult = portFolioOverlapCommand.execute(input);

        assertNotNull(commandResult);
        assertFalse(commandResult.isSuccessFul());
        assertFalse(commandResult.isEmpty());
        assertEquals("FUND_NOT_FOUND", commandResult.format());
        verify(investorService).calculateOverLap("firstFund");
    }
}