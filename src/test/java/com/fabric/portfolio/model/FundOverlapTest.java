package com.fabric.portfolio.model;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

public class FundOverlapTest {


    @Test
    void shouldCreateNewFundOverlap() {
        FundOverlap fundOverlap = new FundOverlap("first", "second", 25.2);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        assertEquals("first second 25.20%", fundOverlap.getFormattedString(decimalFormat));
    }
}