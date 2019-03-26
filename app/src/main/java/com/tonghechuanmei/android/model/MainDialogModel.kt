/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/11/18 3:06 PM
 */

package com.tonghechuanmei.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MainDialogModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val ad: Ad?,
        val welfare: Welfare?
    ) {

        @Parcelize
        data class Ad(
            val createTime: Long,
            val endTime: Long,
            val id: String,
            val imgUrl: String,
            val remark: String,
            val startTime: Long,
            val state: Int,
            val title: String,
            val type: String,
            val typeCode: String
        ) : Parcelable

        @Parcelize
        data class Welfare(
            val code: String,
            val createTime: Long,
            val endTime: Long,
            var giveNum: Double,
            val id: String,
            val isAssignUser: String,
            val name: String,
            val remark: String,
            val startTime: Long,
            val state: String,
            val type: String,
            val userIds: String
        ) : Parcelable {

            fun getGift(): String {
                val unit = when (type) {
                    "1" -> "元"
                    "2", "3", "4" -> "个"
                    else -> "个"
                }
                if (type == "1") {
                    giveNum /= 100
                }
                return "$giveNum$unit"
            }

            fun getGiftName(): String {
                return when (type) {
                    "1" -> "零钱"
                    "2" -> "糖豆"
                    "3" -> "今日贡献度"
                    "4" -> "总贡献度"
                    else -> "零钱"
                }
            }
        }
    }
}