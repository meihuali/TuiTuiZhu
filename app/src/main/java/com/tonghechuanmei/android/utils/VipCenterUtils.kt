/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 下午8:30
 */

package com.tonghechuanmei.android.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.tonghechuanmei.android.R

/**
 * Author     : majingcheng
 * Date       : 2018/10/5 11:30
 */
class VipCenterUtils {
    fun setTab(tab: TabLayout, context: Context, tabIndicators: ArrayList<String>, index: Int) {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tv = tab.customView?.findViewById<TextView>(R.id.text1)
                val line = tab.customView?.findViewById<FrameLayout>(R.id.line)
                val vipBg = tab.customView?.findViewById<ImageView>(R.id.vip_bg1)
                tv?.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                tv?.paint?.isFakeBoldText = true
                tv?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                vipBg?.setImageResource(R.drawable.vip_middle_select)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tv = tab.customView?.findViewById<TextView>(R.id.text1)
                val line = tab.customView?.findViewById<FrameLayout>(R.id.line)
                val vipBg = tab.customView?.findViewById<ImageView>(R.id.vip_bg1)
                tv?.setTextColor(ContextCompat.getColor(context, R.color.textDescription))
                vipBg?.setImageResource(R.drawable.vip_middle_normal)
                tv?.paint?.isFakeBoldText = false
                tv?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        for (i in 0 until tabIndicators.size) {
            tab.getTabAt(i)!!.setCustomView(R.layout.item_vip_center_tab)
            val tv = tab.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.text1)
            val line = tab.getTabAt(i)!!.customView!!.findViewById<FrameLayout>(R.id.line)
            val vipBg = tab.getTabAt(i)!!.customView!!.findViewById<ImageView>(R.id.vip_bg1)
            tv.text = tabIndicators[i]
            if (index == i) {
                tv.paint.isFakeBoldText = true
                tv.text = tabIndicators[i]
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                vipBg?.setImageResource(R.drawable.vip_middle_select)
            } else {
                tv.paint.isFakeBoldText = false
                tv.text = tabIndicators[i]
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                tv.setTextColor(ContextCompat.getColor(context, R.color.textDescription))
                vipBg?.setImageResource(R.drawable.vip_middle_normal)
            }

            if (i == tabIndicators.size - 1) {
                line.visibility = View.GONE
            } else {
                line.visibility = View.VISIBLE
            }
        }
        tab.post {
            TabLayoutUtils.setTabLine(tab)
        }
    }
}