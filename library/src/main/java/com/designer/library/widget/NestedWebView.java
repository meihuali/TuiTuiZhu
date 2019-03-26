/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 支持被ScrollView嵌套的WebView
 */
public class NestedWebView extends WebView {

    @SuppressLint("NewApi")
    public NestedWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NestedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedWebView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
