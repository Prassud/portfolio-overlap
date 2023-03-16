package com.fabric.portfolio.service.impl;

import com.fabric.portfolio.exception.FundNotFoundException;
import com.fabric.portfolio.model.MutualFund;
import com.fabric.portfolio.model.PortfolioOverlap;
import com.fabric.portfolio.service.InvestorService;
import com.fabric.portfolio.store.FundDao;
import com.fabric.portfolio.store.InvestorDao;

import java.util.List;

import static java.util.Objects.isNull;

public class InvestorServiceImpl implements InvestorService {
    private final FundDao fundDao;
    private final InvestorDao investorDao;

    public InvestorServiceImpl(FundDao fundDao, InvestorDao investorDao) {
        this.fundDao = fundDao;
        this.investorDao = investorDao;
    }

    @Override
    public void addFunds(List<String> fundNames) {
        validateFunds(fundNames);
        for (String fundName : fundNames) {
            investorDao.addFund(fundName);
        }
    }

    private void validateFunds(List<String> fundNames) {
        for (String fundName : fundNames) {
            if (!fundDao.isExists(fundName)) {
                throw new FundNotFoundException("Provided Fund name is not found " + fundName);
            }
        }
    }

    @Override
    public PortfolioOverlap calculateOverLap(String fundName) {
        List<String> investments = investorDao.getInvestments();
        List<MutualFund> funds = fundDao.findFundsByNames(investments);
        MutualFund newFund = findFund(fundName);

        return investorDao.calculateOverlap(funds, newFund);
    }

    private MutualFund findFund(String fundName) {
        MutualFund newFund = fundDao.findFundByName(fundName);
        if (isNull(newFund)) {
            throw new FundNotFoundException("Provided Fund name is not found " + fundName);
        }
        return newFund;
    }
}
