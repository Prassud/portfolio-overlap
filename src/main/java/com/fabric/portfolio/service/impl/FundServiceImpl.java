package com.fabric.portfolio.service.impl;

import com.fabric.portfolio.service.FundService;
import com.fabric.portfolio.store.FundDao;

public class FundServiceImpl implements FundService {
    private final FundDao fundDao;

    public FundServiceImpl(FundDao fundDao) {
        this.fundDao = fundDao;
    }

    @Override
    public void addStockToFund(String fundName, String stockName) {
        fundDao.addStock(fundName, stockName);
    }
}
