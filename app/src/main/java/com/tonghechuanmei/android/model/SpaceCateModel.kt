/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-25 下午2:41
 */

package com.tonghechuanmei.android.model

data class SpaceCateModel(
    val `data`: List<Data>,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val state: String,
        val stateName: String,
        val stateNum: String
    )
}