/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-25 下午11:24
 */

package com.tonghechuanmei.android.model

data class UserMsgModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val pageInfo: PageInfo,
        val resultList: List<Result>
    ) {
        data class Result(
            val birthday: String,
            val createTime: Long,
            val id: String,
            val idCard: String,
            val idCardBehindImg: String,
            val idCardFrontImg: String,
            val phone: String,
            val realName: String,
            val sex: String,
            val state: String,
            val userId: String,
            val failReason: String?
        ) {
            fun getReason(): String {
                return if (failReason.isNullOrEmpty()) {
                    "不通过原因：无"
                } else {
                    "不通过原因：$failReason"
                }
            }

            fun getIsShow(): Boolean {
                return state == "-2"
            }
        }

        data class PageInfo(
            val allcount: Int,
            val allpage: Int,
            val birthday: Any,
            val createTime: Any,
            val id: Any,
            val idCard: Any,
            val idCardBehindImg: Any,
            val idCardFrontImg: Any,
            val orderby: Any,
            val page: Int,
            val phone: Any,
            val realName: Any,
            val rows: Int,
            val sex: Any,
            val start: Int,
            val state: Any,
            val userId: String
        )
    }
}