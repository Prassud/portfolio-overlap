package com.fabric.portfolio.service;

import com.fabric.portfolio.model.PortfolioOverlap;

import java.util.List;

public interface InvestorService {
    void addFunds(List<String> fundNames);

    PortfolioOverlap calculateOverLap(String fundName);
}
