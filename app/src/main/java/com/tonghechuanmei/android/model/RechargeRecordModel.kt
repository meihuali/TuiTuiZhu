/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/11/18 8:17 PM
 */

package com.tonghechuanmei.android.model

import com.designer.library.utils.format
import com.tonghechuanmei.android.R

/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/11/18 8:17 PM
 */

data class RechargeRecordModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val pageInfo: PageInfo,
        val resultList: List<Result>
    ) {
        data class PageInfo(
            val allcount: Int,
            val allpage: Int,
            val detailList: Any,
            val months: Any,
            val orderby: Any,
            val page: Int,
            val qkeyword: Any,
            val rows: Int,
            val start: Int,
            val userId: String
        )

        data class Result(
            val createTime: Long,
            val detail: String,
            val id: Int,
            val memberLevelId: String,
            val orderId: String,
            val payPrice: Long,
            val rechargeMonth: String,
            val rechargePrice: Int,
            val state: String,
            val type: String,
            val userGasolineNo: Any,
            val userId: String
        ) {
            fun getIcon(): Int {
                return when (type) {
                    "2" -> {
                        R.drawable.ic_recharge_flow_record_item_flow
                    }
                    else -> R.drawable.ic_recharge_record_item_mobile
                }
            }

            fun getPayMoney(): String {
                return payPrice.format()
            }
        }
    }
}