package com.fabric.portfolio.strategy;

import com.fabric.portfolio.model.FundOverlap;
import com.fabric.portfolio.model.MutualFund;

public class OverlapCalculatorImpl implements OverlapCalculator {
    public FundOverlap calculateOverlap(MutualFund firstFund, MutualFund secondFund) {
        int noOfCommonStockSize = firstFund.getCommonStocksSizeWith(secondFund);
        final int total = firstFund.getAssetsSize() + secondFund.getAssetsSize();
        double overlapPercentage = ((2 * (double) noOfCommonStockSize) / (double) total) * 100;

        return new FundOverlap(firstFund.getName(), secondFund.getName(), overlapPercentage);
    }
}
