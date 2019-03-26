/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-25 下午10:15
 */

package com.tonghechuanmei.android.model

data class SpaceTaskInfoModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
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
        val taskName: String,
        val userId: String,
        val userName: Any,
        val userType: String,
        val width: Int = 170,
        val height: Int = 170
    )
}