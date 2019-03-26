/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.recycler.animation

import android.view.View


interface BaseItemAnimation {

    /**
     * 处理item被添加的时候的进入动画
     *
     * @param view item view
     */
    fun onItemEnterAnimation(view: View)
}
