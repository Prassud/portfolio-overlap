package com.fabric.portfolio.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PortfolioOverlapTest {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Test
    public void shouldAddFundOverLapToPortFolioOverlap() {
        FundOverlap firstFundOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap secondOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap thirdFund = Mockito.mock(FundOverlap.class);
        List<FundOverlap> fundOverlaps = new ArrayList<>();
        fundOverlaps.add(firstFundOverlap);
        fundOverlaps.add(secondOverlap);

        PortfolioOverlap portfolioOverlap = new PortfolioOverlap(fundOverlaps);

        portfolioOverlap.add(thirdFund);

        assertEquals(3, fundOverlaps.size());
        assertEquals(thirdFund, fundOverlaps.get(2));
    }

    @Test
    public void shouldGetOverlapResults() {
        FundOverlap firstFundOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap secondOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap thirdFund = Mockito.mock(FundOverlap.class);
        List<FundOverlap> fundOverlaps = new ArrayList<>();
        fundOverlaps.add(firstFundOverlap);
        fundOverlaps.add(secondOverlap);
        fundOverlaps.add(thirdFund);
        when(firstFundOverlap.isValid(decimalFormat)).thenReturn(true);
        when(secondOverlap.isValid(decimalFormat)).thenReturn(true);
        when(thirdFund.isValid(decimalFormat)).thenReturn(true);
        when(firstFundOverlap.getFormattedString(decimalFormat)).thenReturn("first fund overlap");
        when(secondOverlap.getFormattedString(decimalFormat)).thenReturn("second fund overlap");
        when(thirdFund.getFormattedString(decimalFormat)).thenReturn("third fund overlap");


        PortfolioOverlap portfolioOverlap = new PortfolioOverlap(fundOverlaps);


        List<String> overlapResult = portfolioOverlap.getOverlapResult(decimalFormat);

        assertEquals(3, overlapResult.size());
        assertEquals("first fund overlap", overlapResult.get(0));
        assertEquals("second fund overlap", overlapResult.get(1));
        assertEquals("third fund overlap", overlapResult.get(2));
        verify(firstFundOverlap).isValid(decimalFormat);
        verify(secondOverlap).isValid(decimalFormat);
        verify(thirdFund).isValid(decimalFormat);
        verify(firstFundOverlap).getFormattedString(decimalFormat);
        verify(secondOverlap).getFormattedString(decimalFormat);
        verify(thirdFund).getFormattedString(decimalFormat);
    }

    @Test
    public void shouldSkipFund_WhenFundOverlapIsInvalid() {
        FundOverlap firstFundOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap secondOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap thirdFund = Mockito.mock(FundOverlap.class);
        List<FundOverlap> fundOverlaps = new ArrayList<>();
        fundOverlaps.add(firstFundOverlap);
        fundOverlaps.add(secondOverlap);
        fundOverlaps.add(thirdFund);
        when(firstFundOverlap.isValid(decimalFormat)).thenReturn(false);
        when(secondOverlap.isValid(decimalFormat)).thenReturn(true);
        when(thirdFund.isValid(decimalFormat)).thenReturn(true);
        when(secondOverlap.getFormattedString(decimalFormat)).thenReturn("second fund overlap");
        when(thirdFund.getFormattedString(decimalFormat)).thenReturn("third fund overlap");


        PortfolioOverlap portfolioOverlap = new PortfolioOverlap(fundOverlaps);


        List<String> overlapResult = portfolioOverlap.getOverlapResult(decimalFormat);

        assertEquals(2, overlapResult.size());
        assertEquals("second fund overlap", overlapResult.get(0));
        assertEquals("third fund overlap", overlapResult.get(1));
        verify(firstFundOverlap).isValid(decimalFormat);
        verify(secondOverlap).isValid(decimalFormat);
        verify(thirdFund).isValid(decimalFormat);
        verify(firstFundOverlap, never()).getFormattedString(any(DecimalFormat.class));
        verify(secondOverlap).getFormattedString(decimalFormat);
        verify(thirdFund).getFormattedString(decimalFormat);
    }


    @Test
    public void shouldSkipAllTheFunds_WhenAllTheFundOverlapIsInvalid() {
        FundOverlap firstFundOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap secondOverlap = Mockito.mock(FundOverlap.class);
        FundOverlap thirdFund = Mockito.mock(FundOverlap.class);
        List<FundOverlap> fundOverlaps = new ArrayList<>();
        fundOverlaps.add(firstFundOverlap);
        fundOverlaps.add(secondOverlap);
        fundOverlaps.add(thirdFund);
        when(firstFundOverlap.isValid(decimalFormat)).thenReturn(false);
        when(secondOverlap.isValid(decimalFormat)).thenReturn(false);
        when(thirdFund.isValid(decimalFormat)).thenReturn(false);

        PortfolioOverlap portfolioOverlap = new PortfolioOverlap(fundOverlaps);

        List<String> overlapResult = portfolioOverlap.getOverlapResult(decimalFormat);

        assertEquals(0, overlapResult.size());
        verify(firstFundOverlap).isValid(decimalFormat);
        verify(secondOverlap).isValid(decimalFormat);
        verify(thirdFund).isValid(decimalFormat);
        verify(firstFundOverlap, never()).getFormattedString(any(DecimalFormat.class));
        verify(secondOverlap, never()).getFormattedString(any(DecimalFormat.class));
        verify(thirdFund, never()).getFormattedString(any(DecimalFormat.class));

    }
}
