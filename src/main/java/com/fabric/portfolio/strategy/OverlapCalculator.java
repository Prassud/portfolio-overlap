package com.fabric.portfolio.strategy;

import com.fabric.portfolio.model.FundOverlap;
import com.fabric.portfolio.model.MutualFund;

public interface OverlapCalculator {
    FundOverlap calculateOverlap(MutualFund firstFund, MutualFund secondFund);
}
