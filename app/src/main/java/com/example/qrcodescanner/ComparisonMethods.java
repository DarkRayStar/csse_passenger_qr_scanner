package com.example.qrcodescanner;

import android.text.InputFilter;
import android.text.Spanned;

public class ComparisonMethods implements InputFilter {

    private int min, max;

    public ComparisonMethods(){};

    public ComparisonMethods(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public ComparisonMethods(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    // This method uses to check the 1-5000 LKR constraint on input fare Edit Text input field
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    // Method to get current balance
    public Float getNewBalance(Float currentBalance, Float fare) {
        return currentBalance - fare;
    }

    public boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}