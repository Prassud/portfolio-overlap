package com.fabric.portfolio.strategy;

import com.fabric.portfolio.model.FundOverlap;
import com.fabric.portfolio.model.MutualFund;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OverlapCalculatorTest {
    @InjectMocks
    private OverlapCalculatorImpl overlapCalculator;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void shouldCalculateOverlap() {
        MutualFund firstFund = Mockito.mock(MutualFund.class);
        MutualFund secondFund = Mockito.mock(MutualFund.class);
        when(firstFund.getName()).thenReturn("firstFund");
        when(firstFund.getAssetsSize()).thenReturn(11);
        when(secondFund.getName()).thenReturn("secondFund");
        when(secondFund.getAssetsSize()).thenReturn(35);
        when(firstFund.getCommonStocksSizeWith(secondFund)).thenReturn(8);

        FundOverlap fundOverlap = overlapCalculator.calculateOverlap(firstFund, secondFund);

        assertEquals("firstFund secondFund 34.78%", fundOverlap.getFormattedString(new DecimalFormat("0.00")));
    }

    @Test
    public void shouldCalculateOverlap_WhenOverlapPercentageIs100() {
        MutualFund firstFund = Mockito.mock(MutualFund.class);
        MutualFund secondFund = Mockito.mock(MutualFund.class);
        when(firstFund.getName()).thenReturn("firstFund");
        when(firstFund.getAssetsSize()).thenReturn(23);
        when(secondFund.getName()).thenReturn("secondFund");
        when(secondFund.getAssetsSize()).thenReturn(23);
        when(secondFund.getCommonStocksSizeWith(firstFund)).thenReturn(23);

        FundOverlap fundOverlap = overlapCalculator.calculateOverlap(secondFund, firstFund);
        assertEquals("secondFund firstFund 100.00%", fundOverlap.getFormattedString(new DecimalFormat("0.00")));
    }
}