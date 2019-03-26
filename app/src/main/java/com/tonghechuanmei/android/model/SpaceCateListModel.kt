/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-19 下午7:33
 */

package com.tonghechuanmei.android.model

data class SpaceCateListModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var adNum: Int,
        var adSpaceImg: String,
        var adSpacePrice: Long,
        var createTime: Any,
        var height: String,
        var id: String,
        var name: String,
        var pId: String,
        var sort: Int,
        var typeCode: String,
        var width: String
    )
}