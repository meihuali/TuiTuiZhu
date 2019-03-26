package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/17 14:45
 */
data class HomeIconEntity(var name: String, var hotNum: String, var iconId: Int) {

    fun getHotDes(): String {
        if (hotNum == "暂未开放") {
            return hotNum
        }
        return "热度  $hotNum%"
    }
}
