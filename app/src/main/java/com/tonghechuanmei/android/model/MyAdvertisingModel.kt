/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-25 下午1:45
 */

package com.tonghechuanmei.android.model

import android.text.format.DateFormat
import com.designer.library.widget.PriceUtils

data class MyAdvertisingModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val mySAdInfoList: List<MySAdInfo>,
        val pager: Pager
    ) {
        data class Pager(
            val count: Int,
            val page: Int,
            val pageList: Any,
            val pageSize: Int,
            val pageTotal: Int
        )

        data class MySAdInfo(
            val adColor: Any,
            val adImage: String,
            val adSpaceId: String,
            val adUrl: Any,
            val allcount: Int,
            val allpage: Int,
            val amount: Int,
            val auditTime: Any,
            val auditUser: Any,
            val createTime: Long,
            val createUser: String,
            val detail: Any,
            val duration: String,
            val endTime: Long,
            val id: String,
            val infoType: String,
            val isNowOnline: Any,
            val name: String,
            val orderby: Any,
            val page: Int,
            val remark: Any,
            val rows: Int,
            val shareCount: Any,
            val sort: Any,
            val spaceCode: Any,
            val spaceName: String,
            val start: Int,
            val startTime: Long,
            val state: String,
            val taskId: String,
            val taskName: Any,
            val userId: String,
            val userName: Any,
            val userType: String
        ) {
            fun getBuyLocation(): String {
                return "购买位置：$spaceName"
            }

            fun getStartDate(): String {
                return "开始时间：${DateFormat.format("yyyy-MM-dd HH:mm:ss", startTime)}"
            }

            fun getEndDate(): String {
                return "结束时间：${DateFormat.format("yyyy-MM-dd HH:mm:ss", endTime)}"
            }

            fun getBuyPrice(): String {
                return "¥${PriceUtils.getPrice(amount)}元"
            }

            //是否显示编辑
            fun getIsShowEdit(): Boolean {
                return state == "-1"
            }

            //是否显示支付
            fun getIsShowPay(): Boolean {
                return state == "1"
            }
        }
        //状态：-1审核失败，1创建，未支付，2已支付,待审核，3审核成功
    }
}