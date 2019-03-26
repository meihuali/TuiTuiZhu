/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午9:18
 */

package com.tonghechuanmei.android.model

data class ContributeModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) {
        data class Result(
            var allContributeNum: String,
            var contributeNum: Double,
            var createTime: Long,
            var detail: String,
            var id: String,
            var state: String,
            var targetId: String,
            var todayContributeNum: Double,
            var type: String,
            var userId: String
        ) {
            fun getPrice(): String {
                return if (state == "1") {
                    "+$contributeNum"
                } else {
                    "-$contributeNum"
                }
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var contributeNum: Any,
            var createTime: Any,
            var detail: Any,
            var id: Any,
            var orderby: Any,
            var page: Int,
            var rows: Int,
            var start: Int,
            var state: Any,
            var targetId: Any,
            var type: Any,
            var userId: String,
            var allContributeNum: String,
            var todayContributeNum: String,
            var middleTitle: String,
            var bottomTitle: String
        ) {
            fun getAllNum(): String {
                return "总贡献值$allContributeNum"
            }

            fun getPrice(): String {
                return if (bottomTitle == "贡献值记录") {
                    if (allContributeNum.isNullOrEmpty()) {
                        "0"
                    } else {
                        allContributeNum
                    }
                } else {
                    if (todayContributeNum.isNullOrEmpty()) {
                        "0"
                    } else {
                        todayContributeNum
                    }
                }
            }
        }
    }
}