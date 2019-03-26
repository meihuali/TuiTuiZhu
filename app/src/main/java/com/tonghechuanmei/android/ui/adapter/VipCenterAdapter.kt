/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-11 下午1:02
 */

package com.tonghechuanmei.android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.designer.library.component.databinding.loadImage
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.ui.activity.VipCenterActivity1
import kotlinx.android.synthetic.main.item_horizontal_layout.view.*


class VipCenterAdapter constructor(
    private val context: Context,
    private var list: List<String>,
    private val activity: VipCenterActivity1
) : PagerAdapter() {

    override fun destroyItem(parent: ViewGroup, paramInt: Int, paramObject: Any) {
        parent.removeView(paramObject as View)
    }

    override fun getCount(): Int {
        return this.list.size
    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val rooView = LayoutInflater.from(this.context).inflate(R.layout.item_horizontal_layout, null)
        rooView.iv_item_vip.loadImage(list[position])
        parent.addView(rooView)
        return rooView
    }

    override fun isViewFromObject(paramView: View, paramObject: Any): Boolean {
        return paramView === paramObject
    }
}
