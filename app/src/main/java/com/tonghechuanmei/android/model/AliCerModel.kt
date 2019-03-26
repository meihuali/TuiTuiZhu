/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-10 下午4:05
 */

package com.tonghechuanmei.android.model

data class AliCerModel(
    val `data`: Data?,
    val msg: String?,
    val success: Boolean?
) {
    data class Data(
        val bizNo: String?,
        val state: String?,
        val transactionId: String?
    )
}