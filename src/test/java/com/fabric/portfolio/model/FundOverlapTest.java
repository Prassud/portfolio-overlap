package com.fabric.portfolio.model;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

public class FundOverlapTest {

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Test
    public void shouldCreateNewFundOverlap() {
        FundOverlap fundOverlap = new FundOverlap("first", "second", 25.2);

        assertTrue(fundOverlap.isValid(decimalFormat));
        assertEquals("first second 25.20%", fundOverlap.getFormattedString(decimalFormat));
    }

    @Test
    public void shouldReturnFalseIfOverlapPercentageIsLessThanEqualToZero() {
        FundOverlap fundOverlap = new FundOverlap("first", "second", 0.000);

        assertFalse(fundOverlap.isValid(decimalFormat));
    }

    @Test
    public void shouldReturnFalseIfOverlapPercentageIsLessThanEqualToZeroWithdecimal() {
        FundOverlap fundOverlap = new FundOverlap("first", "second", 0.00111);

        assertFalse(fundOverlap.isValid(decimalFormat));
    }

    @Test
    public void shouldReturnTrueIfOverlapPercentageIsLessThanEqualToZeroWithDecimal_BasedOnDecimalFormat() {
        FundOverlap fundOverlap = new FundOverlap("first", "second", 0.00111);
        DecimalFormat decimalFormat = new DecimalFormat("0.00000");

        assertTrue(fundOverlap.isValid(decimalFormat));
    }
}