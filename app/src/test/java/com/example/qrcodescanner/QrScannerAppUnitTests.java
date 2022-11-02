package com.example.qrcodescanner;

import org.junit.Test;

import static org.junit.Assert.*;

public class QrScannerAppUnitTests {

    @Test
    public void assertGetNewBalanceOne() {

        ComparisonMethods comparisonMethods = new ComparisonMethods();

        Float currentBalance = 1520.50f;
        Float fare = 520.50f;
        Float expectedBalance = 1000.00f;

        Float valueFromFunction = comparisonMethods.getNewBalance(currentBalance, fare);

        assertEquals(expectedBalance, valueFromFunction, 0.00f);

    }

    @Test
    public void assertGetNewBalanceTwo() {

        ComparisonMethods comparisonMethods = new ComparisonMethods();

        Float currentBalance = 1000.00f;
        Float fare = 520.50f;
        Float expectedBalance = 479.50f;

        Float valueFromFunction = comparisonMethods.getNewBalance(currentBalance, fare);

        assertEquals(expectedBalance, valueFromFunction, 0.00f);

    }

    @Test
    public void assertIsInRangeOne() {

        ComparisonMethods comparisonMethods = new ComparisonMethods();

        int valueStart = 10;
        int valueEnd = 100;
        int valueMiddle = 50;

        boolean outputFromFunction = comparisonMethods.isInRange(valueStart, valueEnd, valueMiddle);

        assertEquals(true, outputFromFunction);

    }

    @Test
    public void assertIsInRangeTwo() {

        ComparisonMethods comparisonMethods = new ComparisonMethods();

        int valueStart = 10;
        int valueEnd = 100;
        int valueMiddle = 110;

        boolean outputFromFunction = comparisonMethods.isInRange(valueStart, valueEnd, valueMiddle);

        assertEquals(false, outputFromFunction);

    }

}