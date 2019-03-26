/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-12-3 下午3:05
 */

package com.tonghechuanmei.android.model

import java.io.Serializable

data class AddressListModel(
    val `data`: List<Data>,
    val msg: String,
    val success: Boolean
) : Serializable {
    fun getIsShowAddress(): Boolean {
        return msg != "-4"
    }

    data class Data(
        val address: String,
        val cityArea: String,
        val createTime: Long,
        val delState: String,
        val id: String,
        val isDefault: String,
        val name: String,
        val phone: String,
        val tel: Any,
        val userId: String,
        val userName: String
    ) : Serializable {
        fun getAddressData(): String {
            return if (cityArea.isNullOrEmpty()) "" else cityArea!! + if (address.isNullOrEmpty()) "" else address
        }
    }
}