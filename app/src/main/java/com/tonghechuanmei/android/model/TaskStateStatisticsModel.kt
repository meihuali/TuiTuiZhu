package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/20 18:54
 */
data class TaskStateStatisticsModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var btgFlag: Int,
        var wshFlag: Int,
        var ytgFlag: Int
    )
}