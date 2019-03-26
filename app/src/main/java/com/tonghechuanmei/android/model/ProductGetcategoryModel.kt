/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午2:35
 */

package com.tonghechuanmei.android.model

data class ProductGetcategoryModel(
    val `data`: List<Data?>?,
    val msg: String?,
    val success: Boolean?
) {
    data class Data(
        val brokerageRatio: String?,
        val categoryAreaId: String?,
        val categoryFirstId: String?,
        val createTime: Long?,
        val id: String?,
        val imageUrl: String?,
        val isRecommend: String?,
        val name: String?,
        val navigationDisplay: String?,
        val pid: String?,
        val sort: String?,
        val state: String?,
        val wapImageUrl: Any?,
        val wapName: Any?
    )
}