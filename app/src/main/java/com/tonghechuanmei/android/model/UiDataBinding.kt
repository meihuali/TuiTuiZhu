/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-3 下午4:15
 */

package com.tonghechuanmei.android.model

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.designer.library.component.BannerImageLoader
import com.designer.library.widget.DifferentSizeTextView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import java.util.regex.Pattern

/**
 * 视图显示组件
 */

@BindingAdapter(value = ["otherRmb", "beanPrice", "sort"], requireAll = false)
fun DifferentSizeTextView.setText(price: Long, beanPrice: Long, sort: String?) {
    if (UserModel.memberLevelId != UserDetailModel.GOLD) {           //除黄金等级全是糖豆
        setRightText("")
        setLastText("糖豆")
        setLeftText(beanPrice?.toString())
    } else {                                       //显示金额
        setDifferentText(price)
        setLeftText("¥")
    }
}

@BindingAdapter("banner")
fun Banner.setImgBanner(img: String?) {
    if (!img.isNullOrEmpty()) {
        setDelayTime(5000).setImages(img!!.split(",")).setImageLoader(BannerImageLoader()).setIndicatorGravity(
            BannerConfig.RIGHT
        ).start()
    }
}

private fun isNumber(str: String): Boolean {
    val pattern = Pattern.compile("-?[0-9]+")
    val isNum = pattern.matcher(str)
    return isNum.matches()
}

@BindingAdapter("defaultText")
fun TextView.setDefaultText(s: String?) {
    if (!s.isNullOrEmpty()) {
        val text = " 默认   $s"
        val span = SpannableString(text)
        span.setSpan(ForegroundColorSpan(Color.WHITE), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(BackgroundColorSpan(Color.parseColor("#C6212D")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setText(span)
    }
}


@BindingAdapter("textBgColor")
fun TextView.setColor(color: Int) {
    setBackgroundColor(color)
}
