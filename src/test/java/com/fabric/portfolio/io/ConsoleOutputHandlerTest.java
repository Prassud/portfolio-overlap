package com.fabric.portfolio.io;

import com.fabric.portfolio.model.comand.CommandResult;
import com.fabric.portfolio.model.comand.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleOutputHandlerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldValidateOutputContecnt() {
        CommandResult firstResult = new CommandResult(List.of("first"), Status.SUCCESS);
        CommandResult secondResult = new CommandResult(List.of("second"), Status.SUCCESS);
        CommandResult thirdResult = new CommandResult(List.of("third"), Status.SUCCESS);
        CommandResult fourthResult = new CommandResult(List.of("fourth"), Status.SUCCESS);
        CommandResult fifthResult = new CommandResult(List.of("fifth"), Status.SUCCESS);

        consoleOutputHandler.handle(List.of(firstResult, secondResult, thirdResult, fourthResult, fifthResult));

        assertEquals("first\n" +
                "second\n" +
                "third\n" +
                "fourth\n" +
                "fifth\n", outContent.toString());

    }
}