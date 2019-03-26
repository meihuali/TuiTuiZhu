/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/30/18 7:42 PM
 */

package com.tonghechuanmei.android.model

data class UserAuthDynamicModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val createTime: String,
        val downloadUrl: String,
        val id: String,
        val isIdcardCheck: String,
        val remark: String,
        val type: String,
        val userId: String,
        val versionNo: String
    )
}