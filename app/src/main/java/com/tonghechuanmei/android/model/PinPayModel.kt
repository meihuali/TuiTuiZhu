package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/24 15:11
 */
data class PinPayModel(
    var `data`: Data = Data(),
    var msg: String = "",
    var success: Boolean = false
) {
    data class Data(
        var `data`: String = "",
        var orderNo: String = ""
    )
}