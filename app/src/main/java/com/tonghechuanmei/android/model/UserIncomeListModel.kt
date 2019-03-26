/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-23 下午3:24
 */

package com.tonghechuanmei.android.model

import com.designer.library.widget.PriceUtils

data class UserIncomeListModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) {
        data class Result(
            var createTime: Long,
            var detail: String,
            var failReason: String,
            var id: String,
            var incomePrice: Int,
            var orderId: Any,
            var orderNo: String,
            var rate: Long,
            var realIncome: Long,
            var reward: Double,
            var state: String,
            var type: String,
            var userId: String
        ) {
            fun getPrice(): String {
                return when (type) {
                    "0" -> "-${PriceUtils.getPrice(realIncome)}"
                    "1" -> "+${PriceUtils.getPrice(realIncome)}"
                    else -> "-${PriceUtils.getPrice(realIncome)}"
                }
            }

            fun getRateTv(): String {
                return "手续费：${PriceUtils.getPrice(rate)}"
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var createTime: Any,
            var detail: Any,
            var failReason: Any,
            var id: Any,
            var incomePrice: Any,
            var orderId: Any,
            var orderNo: Any,
            var orderby: Any,
            var page: Int,
            var realIncome: Any,
            var rows: Int,
            var start: Int,
            var state: Any,
            var type: Any,
            var typeList: Any,
            var userId: Any
        )
    }
}