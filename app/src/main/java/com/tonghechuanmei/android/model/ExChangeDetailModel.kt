/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-29 下午3:30
 */

package com.tonghechuanmei.android.model

data class ExChangeDetailModel(val s: String?, val image: String?) {
    fun getContent(): String {
        return if (s.isNullOrEmpty()) {
            ""
        } else {
            s!!
        }
    }

    fun getUrlImg(): String {
        return if (image.isNullOrEmpty()) {
            ""
        } else {
            image!!
        }
    }

    fun getShowContent(): Boolean {
        return !s.isNullOrEmpty()
    }

    fun getShowUrlImg(): Boolean {
        return !image.isNullOrEmpty()
    }
}