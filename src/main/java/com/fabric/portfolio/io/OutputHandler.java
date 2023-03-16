package com.fabric.portfolio.io;

import com.fabric.portfolio.model.comand.CommandResult;

import java.util.List;

public interface OutputHandler {
    void handle(List<CommandResult> results);
}
