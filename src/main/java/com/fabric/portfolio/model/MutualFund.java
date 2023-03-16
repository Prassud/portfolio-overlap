package com.fabric.portfolio.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MutualFund {
    private String name;

    public MutualFund() {
    }

    public MutualFund(String name, List<String> stocks) {
        this.name = name;
        this.stocks = stocks;
    }

    public List<String> getStocks() {
        return stocks;
    }

    private List<String> stocks;

    public String getName() {
        return name;
    }

    public void addStock(String stockName) {
        this.stocks.add(stockName);
    }

    public int getAssetsSize() {
        return stocks.size();
    }

    public int getCommonStocksSizeWith(MutualFund mutualFund) {
        return this.getCommonStocksWith(mutualFund).size();
    }

    private Set<String> getCommonStocksWith(MutualFund mutualFund) {
        HashSet<String> firstAssets = new HashSet<>(stocks);
        Set<String> secondAssets = new HashSet<>(mutualFund.stocks);
        firstAssets.retainAll(secondAssets);
        return firstAssets;
    }
}
