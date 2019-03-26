/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午10:33
 */

package com.tonghechuanmei.android.model

data class JellyBeanModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var beanList: List<Bean>,
        var pageInfo: PageInfo
    ) {
        data class Bean(
            var allBeanNum: Double,
            var beanNum: String?,
            var createTime: Long,
            var detail: String,
            var failReason: String,
            var id: String,
            var state: String,
            var targetId: String,
            var type: String,
            var userId: String,
            var userName: String,
            var acquireBeanNum: String?
        ) {
            fun getPrice(): String {
                return if (state == "1") {
                    "+${(if (beanNum.isNullOrEmpty()) "0" else beanNum)!!.toDouble().toLong()}"
                } else {
                    "-${(if (beanNum.isNullOrEmpty()) "0" else beanNum)!!.toDouble().toLong()}"
                }
            }

            fun getServiceCharge(): String {
                val fontNum = (if (acquireBeanNum.isNullOrEmpty()) "0" else acquireBeanNum)!!.toDouble().toLong()
                val backNum = (if (beanNum.isNullOrEmpty()) "0" else beanNum)!!.toDouble().toLong()
                return if (fontNum < backNum) {
                    "手续费：0"
                } else {
                    "手续费：${fontNum - backNum}"
                }
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var beanNum: Any,
            var createTime: Any,
            var detail: Any,
            var failReason: Any,
            var id: Any,
            var orderby: Any,
            var page: Int,
            var rows: Int,
            var start: Int,
            var state: Any,
            var targetId: Any,
            var type: Any,
            var userId: String
        )
    }
}