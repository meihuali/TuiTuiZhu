/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午2:57
 */

package com.tonghechuanmei.android.utils

import android.content.Context
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.tonghechuanmei.android.R

/**
 * Author     : majingcheng
 * Date       : 2018/10/5 11:30
 */
class SelectTabUtils {
    fun setTab(tab: TabLayout, context: Context, tabIndicators: ArrayList<String>, left: Int, right: Int) {
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tv = tab.customView?.findViewById<TextView>(R.id.tab_title)
                tv?.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                tv?.paint?.isFakeBoldText = true
                tv?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tv = tab.customView?.findViewById<TextView>(R.id.tab_title)
                tv?.setTextColor(ContextCompat.getColor(context, R.color.textDescription))
                tv?.paint?.isFakeBoldText = false
                tv?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        for (i in 0 until tabIndicators.size) {
            tab.getTabAt(i)!!.setCustomView(R.layout.item_top_tab)
            val tv = tab.getTabAt(i)!!.customView!!.findViewById<TextView>(R.id.tab_title)
            tv.text = tabIndicators[i]
            if (0 == i) {
                tv.paint.isFakeBoldText = true
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            } else {
                tv.paint.isFakeBoldText = false
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
                tv.setTextColor(ContextCompat.getColor(context, R.color.textDescription))
            }
        }
        tab.post {
            TabLayoutUtils.setTabLine(tab, left, right, context)
        }
    }
}