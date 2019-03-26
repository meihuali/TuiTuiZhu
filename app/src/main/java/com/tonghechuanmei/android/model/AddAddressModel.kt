/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 下午2:42
 */

package com.tonghechuanmei.android.model

data class AddAddressModel(
    val `data`: Data?,
    val msg: String?,
    val success: Boolean?
) {
    data class Data(
        val address: String?,
        val cityArea: String?,
        val createTime: Long?,
        val delState: String?,
        val id: String?,
        val isDefault: String?,
        val name: String?,
        val phone: String?,
        val tel: String?,
        val userId: String?
    )
}