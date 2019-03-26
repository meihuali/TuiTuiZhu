package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.tonghechuanmei.android.BR
import com.tonghechuanmei.android.R

/**
 * Author     : shandirong
 * Date       : 2018/11/20 18:57
 */
data class ApplyStateListModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) {
        data class Result(
            var createTime: Long,
            var haveObj: String? = null,
            var headImg: String,
            var id: String,
            var reason: String? = null,
            var remark: String? = null,
            var signUpNum: Int,
            var signedNum: Int,
            var state: String,
            var subContent: String? = null,
            var subFiles: String? = null,
            var taskId: String,
            var taskName: String,
            var objName: String? = null,
            var taskPrice: Long,
            var userId: String,
            var nickName: String,
            var userName: String,
            var award: String
        ) : BaseObservable() {

            @Bindable
            var checked: Boolean = false
                set(value) {
                    field = value
                    notifyPropertyChanged(BR.checked)
                }

            fun getSignedNumber(): String {
                return "已有" + signedNum + "人报名"
            }

            fun getAwardTv(): String {
                return "悬赏金额: $award"
            }

            fun getStateStr(): String {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return when (state) {
                    "-2" -> "未通过"
                    "0" -> "进行中"
                    "1" -> "待审核"
                    "2" -> "已通过"
                    else -> {
                        ""
                    }
                }
            }

            fun getCancelBtnStr(): String {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return when (state) {
                    "2" -> "删除活动"
                    else -> {
                        "放弃活动"
                    }
                }
            }

            fun getBtnStr(): String {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return when (state) {
                    "-2" -> "重新审核"
                    "0" -> "提交活动"
                    else -> {
                        ""
                    }
                }
            }

            fun getBtnGone(): Boolean {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return when (state) {
                    "-2" -> true
                    "0" -> true
                    "1" -> false
                    "2" -> false
                    else -> {
                        false
                    }
                }
            }

            fun getTextRemark(): String {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return when (state) {
                    "-2" -> "原因：$reason"
                    "2" -> "运单单号：$remark"
                    else -> {
                        ""
                    }
                }
            }

            fun showRemark(): Boolean {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return when (state) {
                    "-2" -> reason != null
                    "2" -> remark != null
                    else -> {
                        false
                    }
                }
            }

            fun showNumberBtn(): Boolean {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return state == "2" && remark == null && (!haveObj.isNullOrEmpty() && haveObj == "1")
            }

            fun clickNumberBtn(): Boolean {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return remark == null && (!haveObj.isNullOrEmpty() && haveObj == "1")
            }

            fun numberBtnBackground(): Int {
                //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                return if (remark == null && (!haveObj.isNullOrEmpty() && haveObj == "1")) R.drawable.task_start_end_bg else R.drawable.task_start_end_ground_bg
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var createTime: String,
            var id: String,
            var orderby: String,
            var page: Int,
            var reason: String,
            var remark: String,
            var rows: Int,
            var signUpNum: String,
            var start: Int,
            var state: String,
            var subContent: String,
            var subFiles: String,
            var taskId: String,
            var taskName: String,
            var userId: String,
            var userName: String
        )
    }
}