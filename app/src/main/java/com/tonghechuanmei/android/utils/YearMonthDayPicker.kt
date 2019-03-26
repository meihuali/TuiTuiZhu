/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-26 上午1:07
 */

package com.tonghechuanmei.android.utils

import android.app.Activity
import androidx.core.content.ContextCompat
import cn.qqtheme.framework.picker.DatePicker
import cn.qqtheme.framework.util.ConvertUtils
import com.tonghechuanmei.android.R
import java.util.*

/**
 * Author : majingcheng
 * Date : 2018/8/21
 */
class YearMonthDayPicker {
    //新品推荐Item点击
    private var onYearMonthDayClick: ((String) -> Unit)? = null       //前一个为整个数据的position,后一个为item中的position

    fun onYearMonthDayClick(onYearMonthDayClick: (String) -> Unit): YearMonthDayPicker {
        this.onYearMonthDayClick = onYearMonthDayClick
        return this
    }

    lateinit var activity: Activity
    lateinit var picker: DatePicker

    fun showYearMonthDayPicker(activity: Activity) {
        this.activity = activity
        /**
         * 使用 Calendar 获取当前的年月日，在下面的日期选择器中使用
         */
        val c = Calendar.getInstance()
        val currentYear = 1900
        val currentMonth = c.get(Calendar.MONTH) + 1
        val currentDay = c.get(Calendar.DAY_OF_MONTH)
        /**
         * 日期选择器
         */
        picker = DatePicker(activity)
        picker.setCanceledOnTouchOutside(true)
        picker.setUseWeight(true)
        picker.setTopPadding(ConvertUtils.toPx(activity, 10f))
        picker.setContentPadding(ConvertUtils.toPx(activity, 10f), ConvertUtils.toPx(activity, 10f))
        picker.setTextColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setDividerColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setLabelTextColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setCancelTextColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setSubmitTextColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setPressedTextColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setTopHeight(45)
        picker.setTopLineColor(ContextCompat.getColor(activity, R.color.textColor))
        picker.setCancelTextSize(14)
        picker.setSubmitTextSize(14)
        picker.setRangeEnd(c.get(Calendar.YEAR), currentMonth, currentDay)
        picker.setRangeStart(currentYear, currentMonth, currentDay)
        picker.setSelectedItem(c.get(Calendar.YEAR), currentMonth, currentDay)
        picker.setResetWhileWheel(false)
        picker.setOnDatePickListener(DatePicker.OnYearMonthDayPickListener { year, month, day ->
            val pickerDateShowText = "$year-$month-$day"
            onYearMonthDayClick!!.invoke(pickerDateShowText)
        })
        picker.setOnWheelListener(object : DatePicker.OnWheelListener {
            override fun onYearWheeled(index: Int, year: String) {
                picker.setTitleText(year + "-" + picker.selectedMonth + "-" + picker.selectedDay)
            }

            override fun onMonthWheeled(index: Int, month: String) {
                picker.setTitleText(picker.selectedYear + "-" + month + "-" + picker.selectedDay)
            }

            override fun onDayWheeled(index: Int, day: String) {
                picker.setTitleText(picker.selectedYear + "-" + picker.selectedMonth + "-" + day)
            }
        })
        picker.show()
    }
}