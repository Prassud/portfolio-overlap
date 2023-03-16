package com.fabric.portfolio.store;

import com.fabric.portfolio.model.MutualFund;

import java.util.List;

public interface FundDao {
    void addStock(String fundName, String stockName);

    boolean isExists(String fundName);

    MutualFund findFundByName(String name);

    List<MutualFund> findFundsByNames(List<String> fundNames);
}
