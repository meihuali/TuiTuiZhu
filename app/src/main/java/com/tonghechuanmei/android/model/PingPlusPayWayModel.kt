package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/25 18:21
 */
data class PingPlusPayWayModel(
    var `data`: Data = Data(),
    var msg: String = "",
    var success: Boolean = false
) {
    data class Data(
        var payNo: String = "",
        var payPrice: String = "",
        var payState: String = "",
        var state: String = "",
        var id: String = ""
    )
}