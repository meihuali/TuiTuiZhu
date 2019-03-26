package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/20 18:44
 */
data class TaskStatisticsModel(
    var `data`: Data = Data(),
    var msg: String = "",
    var success: Boolean = false
) {
    data class Data(
        var taskFlag: Int = 0,
        var taskSignFlag: Int = 0
    ) {
        var publishTaskCount: String = ""
            get() = "您已发布 $taskFlag 条"
        var applyTaskCount: String = ""
            get() = "您已报名 $taskSignFlag 条"
    }
}