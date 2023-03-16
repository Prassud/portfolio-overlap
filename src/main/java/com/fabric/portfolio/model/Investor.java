package com.fabric.portfolio.model;

import java.util.List;

public class Investor {
    private final Portfolio portfolio;

    public Investor(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public boolean addFund(String fundName) {
        return portfolio.addFund(fundName);
    }

    public PortfolioOverlap calculateOverlap(List<MutualFund> currentMutualFunds, MutualFund mutualFund) {
        return portfolio.calculateOverlap(currentMutualFunds, mutualFund);
    }

    public List<String> getInvestments() {
        return portfolio.getInvestmentNames();
    }
}
