/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-12-23 下午8:31
 */

package com.tonghechuanmei.android.model

data class DeviceModel(
    var `data`: Data?,
    var msg: String?,
    var success: Boolean?
) {
    data class Data(
        var dayNum: Int?,
        var deviceId: String?,
        var hourNum: Int?,
        var id: String?
    )
}