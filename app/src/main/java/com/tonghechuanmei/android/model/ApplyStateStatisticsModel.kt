package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/20 18:57
 */
data class ApplyStateStatisticsModel(
    var `data`: Data = Data(),
    var msg: String = "",
    var success: Boolean = false
) {
    data class Data(
        var dshFlag: Int = 0,
        var jxzFlag: Int = 0,
        var wtgFlag: Int = 0,
        var ytgFlag: Int = 0
    )
}