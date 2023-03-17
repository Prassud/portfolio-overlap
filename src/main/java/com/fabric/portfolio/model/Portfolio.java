package com.fabric.portfolio.model;

import com.fabric.portfolio.strategy.OverlapCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Portfolio {
    private final Set<Investment> investments;

    private final OverlapCalculator overlapCalculator;

    public Portfolio(Set<Investment> investments, OverlapCalculator overlapCalculator) {
        this.investments = investments;
        this.overlapCalculator = overlapCalculator;
    }

    public PortfolioOverlap calculateOverlap(final List<MutualFund> funds,
                                             final MutualFund secondMutualFund) {
        final List<FundOverlap> fundOverlaps = new ArrayList<>();
        final PortfolioOverlap portfolioOverlap = new PortfolioOverlap(fundOverlaps);
        for (MutualFund fund : funds) {
            FundOverlap fundOverlap = overlapCalculator.calculateOverlap(secondMutualFund, fund);
            portfolioOverlap.add(fundOverlap);
        }
        return portfolioOverlap;
    }

    public boolean addFund(final String fundName) {
        Investment investment = new Investment(fundName, InvestmentType.MUTUAL_FUNDS);
        if (this.isNewFund(investment)) {
            return this.investments.add(investment);
        }
        return false;
    }

    private boolean isNewFund(Investment investment) {
        return !investments.contains(investment);
    }

    public List<String> getInvestmentNames() {
        List<String> investmentNames = new ArrayList<>();
        for (Investment investment : investments) {
            String name = investment.getName();
            investmentNames.add(name);
        }
        return investmentNames;
    }
}
