package com.fabric.portfolio.store.impl;

import com.fabric.portfolio.model.Investor;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.model.PortfolioOverlap;
import com.fabric.portfolio.store.InvestorDao;

import java.util.List;

public class InvestorDaoImpl implements InvestorDao {
    private final Investor investor;

    public InvestorDaoImpl(Investor investor) {
        this.investor = investor;
    }

    @Override
    public boolean addFund(String fundName) {
        return investor.addFund(fundName);
    }

    @Override
    public PortfolioOverlap calculateOverlap(List<MutualFund> funds, MutualFund newFund) {
        return investor.calculateOverlap(funds, newFund);
    }

    @Override
    public List<String> getInvestments() {
        return investor.getInvestments();
    }
}
