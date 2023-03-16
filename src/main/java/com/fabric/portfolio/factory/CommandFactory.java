package com.fabric.portfolio.factory;

import com.fabric.portfolio.command.ICommand;

import java.util.Map;

public class CommandFactory {
    private final Map<String, ICommand> commandMap;

    public CommandFactory(Map<String, ICommand> commandMap) {
        this.commandMap = commandMap;
    }

    public ICommand getCommand(String commandName) {
        return commandMap.get(commandName);
    }
}

