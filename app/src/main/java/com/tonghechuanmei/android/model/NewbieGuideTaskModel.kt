/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/12/18 2:03 PM
 */

package com.tonghechuanmei.android.model

import com.designer.library.base.getApp
import com.tonghechuanmei.android.R

/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/12/18 2:02 PM
 */

data class NewbieGuideTaskModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val pageInfo: PageInfo,
        val resultList: List<Result>
    ) {
        data class Result(
            val code: String,
            val createTime: Long,
            val detial: String,
            val giveNum: Double,
            val id: String,
            val isComplete: String,
            val isState: String,
            val title: String,
            val type: String
        ) {

            fun getAwardName(): String {
                val awardName = when (type) {
                    "1" -> if (UserModel.memberLevelId == UserDetailModel.GOLD) {
                        "广告余额"
                    } else "糖豆"
                    "2" -> "余额"
                    "3" -> "贡献值"
                    "5" -> "广告余额"
                    else -> "总贡献度"
                }
                return "$giveNum $awardName"
            }

            fun getAwardNameStr(): String {
                return "+ ${getAwardName()}"
            }


            fun getBtnStr(): String {
                return when (isComplete) {
                    "1" -> {
                        if (isState == "1") {
                            "领取奖励"
                        } else {
                            "已领取"
                        }
                    }
                    else -> {
                        "去完成"
                    }
                }
            }

            fun getBtnBackground(): Int {
                return if (isComplete == "0") {
                    getApp().resources.getColor(R.color.colorAccent)
                } else if (isComplete == "1" && isState == "1") {
                    getApp().resources.getColor(R.color.red)
                } else getApp().resources.getColor(R.color.windowBackground)
            }

            fun getBtnEnable(): Boolean {
                return !(isComplete == "1" && isState == "2")
            }
        }

        data class PageInfo(
            val allcount: Int,
            val allpage: Int,
            val code: Any,
            val createTime: Any,
            val detial: Any,
            val giveNum: Any,
            val id: Any,
            val orderby: Any,
            val page: Int,
            val qkeyword: Any,
            val rows: Int,
            val start: Int,
            val title: Any,
            val type: Any,
            val userId: String
        )
    }
}