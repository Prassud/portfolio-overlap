package com.fabric.portfolio.factory;

import com.fabric.portfolio.command.AddStockCommand;
import com.fabric.portfolio.command.ICommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandFactoryTest {

    @InjectMocks
    private CommandFactory commandFactory;

    @Mock
    private Map<String, ICommand> commandMap;

    @Mock
    private AddStockCommand addStockCommand;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldGetCommandToExecuteFromCommandMap(){
        when(commandMap.get("testCommand")).thenReturn(addStockCommand);

        assertEquals(addStockCommand, commandFactory.getCommand("testCommand"));
    }

    @Test
    public void shouldReturnNullWhenCommandIsNotFound(){
        assertNull(commandFactory.getCommand("testCommand"));
    }


}