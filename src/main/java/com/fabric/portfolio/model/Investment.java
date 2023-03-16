package com.fabric.portfolio.model;

import java.util.Objects;

public final class Investment {
    private final String name;

    private final InvestmentType investmentType;

    public Investment(String name, InvestmentType investmentType) {
        this.name = name;
        this.investmentType = investmentType;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Investment that = (Investment) o;
        return name.equals(that.name) && investmentType == that.investmentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, investmentType);
    }
}
