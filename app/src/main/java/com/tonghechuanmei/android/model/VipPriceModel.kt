/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-30 下午2:52
 */

package com.tonghechuanmei.android.model

import com.designer.library.widget.PriceUtils

data class VipPriceModel(
    val `data`: Data?,
    val msg: String?,
    val success: Boolean?
) {
    data class Data(
        val price: Long?
    ) {
        fun getPayPrice(): String {
            return "确认并支付${PriceUtils.getPrice(price!!)}"
        }
    }
}