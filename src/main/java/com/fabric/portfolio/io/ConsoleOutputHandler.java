package com.fabric.portfolio.io;

import com.fabric.portfolio.model.comand.CommandResult;

import java.util.List;

public class ConsoleOutputHandler implements OutputHandler {
    @Override
    public void handle(List<CommandResult> results) {
        for (CommandResult result : results) {
            if (!result.isEmpty()) {
                System.out.println(result.format());
            }
        }


    }
}
