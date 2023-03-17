package com.fabric.portfolio.model.comand;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandResultTest {
    @Test
    public void shouldAddResults() {
        List<String> results = new ArrayList<>();
        CommandResult commandResult = new CommandResult(results, Status.SUCCESS);

        commandResult.addResult("new result");
        commandResult.addResult("second new  result");

        assertEquals("new result\n" +
                "second new  result", commandResult.format());
        assertEquals(2, results.size());
        assertEquals("new result", results.get(0));
        assertEquals("second new  result", results.get(1));
    }

    @Test
    public void shouldMarkAsFailed() {
        List<String> results = new ArrayList<>();
        CommandResult commandResult = new CommandResult(results, Status.SUCCESS);

        commandResult.markAsFailed();

        assertFalse(commandResult.isSuccessFul());;
        assertTrue(commandResult.isEmpty());;
    }
}