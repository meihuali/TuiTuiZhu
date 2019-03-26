/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.recycler.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View


class ScaleItemAnimation @JvmOverloads constructor(private val mFrom: Float = DEFAULT_SCALE_FROM) : BaseItemAnimation {

    override fun onItemEnterAnimation(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 300
        animatorSet.start()
    }

    companion object {

        private val DEFAULT_SCALE_FROM = .5f
    }
}
