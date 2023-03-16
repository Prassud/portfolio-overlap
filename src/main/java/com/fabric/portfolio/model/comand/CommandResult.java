package com.fabric.portfolio.model.comand;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class CommandResult {
    private final List<String> results;

    private Status status;

    public CommandResult(List<String> results, Status status) {
        this.results = results;
        this.status = status;
    }

    public void markAsFailed() {
        this.status = Status.FAILED;
    }

    public void addResult(String result) {
        if (isNull(results)) {
            throw new RuntimeException("Failed to add command Result " + result);
        }
        results.add(result);
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public String format() {
        return results.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    public boolean isSuccessFul() {
        return Status.SUCCESS.equals(this.status);
    }
}
