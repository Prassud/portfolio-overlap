package com.fabric.portfolio.io;

import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import com.fabric.portfolio.orchestrator.CommandOrchestrator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FileInputHandlerTest {

    @Mock
    private CommandOrchestrator commandOrchestrator;


    private FileInputHandler fileInputHandler;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("testinput.txt")).getFile();
        fileInputHandler = new FileInputHandler(commandOrchestrator, filePath);
    }

    @Test
    public void shouldParseInputFromFile() throws IOException {
        CommandResult firstResult = new CommandResult(List.of("first"), Status.SUCCESS);
        CommandResult secondResult = new CommandResult(List.of("second"), Status.SUCCESS);
        CommandResult thirdResult = new CommandResult(List.of("third"), Status.SUCCESS);
        CommandResult fourthResult = new CommandResult(List.of("fourth"), Status.SUCCESS);
        CommandResult fifthResult = new CommandResult(List.of("fifth"), Status.SUCCESS);
        when(commandOrchestrator.orchestrate("CURRENT_PORTFOLIO UTI_NIFTY_INDEX AXIS_MIDCAP PARAG_PARIKH_FLEXI_CAP")).thenReturn(firstResult);
        when(commandOrchestrator.orchestrate("CALCULATE_OVERLAP ICICI_PRU_NIFTY_NEXT_50_INDEX")).thenReturn(secondResult);
        when(commandOrchestrator.orchestrate("CALCULATE_OVERLAP NIPPON_INDIA_PHARMA_FUND")).thenReturn(thirdResult);
        when(commandOrchestrator.orchestrate("ADD_STOCK PARAG_PARIKH_FLEXI_CAP NOCIL")).thenReturn(fourthResult);
        when(commandOrchestrator.orchestrate("ADD_STOCK AXIS_MIDCAP NOCIL")).thenReturn(fifthResult);

        List<CommandResult> commandResults = fileInputHandler.handle();

        assertEquals(6, commandResults.size());
        assertEquals(firstResult.format(), commandResults.get(0).format());
        assertEquals(secondResult.format(), commandResults.get(1).format());
        assertEquals(thirdResult.format(), commandResults.get(2).format());
        assertEquals(fourthResult.format(), commandResults.get(3).format());
        assertEquals(fifthResult.format(), commandResults.get(4).format());

        verify(commandOrchestrator).orchestrate("CURRENT_PORTFOLIO UTI_NIFTY_INDEX AXIS_MIDCAP PARAG_PARIKH_FLEXI_CAP");
        verify(commandOrchestrator, times(2)).orchestrate("CALCULATE_OVERLAP ICICI_PRU_NIFTY_NEXT_50_INDEX");
        verify(commandOrchestrator).orchestrate("CALCULATE_OVERLAP NIPPON_INDIA_PHARMA_FUND");
        verify(commandOrchestrator).orchestrate("ADD_STOCK PARAG_PARIKH_FLEXI_CAP NOCIL");
        verify(commandOrchestrator).orchestrate("ADD_STOCK AXIS_MIDCAP NOCIL");
    }
}