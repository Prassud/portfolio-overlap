package com.fabric.portfolio.model;

import java.text.DecimalFormat;

import static java.lang.Double.parseDouble;

public class FundOverlap {
    private final String firstFundName;

    private final String secondFundName;
    private final double overlapPercentage;

    public FundOverlap(String firstFundName,
                       String secondFundName,
                       double overlapPercentage) {
        this.firstFundName = firstFundName;
        this.secondFundName = secondFundName;
        this.overlapPercentage = overlapPercentage;
    }

    public String getFormattedString(DecimalFormat decimalFormat) {
        String overLoadPercentageString = decimalFormat.format(overlapPercentage);
        return String.format("%s %s %s", firstFundName, secondFundName, overLoadPercentageString + "%");

    }

    public boolean isValid(DecimalFormat decimalFormat) {
        String overLoadPercentageString = decimalFormat.format(overlapPercentage);
        return parseDouble(overLoadPercentageString) > 0;
    }
}
