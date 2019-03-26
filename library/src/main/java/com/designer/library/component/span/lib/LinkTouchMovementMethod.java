/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.span.lib;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 配合 {@link LinkTouchDecorHelper} 使用
 *
 * @author cginechen
 * @date 2017-03-20
 */

public class LinkTouchMovementMethod extends LinkMovementMethod {

    private static LinkTouchMovementMethod sInstance;
    private static LinkTouchDecorHelper sHelper = new LinkTouchDecorHelper();

    public static MovementMethod getInstance() {
        if (sInstance == null) sInstance = new LinkTouchMovementMethod();

        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        return sHelper.onTouchEvent(widget, buffer, event) || Touch.onTouchEvent(widget, buffer, event);
    }

}
