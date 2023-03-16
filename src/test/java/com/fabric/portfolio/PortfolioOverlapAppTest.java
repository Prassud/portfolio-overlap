package com.fabric.portfolio;

import com.fabric.portfolio.io.ConsoleOutputHandler;
import com.fabric.portfolio.io.FileInputHandler;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PortfolioOverlapAppTest {

    @Mock
    private ConsoleOutputHandler consoleOutputHandler;

    @Mock
    private FileInputHandler fileInputHandler;

    @InjectMocks
    private PortfolioOverlapApp portfolioOverlapApp;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldStartExecution() throws IOException {
        List<CommandResult> results = List.of(new CommandResult(List.of("s"), Status.SUCCESS));
        when(fileInputHandler.handle()).thenReturn(results);
        portfolioOverlapApp.start();

        verify(fileInputHandler).handle();
        verify(consoleOutputHandler).handle(results);
    }
}