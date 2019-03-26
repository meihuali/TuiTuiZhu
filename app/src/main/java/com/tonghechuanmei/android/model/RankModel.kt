/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-20 下午1:35
 */

package com.tonghechuanmei.android.model

import com.tonghechuanmei.android.R

data class RankModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val pageInfo: PageInfo,
        val resultList: List<Result>,
        val selfCensus: SelfCensus
    ) {
        data class SelfCensus(
            val createTime: Long,
            val id: String,
            val issueTaskNum: Int,
            val refereeNum: Int,
            val refereeSort: String,
            val takeOverTaskNum: Int,
            val userHeadImg: String,
            val userId: String,
            val userName: String
        ) {
            fun getSort(): String {
                return "排名：${if (refereeSort.isNullOrEmpty()) "0" else refereeSort}"
            }

            fun getNum(): String {
                return "粉丝：$refereeNum"
            }
        }

        data class PageInfo(
            val allcount: Int,
            val allpage: Int,
            val createTime: Any,
            val id: Any,
            val issueTaskNum: Any,
            val orderby: Any,
            val page: Int,
            val refereeNum: Any,
            val refereeSort: Any,
            val rows: Int,
            val start: Int,
            val takeOverTaskNum: Any,
            val userHeadImg: Any,
            val userId: String,
            val userName: Any
        )

        data class Result(
            val createTime: Long,
            val id: String,
            val issueTaskNum: Int,
            val refereeNum: Int,
            val refereeSort: Int,
            val takeOverTaskNum: Int,
            val userHeadImg: String,
            val userId: String,
            val userName: String,
            var position: Int
        ) {
            fun getSendNum(): String {
                return "发布${issueTaskNum}次 | 已完成${takeOverTaskNum}次"
            }

            fun getAcceptNum(): String {
                return "接受活动${takeOverTaskNum}次"
            }

            fun getRecommendNum(): String {
                return refereeNum.toString()
            }

            fun getRankingImg(): Int {
                return when (position) {
                    0 -> R.drawable.group_first
                    1 -> R.drawable.group_two
                    2 -> R.drawable.group_thread
                    else -> 0
                }

            }

            fun isShowRankingImg(): Boolean {
                return position < 3
            }

            fun isShowRankTv(): Boolean {
                return position >= 3
            }

            fun getRankTv(): String {
                return (position + 1).toString()
            }
        }
    }
}