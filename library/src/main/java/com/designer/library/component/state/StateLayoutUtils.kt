/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 11:03 PM
 */

package com.designer.library.component.state

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

fun Activity.state(): StateLayout {
    val view = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    return view.state()
}

fun Fragment.state(): StateLayout {
    return view!!.state()
}

fun View.state(): StateLayout {
    val stateLayout = StateLayout(context)
    val parent = parent as ViewGroup
    stateLayout.id = id
    val index = parent.indexOfChild(this)
    parent.removeView(this)
    val layoutParams = layoutParams
    parent.addView(stateLayout, index, layoutParams)

    if (this is ConstraintLayout) {
        val contentViewLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        stateLayout.addView(this, contentViewLayoutParams)
    } else {
        stateLayout.addView(this)
    }
    stateLayout.setContentView(this)

    return stateLayout
}