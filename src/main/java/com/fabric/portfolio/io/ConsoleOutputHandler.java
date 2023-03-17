package com.fabric.portfolio.io;

import com.fabric.portfolio.model.comand.CommandResult;

import java.util.List;

public class ConsoleOutputHandler implements OutputHandler {
    @Override
    public void handle(List<CommandResult> results) {
        StringBuilder resultBuilder = new StringBuilder();
        for (CommandResult result : results) {
            if (!result.isEmpty()) {
                resultBuilder.append(result.format()).append(System.lineSeparator());
            }
        }
        if (resultBuilder.length() != 0) {
            resultBuilder.deleteCharAt(resultBuilder.length()-1);
        }
        System.out.print(resultBuilder);
    }
}
