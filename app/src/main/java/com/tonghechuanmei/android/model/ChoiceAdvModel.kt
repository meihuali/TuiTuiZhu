/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-21 上午10:55
 */

package com.tonghechuanmei.android.model

import com.designer.library.utils.DateUtils

/**
 * 选择活动
 */
data class ChoiceAdvModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) {
        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var contentFileId: Any,
            var contentId: Any,
            var createTime: Any,
            var createUserId: Any,
            var currentUserId: Any,
            var endTime: Any,
            var explicitLink: Any,
            var haveObj: Any,
            var id: Any,
            var isNovice: Any,
            var lat: Any,
            var lng: Any,
            var name: Any,
            var nowTime: Any,
            var objName: Any,
            var objPrice: Any,
            var orderby: Any,
            var page: Int,
            var payAdFee: Any,
            var payPrice: Any,
            var reason: Any,
            var requirements: Any,
            var rows: Int,
            var serverAreaCityId: Any,
            var serverAreaCountyId: Any,
            var serverAreaProvinceId: Any,
            var signUpAleady: Any,
            var signUpNum: Any,
            var sort: Any,
            var start: Int,
            var startTime: Any,
            var state: Any,
            var states: Any,
            var tackPrice: Any,
            var taskCategoryId: Any,
            var userId: String
        )

        data class Result(
            var allTaskPrice: Int,
            var content: String,
            var contentFileId: Any,
            var contentId: String,
            var createTime: Any,
            var createUserId: Any,
            var endTime: Long,
            var explicitLink: Any,
            var haveAudit: Any,
            var haveObj: String,
            var headImg: String,
            var id: String,
            var isNovice: String,
            var lat: Any,
            var lng: Any,
            var memberId: Any,
            var name: String,
            var nickName: String,
            var objName: Any,
            var objPrice: Any,
            var reason: String,
            var requirements: Any,
            var serverAreaCityId: Any,
            var serverAreaCountyId: Any,
            var serverAreaProvinceId: Any,
            var signUpAleady: String?,
            var signUpNum: Int,
            var sort: Any,
            var startTime: Long,
            var state: String,
            var taskCategoryId: String,
            var taskPrice: Int,
            var taskSignCount: Any,
            var taskSignId: Any,
            var taskSignList: Any,
            var taskSignState: Any,
            var userId: String
        ) {
            fun getNum(): String {
                return "已报名人数：${if (signUpAleady.isNullOrEmpty()) "0" else signUpAleady}"
            }

            fun getSendTime(): String {
                return "已发布时间：${DateUtils.formatDate(startTime, "yyyy-MM-dd HH:mm")}"
            }
        }
    }
}