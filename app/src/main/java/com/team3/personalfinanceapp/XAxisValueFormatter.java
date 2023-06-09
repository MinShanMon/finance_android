package com.team3.personalfinanceapp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.Calendar;

public class XAxisValueFormatter extends ValueFormatter {
    private String[] xStrs = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override

    public String getAxisLabel(float value, AxisBase axis) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) ;
        int position = (int) value+month;
        if (position >= 12) {
            position = position-12;
        }
        return xStrs[position];
    }
}

