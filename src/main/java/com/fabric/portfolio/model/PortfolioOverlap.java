package com.fabric.portfolio.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PortfolioOverlap {
    private final List<FundOverlap> fundOverlaps;

    public PortfolioOverlap(List<FundOverlap> fundOverlaps) {
        this.fundOverlaps = fundOverlaps;
    }

    public void add(FundOverlap fundOverlap) {
        this.fundOverlaps.add(fundOverlap);
    }

    public List<String> getOverlapResult(DecimalFormat decimalFormat) {
        List<String> overlapResult = new ArrayList<>();
        for (FundOverlap fundOverlap : fundOverlaps) {
            if (fundOverlap.isValid(decimalFormat)) {
                String formattedString = fundOverlap.getFormattedString(decimalFormat);
                overlapResult.add(formattedString);
            }
        }
        return overlapResult;
    }
}
