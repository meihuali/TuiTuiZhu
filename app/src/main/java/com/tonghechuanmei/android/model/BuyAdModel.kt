/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午12:41
 */

package com.tonghechuanmei.android.model

data class BuyAdModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var payNo: String,
        var state: String
    )
}