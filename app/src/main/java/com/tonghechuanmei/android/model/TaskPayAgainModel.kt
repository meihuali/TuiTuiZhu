package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/24 22:40
 */
data class TaskPayAgainModel(
    var `data`: Data = Data(),
    var msg: String = "",
    var success: Boolean = false
) {
    data class Data(
        var payNo: String = "",
        var payPrice: String = "",
        var state: String = ""
    )
}