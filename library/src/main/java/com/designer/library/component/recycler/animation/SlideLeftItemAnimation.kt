/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.recycler.animation

import android.animation.ObjectAnimator
import android.view.View


class SlideLeftItemAnimation : BaseItemAnimation {

    override fun onItemEnterAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "translationX", -view.rootView.width.toFloat(), 0F)
            .setDuration(300)
            .start()
    }
}
