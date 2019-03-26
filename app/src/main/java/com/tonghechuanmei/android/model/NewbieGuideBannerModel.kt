package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/19 18:48
 */
data class NewbieGuideBannerModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) {
        data class Result(
            var bannerPath: String,
            var createTime: Long,
            var id: String,
            var state: Int,
            var title: String,
            var type: String,
            var url: String
        ) {
            fun isVideo(): Boolean {
                return type == "2"
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var bannerPath: String,
            var createTime: String,
            var id: String,
            var orderby: String,
            var page: Int,
            var rows: Int,
            var start: Int,
            var state: Int,
            var title: String,
            var type: String
        )
    }
}