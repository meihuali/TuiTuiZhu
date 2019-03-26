package com.tonghechuanmei.android.api

import com.tonghechuanmei.android.BuildConfig

object Const {
    const val HOST_TEST = "http://47.104.247.56:8080/tuituipigapp/"
    const val HOST_RELEASE = "http://www.tonghechuanmei.com/tuituipigapp/"
    const val HOST_LOCAL_1 = "http://192.168.0.122:7081/"   //江波
    const val HOST_LOCAL_2 = "http://192.168.0.115:8080/"     //吴雪清
    val HOST = if (BuildConfig.DEBUG) {
        Const.HOST_TEST
    } else {
        Const.HOST_RELEASE
    }
}



