/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 上午10:43
 */

package com.tonghechuanmei.android.ui.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.model.UserAddressEntity


/**
 * 收货地址item
 *
 * Author     : shandirong
 * Date       : 2018/6/28 16:13
 */
class ShippingAddressAdapter :
    BaseQuickAdapter<UserAddressEntity.DataBean, BaseViewHolder>(R.layout.item_shipping_address) {
    override fun convert(helper: BaseViewHolder, item: UserAddressEntity.DataBean) {
        if (helper.layoutPosition == 0) {
            helper.setGone(R.id.normal_line, true)
            helper.setGone(R.id.line, false)
            val text = " 默认   " + item.cityArea.replace(",", "") + item.address
            val span = SpannableString(text)
            span.setSpan(ForegroundColorSpan(Color.WHITE), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            span.setSpan(BackgroundColorSpan(Color.parseColor("#C6212D")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            helper.setText(R.id.address, span)
        } else {
            helper.setGone(R.id.normal_line, false)
                .setGone(R.id.line, true)
                .setText(R.id.address, item.cityArea.replace(",", "") + item.address)
        }
        helper.setText(R.id.name, item.name)
            .setText(R.id.phone, item.phone)
            .addOnClickListener(R.id.modify)
    }
}
