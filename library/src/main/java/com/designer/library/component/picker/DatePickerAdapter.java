/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/29/18 12:14 AM
 */

package com.designer.library.component.picker;

import java.text.DecimalFormat;


public class DatePickerAdapter implements PickAdapter {

    private int mMinValue;
    private int mMaxValue;
    private final DecimalFormat mDecimalFormat;

    public DatePickerAdapter(int minValue, int maxValue) {
        this(minValue, maxValue, null);
    }

    public DatePickerAdapter(int minValue, int maxValue, DecimalFormat decimalFormat) {
        this.mMinValue = minValue;
        this.mMaxValue = maxValue;
        this.mDecimalFormat = decimalFormat;
    }

    @Override
    public int getCount() {
        return mMaxValue - mMinValue + 1;
    }

    @Override
    public String getItem(int position) {
        if (position >= 0 && position < getCount()) {
            if (mDecimalFormat == null) {
                return String.valueOf(mMinValue + position);
            } else {
                return mDecimalFormat.format(mMinValue + position);
            }
        }
        return null;
    }

    public int getDate(int position) {
        if (position >= 0 && position < getCount()) {
            return mMinValue + position;
        }
        return 0;
    }

    public int indexOf(String valueString) {
        int value;
        try {
            value = Integer.parseInt(valueString);
        } catch (NumberFormatException e) {
            return -1;
        }
        return indexOf(value);
    }

    public int indexOf(int value) {
        if (value < mMinValue || value > mMaxValue) {
            return -1;
        }
        return value - mMinValue;
    }

    public int getMinValue() {
        return mMinValue;
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMinValue(int minValue) {
        this.mMinValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.mMaxValue = maxValue;
    }

}
