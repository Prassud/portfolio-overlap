package com.fabric.portfolio.orchestrator;

import com.fabric.portfolio.command.AddStockCommand;
import com.fabric.portfolio.exception.CommandNotFoundException;
import com.fabric.portfolio.factory.CommandFactory;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandOrchestratorTest {

    @InjectMocks
    private CommandOrchestrator commandOrchestrator;

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private AddStockCommand addStockCommand;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldExecuteMockCOmmand() {
        List<String> commandInputs = List.of("dummy", "secondary");
        CommandInput commandInput = new CommandInput(commandInputs);
        CommandResult commandResult = new CommandResult(List.of("dummy", "dummy"), Status.SUCCESS);

        when(commandFactory.getCommand("testCommand")).thenReturn(addStockCommand);
        when(addStockCommand.execute(commandInput)).thenReturn(commandResult);
        CommandResult result = commandOrchestrator.orchestrate("testCommand dummy secondary");

        assertNotNull(result);
        assertEquals(commandResult.format(), result.format());
        verify(commandFactory).getCommand("testCommand");
        verify(addStockCommand).execute(commandInput);
    }

    @Test
    public void shouldThrowCommandNotFoundException() {
        try {
            commandOrchestrator.orchestrate("testCommand dummy secondary");
        } catch (CommandNotFoundException commandNotFoundException) {
            assertEquals("Failed to find the command testCommand", commandNotFoundException.getMessage());
            verify(commandFactory).getCommand("testCommand");
            verify(addStockCommand, never()).execute(any(CommandInput.class));
        }
    }
}