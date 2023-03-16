package com.fabric.portfolio.io;

import com.fabric.portfolio.model.comand.CommandResult;

import java.io.IOException;
import java.util.List;

public interface InputHandler {
    List<CommandResult> handle() throws IOException;
}
