/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午2:14
 */

package com.tonghechuanmei.android.model

import com.designer.library.widget.PriceUtils

/**
 * 获取钱包提现记录-ok-刘海龙
 */
data class UserIncomeModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>,
        var user: User
    ) {
        data class User(
            var address: Any,
            var age: Int,
            var alipayName: Any,
            var alipayNo: Any,
            var allContributeNum: String,
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
            var idCard: Any,
            var isSupplier: String,
            var keyword: Any,
            var lat: Any,
            var lng: Any,
            var memberId: String,
            var memberLevelId: Any,
            var memberLevelLogo: Any,
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
            var realName: Any,
            var refereeUserId: Any,
            var refereeUserNum: Any,
            var remark: Any,
            var serverArea: Any,
            var serverAreaId: Any,
            var sex: String,
            var shanghejiaId: Any,
            var sort: Int,
            var state: String,
            var surplusIncome: Long,
            var todayContributeNum: Double,
            var totalIncome: Long,
            var userLink: String,
            var uuid: Any,
            var weixinNo: Any,
            var weixinOpenid: Any,
            var weixinUid: Any,
            var weixinUnionid: Any,
            var xinlangOpenid: String,
            var xinlangUid: Any
        )

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var createTime: Any,
            var detail: Any,
            var failReason: Any,
            var id: Any,
            var incomePrice: Any,
            var orderId: Any,
            var orderNo: Any,
            var orderby: Any,
            var page: Int,
            var realIncome: Any,
            var rows: Int,
            var start: Int,
            var state: Any,
            var type: String,
            var typeList: Any,
            var userId: String
        )

        data class Result(
            var createTime: Long,
            var detail: String,
            var failReason: Any,
            var id: String,
            var incomePrice: Long,
            var orderId: Any,
            var orderNo: String,
            var rate: Long,
            var realIncome: Long,
            var reward: Int,
            var state: String,
            var type: String,
            var userId: String
        ) {
            fun getTaxesTv(): String {
                return "税费：${PriceUtils.getPrice(incomePrice - realIncome)}"
            }

            fun getPrice(): String {
                return "+${PriceUtils.getPrice(realIncome)}"
            }

            fun getMoneyPackage(): String {
                return if (type == "1") {
                    "+${PriceUtils.getPrice(realIncome)}"
                } else {
                    "-${PriceUtils.getPrice(realIncome)}"
                }
            }

            //零钱提现或零钱兑换Item的价格
            fun getMoneyRealIncome(): String {
                return PriceUtils.getPrice(realIncome)
            }


            fun getRateTv(): String {
                return "手续费：${PriceUtils.getPrice(rate)}"
            }

            fun getMoneyPackageTv(): String {
                return if (incomePrice >= realIncome) {
                    "手续费：${PriceUtils.getPrice(incomePrice - realIncome)}"
                } else {
                    "手续费：0"
                }

            }
        }
    }
}