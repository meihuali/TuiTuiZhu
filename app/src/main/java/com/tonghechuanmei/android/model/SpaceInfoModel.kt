/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-19 下午3:53
 */

package com.tonghechuanmei.android.model

data class SpaceInfoModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val adNum: Int,
        val adSpaceImg: String,
        val adSpacePrice: Int,
        val allcount: Int,
        val allpage: Int,
        val createTime: Any,
        val height: String,
        val id: String,
        val name: String,
        val orderby: Any,
        val pId: String,
        val page: Int,
        val rows: Int,
        val sort: Int,
        val start: Int,
        val typeCode: String,
        val width: String
    )
}