package com.fabric.portfolio.store;

import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.model.PortfolioOverlap;

import java.util.List;

public interface InvestorDao {
    boolean addFund(String fundName);

    List<String> getInvestments();

    PortfolioOverlap calculateOverlap
            (List<MutualFund> funds, MutualFund newFund);

}