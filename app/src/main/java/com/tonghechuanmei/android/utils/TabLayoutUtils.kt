/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午3:32
 */

package com.tonghechuanmei.android.utils

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.dip

/**
 * Author     : majingcheng
 * Date       : 2018-06-21 18:07
 */
object TabLayoutUtils {
    fun setTabLine(tabLayout: TabLayout, left: Int, right: Int, mContext: Context) {
        tabLayout.post {
            kotlin.run {
                try {
                    //拿到tabLayout的mTabStrip属性
                    val mTabStripField = tabLayout.javaClass.getDeclaredField("slidingTabIndicator")
                    mTabStripField.isAccessible = true

                    val mTabStrip = mTabStripField.get(tabLayout) as LinearLayout

                    for (i in 0 until mTabStrip.childCount) {
                        val tabView = mTabStrip.getChildAt(i)

                        //拿到tabView的mTextView属性
                        val mTextViewField = tabView.javaClass.getDeclaredField("textView")
                        mTextViewField.isAccessible = true

                        val mTextView = mTextViewField.get(tabView) as TextView

                        tabView.setPadding(0, 0, 0, 0)

                        //字多宽线就多宽，所以测量mTextView的宽度
                        var width = 0
                        width = mTextView.width
                        if (width == 0) {
                            mTextView.measure(0, 0)
                            width = mTextView.measuredWidth
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        val params = tabView
                            .layoutParams as LinearLayout.LayoutParams
                        params.width = width
                        params.setMargins(
                            mContext.dip(left), 0, mContext.dip(right), 0
                        )
                        tabView.layoutParams = params

                        tabView.invalidate()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun setTabLine(tabLayout: TabLayout) {
        tabLayout.post {
            kotlin.run {
                try {
                    //拿到tabLayout的mTabStrip属性
                    val mTabStripField = tabLayout.javaClass.getDeclaredField("slidingTabIndicator")
                    mTabStripField.isAccessible = true

                    val mTabStrip = mTabStripField.get(tabLayout) as LinearLayout

                    for (i in 0 until mTabStrip.childCount) {
                        val tabView = mTabStrip.getChildAt(i)

                        //拿到tabView的mTextView属性
                        val mTextViewField = tabView.javaClass.getDeclaredField("customView")
                        mTextViewField.isAccessible = true

                        val mTextView = mTextViewField.get(tabView) as View

                        tabView.setPadding(0, 0, 0, 0)

                        //字多宽线就多宽，所以测量mTextView的宽度
                        var width = 0
                        width = mTextView.width
                        if (width == 0) {
                            mTextView.measure(0, 0)
                            width = mTextView.measuredWidth
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        val params = tabView
                            .layoutParams as LinearLayout.LayoutParams
                        if (i == mTabStrip.childCount - 1) {
                            params.width = width + 15
                        } else {
                            params.width = width
                        }
                        tabView.layoutParams = params

                        tabView.invalidate()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
