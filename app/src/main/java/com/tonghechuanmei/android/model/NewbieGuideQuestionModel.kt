package com.tonghechuanmei.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Author     : shandirong
 * Date       : 2018/11/20 14:05
 */
data class NewbieGuideQuestionModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) {
        @Parcelize
        data class Result(
            var answer: String,
            var createTime: Long,
            var id: String,
            var question: String,
            var state: Int,
            var type: Int
        ) : Parcelable {

            fun getQuestionStr(): String {
                return question
            }

            fun getAnswerStr(): String {
                return "答：$answer"
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var answer: Any,
            var createTime: Any,
            var id: Any,
            var orderby: Any,
            var page: Int,
            var question: Any,
            var rows: Int,
            var start: Int,
            var state: Int,
            var type: Int
        )
    }
}