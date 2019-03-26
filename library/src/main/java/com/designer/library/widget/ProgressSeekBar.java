/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不支持划动的SeekBar
 */
public class ProgressSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    public ProgressSeekBar(Context context) {
        super(context);
    }

    public ProgressSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
