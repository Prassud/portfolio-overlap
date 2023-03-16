package com.fabric.portfolio;

import com.fabric.portfolio.io.InputHandler;
import com.fabric.portfolio.io.OutputHandler;
import com.fabric.portfolio.loader.ApplicationLoader;
import com.fabric.portfolio.loader.BeanFactory;
import com.fabric.portfolio.model.comand.CommandResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class PortfolioOverlapApp {
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public PortfolioOverlapApp(InputHandler inputHandler,
                               OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void start() throws IOException {
        List<CommandResult> results = inputHandler.handle();
        outputHandler.handle(results);
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String inputFileName = args[0];
        BeanFactory beanFactory = new BeanFactory(objectMapper);
        ApplicationLoader applicationLoader = new ApplicationLoader(beanFactory);
        PortfolioOverlapApp overlapApp = applicationLoader.bootstrap(inputFileName);
        overlapApp.start();
    }
}