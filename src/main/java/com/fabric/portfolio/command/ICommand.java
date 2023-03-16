package com.fabric.portfolio.command;

import com.fabric.portfolio.model.comand.CommandInput;
import com.fabric.portfolio.model.comand.CommandResult;

public interface ICommand {
    CommandResult execute(CommandInput commandInput);
}
