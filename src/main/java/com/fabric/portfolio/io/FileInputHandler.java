package com.fabric.portfolio.io;

import com.fabric.portfolio.orchestrator.CommandOrchestrator;
import com.fabric.portfolio.model.comand.CommandResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileInputHandler implements InputHandler {

    private final CommandOrchestrator commandOrchestrator;
    private final String inputFileName;

    public FileInputHandler(CommandOrchestrator commandOrchestrator, String inputFileName) {
        this.commandOrchestrator = commandOrchestrator;
        this.inputFileName = inputFileName;
    }

    public List<CommandResult> handle() throws IOException {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
        List<CommandResult> commandResults = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                break;
            }
            CommandResult commandResult = commandOrchestrator.orchestrate(line);
            commandResults.add(commandResult);
        }
        return commandResults;
    }
}
