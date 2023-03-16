package com.fabric.portfolio.orchestrator;

import com.fabric.portfolio.command.ICommand;
import com.fabric.portfolio.exception.CommandNotFoundException;
import com.fabric.portfolio.factory.CommandFactory;
import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class CommandOrchestrator {
    private final CommandFactory commandFactory;

    public CommandOrchestrator(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public CommandResult orchestrate(String requestContent) {
        String[] request = requestContent.split(" ");
        CommandInput commandInput = createCommandInput(request);
        String commandName = request[0];
        ICommand command = commandFactory.getCommand(commandName);
        if (isNull(command)) {
            throw new CommandNotFoundException("Failed to find the command " + commandName);
        }
        return command.execute(commandInput);
    }

    private CommandInput createCommandInput(String[] request) {
        List<String> inputs = new ArrayList<>();
        for (int index = 1; index < request.length; index++) {
            inputs.add(request[index]);
        }
        return new CommandInput(inputs);
    }
}
