package com.fabric.portfolio.store.impl;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.store.FundDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class FundDaoImpl implements FundDao {
    private final Map<String, MutualFund> funds;

    public FundDaoImpl(Map<String, MutualFund> funds) {
        this.funds = funds;
    }

    public void addStock(String fundName, String stockName) {
        MutualFund fund = findFundByName(fundName);
        isFundNull(fund, fundName);
        fund.addStock(stockName);
    }

    @Override
    public boolean isExists(String fundName) {
        return findFundByName(fundName) != null;
    }

    @Override
    public MutualFund findFundByName(String fundName) {
        return funds.get(fundName);
    }

    @Override
    public List<MutualFund> findFundsByNames(List<String> fundNames) {
        List<MutualFund> funds = new ArrayList<>();
        fundNames.forEach(investedFundName -> {
            MutualFund investedFund = findFundByName(investedFundName);
            isFundNull(investedFund, investedFundName);
            funds.add(investedFund);
        });
        return funds;
    }

    private void isFundNull(MutualFund investedFund, String fundName) {
        if (isNull(investedFund)) {
            throw new FundNotFoundException("Fund not found for " + fundName);
        }
    }
}
