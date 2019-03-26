/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/23/18 11:41 AM
 */

package com.tonghechuanmei.android.model

import java.io.Serializable

data class TaskTypeModel(
    var `data`: List<Data>,
    var msg: String,
    var success: Boolean
) : Serializable {
    data class Data(
            var categoryImage: Any,
            var createTime: Long,
            var id: String,
            var name: String,
            var sortNo: Int,
            var categoryListImage: String,
            var type: String,
            var categoryCode: String
    ) : Serializable
}