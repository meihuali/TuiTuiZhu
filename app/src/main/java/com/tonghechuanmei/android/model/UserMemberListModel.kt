/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 下午6:04
 */

package com.tonghechuanmei.android.model

import java.io.Serializable

data class UserMemberListModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) : Serializable {
    data class Data(
        var resultList: List<Result>,
        var user: User
    ) : Serializable {
        data class User(
            var address: Any,
            var age: Int,
            var alipayName: Any,
            var alipayNo: Any,
            var allContributeNum: String?,
            var appletOpenid: Any,
            var bankCardNo: Any,
            var bankOfDeposit: Any,
            var beanNum: String,
            var birthday: Any,
            var cardholder: Any,
            var createTime: Long,
            var email: Any,
            var getuiCid: Any,
            var headImg: String,
            var id: String,
            var isSupplier: String,
            var keyword: Any,
            var lat: Any,
            var lng: Any,
            var memberId: String,
            var memberLevelId: String,
            var memberLevelLogo: String,
            var name: String,
            var nickName: String,
            var noRecorded: Any,
            var phone: String,
            var phoneType: Any,
            var postcode: Any,
            var pwd: String,
            var qq: Any,
            var qqOpenid: Any,
            var qqUid: Any,
            var refereeUserId: Any,
            var refereeUserNum: Any,
            var remark: Any,
            var serverArea: Any,
            var serverAreaId: Any,
            var sex: String,
            var shanghejiaId: Any,
            var sort: Any,
            var state: String,
            var surplusIncome: Long,
            var todayContributeNum: Any,
            var totalIncome: Long,
            var userLink: String,
            var uuid: Any,
            var weixinNo: Any,
            var weixinOpenid: Any,
            var weixinUid: Any,
            var weixinUnionid: Any,
            var xinlangOpenid: String,
            var xinlangUid: Any,
            var currentLevelNo: String,
            var memberName: String,
            var currentContributeValue: Double,
            var nextContributeValue: Double,
            var currentMemberLevelSort: Int
        ) : Serializable

        data class Result(
            var createTime: Long,
            var detail: String,
            var id: String,
            var name: String,
            var sort: Int,
            var imgUrl: String?,
            var tjUserMemberLevelList: List<TjUserMemberLevel>,
            var currentMemberLevelSort: Int,
            var allContributeNum: String,
            var nextContributeValue: String,
            var currentPos: Int
        ) : Serializable {
            fun getProgress(): String {
                return "$allContributeNum/$nextContributeValue"
            }

            data class TjUserMemberLevel(
                var commissionRatio: Int,
                var context: String,
                var contributeValue: Int,
                var createTime: Long,
                var doTaskContribute: String,
                var doTaskTimes: Int,
                var givingBeanNum: Int,
                var givingContributeNum: Int,
                var givingTodayContributeNum: Int,
                var id: String,
                var levelLogo: String,
                var levelName: String,
                var levelImgUrl: String,
                var levelNo: String,
                var memberId: String,
                var price: Long,
                var publishTaskContribute: String,
                var publishTaskTimes: Int,
                var remark: String,
                var sort: Int,
                var todayContributeUpperLimit: Int,
                var withdrawRatio: Int,
                var currentMemberLevelSort: Int
            ) : Serializable {
                fun getLevel(): String {
                    return levelNo.substring(1, levelNo.length)
                }

                //等级
                fun getVipTv(): String {
                    return if (sort >= 11) {
                        (sort - 10).toString()
                    } else {
                        (if (sort % 5 == 0) 5 else sort % 5).toString()
                    }

                }
            }
        }
    }
}