/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/28/18 7:02 PM
 */

package com.tonghechuanmei.android.model

data class WithdrawServiceChargeModel(
    val `data`: List<Data>,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val rate: String,
        val time: String,
        val type: String
    ) {

        fun getStr(): String {
            return "${time}小时到账 (手续费${rate})"
        }
    }
}