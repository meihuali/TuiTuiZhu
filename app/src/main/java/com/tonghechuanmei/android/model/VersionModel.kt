/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-30 下午5:41
 */

package com.tonghechuanmei.android.model

data class VersionModel(
    val `data`: Data?,
    val msg: String?,
    val success: Boolean?
) {
    data class Data(
        val createTime: Long?,
        val downloadUrl: String?,
        val id: String?,
        val isIdcardCheck: String?,
        val remark: String?,
        val type: String?,
        val userId: Any?,
        val versionNo: String?
    )
}