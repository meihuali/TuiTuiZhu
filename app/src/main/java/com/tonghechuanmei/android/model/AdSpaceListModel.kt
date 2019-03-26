package com.tonghechuanmei.android.model

data class AdSpaceListModel(
    val `data`: List<Data>,
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
        val pId: Any,
        val page: Int,
        val rows: Int,
        val sort: Int,
        val start: Int,
        val typeCode: String,
        val width: String
    )
}