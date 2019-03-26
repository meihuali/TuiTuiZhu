/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/23/18 4:05 PM
 */

package com.tonghechuanmei.android.model

import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.designer.library.utils.UnitUtils
import java.io.Serializable

data class TaskDetailModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {

    data class Data(
        var allTaskPrice: Long,
        var award: String,
        var cateName: String?,
        var content: String?,
        var contentFileId: String,
        var contentId: String,
        var createTime: String,
        var createUserId: String,
        var distance: Double,
        var endTime: Long,
        var explicitLink: String? = null,
        var haveAudit: String?,
        var haveObj: String,
        var haveVoucher: String,
        var headImg: String,
        var id: String,
        var isNovice: String,
        var lat: Double,
        var lng: Double,
        var memberId: String,
        var memberLevelLogo: String,
        var name: String,
        var nickName: String,
        var objName: String,
        var objPrice: String,
        var reason: String,
        var requirements: String,
        var serverAreaCityId: String,
        var serverAreaCountyId: String,
        var serverAreaProvinceId: String,
        var signState: String? = null,
        var adress: String? = null,
        var taskSignId: String? = null,
        var signUpAleady: String,
        var signUpNum: Long,
        var signedNum: Long,
        var sort: String,
        var startTime: Long,
        var state: String? = null,
        var taskCategoryId: String,
        var taskFileList: List<TaskFile>,
        var taskPrice: Long,
        var userId: String
    ) : Serializable {

        fun getExplicitLinkStr(): String {
            return "来源于: $explicitLink"
        }

        fun getAwardTv(): String {
            return "悬赏: $award"

        }

        fun getDistance(): String {
            val calculateLineDistance = AMapUtils.calculateLineDistance(
                LatLng(LocationModel.latitude, LocationModel.longitude),
                LatLng(lat, lng)
            )
            return UnitUtils.distance(calculateLineDistance)
        }


        fun showAuditStr(): Boolean {
            return haveAudit != null && haveAudit == "1"
        }

        fun getAuditStr(): String {
            return if (haveAudit != null && haveAudit == "1") {
                "需要审核"
            } else {
                "不需要审核"
            }
        }

        fun getBtnStr(): String {
            return if (signState.isNullOrEmpty() || signState == "-1") {
                "我要报名"
            } else if (signState == "-2") {
                "重新审核"
            } else if (signState == "0") {
                "提交活动"
            } else if (signState == "1") {
                ""
            } else {
                ""
            }
        }

        fun getAddress(): String {
            return "${adress ?: ""}${calculateDistance()}"
        }

        private fun calculateDistance(): String {
            val calculateLineDistance = AMapUtils.calculateLineDistance(
                LatLng(LocationModel.latitude, LocationModel.longitude),
                LatLng(lat, lng)
            )
            return if (adress.isNullOrEmpty()) {
                UnitUtils.distance(calculateLineDistance)
            } else {
                " • ${UnitUtils.distance(calculateLineDistance)}"
            }
        }

        // 是否显示Negative按钮
        fun getCancelBtnGone(): Boolean {
            return signState == "-2" || signState == "0" || signState == "1"
        }

        // 是否显示Positive按钮
        fun getBtnGone(): Boolean {
            return if (UserModel.userId != userId) {
                signState.isNullOrEmpty() || signState == "-2" || signState == "-1" || signState == "0"
            } else {
                false
            }
        }

        fun getSignNumberStr(): String {
            return "报名人数：$signUpNum      剩余 ${signUpNum - signedNum}"
        }

        data class TaskFile(
            var contentId: String,
            var createTime: String,
            var fileName: String,
            var filePath: String,
            var fileType: String,
            var id: String,
            var sortNo: String,
            var userId: String
        ) : Serializable
    }
}