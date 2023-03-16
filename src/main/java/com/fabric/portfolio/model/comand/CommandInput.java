package com.fabric.portfolio.model.comand;

import java.util.List;
import java.util.Objects;

public class CommandInput {

    private final List<String> inputs;

    public CommandInput(List<String> inputs) {
        this.inputs = inputs;
    }

    public List<String> getInputs() {
        return inputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandInput that = (CommandInput) o;
        return Objects.equals(inputs, that.inputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inputs);
    }
}
