/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 下午3:31
 */

package com.tonghechuanmei.android.model

import com.designer.library.widget.PriceUtils

data class UserDetailModel constructor(
        var `data`: Data,
        var msg: String,
        var success: Boolean
) {

    data class Data(
            var address: Any,
            var age: Int,
            var alipayName: Any,
            var alipayNo: String,
            var allContributeNum: String,
            var appletOpenid: Any,
            var bankCardNo: Any,
            var bankOfDeposit: Any,
            var beanNum: Double,
            var birthday: Any,
            var cardholder: Any,
            var createTime: Long,
            var email: Any,
            var getuiCid: Any,
            var headImg: String,
            var id: String,
            var identification: String,
            var isBind: String,
            var isSupplier: String,
            var keyword: Any,
            var lat: Any,
            var lng: Any,
            var memberId: String?,
            var memberLevelId: String,
            var memberLevelLogo: String?,
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
            var refereeUserName: Any,
            var refereeUserNum: Any,
            var referrerUserCount: Int,
            var remark: Any,
            var serverArea: Any,
            var serverAreaId: Any,
            var serverAreaName: Any,
            var sex: String,
            var shanghejiaId: String,
            var sort: Any,
            var state: String,
            var surplusIncome: Long,
            var todayContributeNum: String,
            var todayContributeUpNum: String,
            var totalIncome: Long,
            var userLink: String,
            var uuid: Any,
            var weixinNo: Any,
            var weixinOpenid: Any,
            var weixinUid: Any,
            var weixinUnionid: Any,
            var xinlangOpenid: String,
            var xinlangUid: Any,
            var detailPrice: Long,
            var detailBean: Long,
            var realName: String,
            var influenceNum: Long
    ) {
        fun getInfluenceNumTv(): String {
            return "影响力 $influenceNum"
        }
        fun getLevel(): String {      //用户的等级
            return if (memberLevelId.isNullOrEmpty()) {
                ""
            } else {
                memberLevelId
            }
        }

        fun getIncome(): String {
            return PriceUtils.getPrice(surplusIncome)
        }

        fun getMoble(): String {
            return if (!identification.isNullOrEmpty() && identification == "1" && !phone.isNullOrEmpty()) {
                phone
            } else {
                "暂无"
            }
        }

        fun getBeanNumber(): String {
            return beanNum.toLong().toString()
        }

        fun getTodayContributeNumber(): String {
            return if (!todayContributeNum.isNullOrEmpty()) {
                if (!memberId.isNullOrEmpty() && memberId == UserDetailModel.GOLD) {
                    //显示分子分母
                    todayContributeNum + "/" + if (todayContributeUpNum.isNullOrEmpty()) "0" else todayContributeUpNum
                } else {
                    //不显示分子分母
                    todayContributeNum
                }
            } else {
                "0"
            }
        }

        //确认兑换
        //是否显示糖豆
        fun getIsShowJellyBean(): Boolean {
            return UserModel.memberLevelId != UserDetailModel.GOLD
        }

        //是否显示零钱
        fun getIsShowSurplusIncome(): Boolean {
            return UserModel.memberLevelId == UserDetailModel.GOLD
        }

        //糖豆的数量
        fun getDetailBeanNum(): String {
            return "${beanNum}个"
        }


        //设置与获取价格
        fun setExChangePrice(price: Long) {
            detailPrice = price
        }

        fun getExChangePrice(): Long {
            return detailPrice
        }

        //设置与获取糖豆
        fun setExChangeBean(bean: Long) {
            detailBean = bean
        }

        fun getExChangeBean(): Long {
            return detailBean
        }

        fun getVipTv(): String {
            return if (memberId == UserDetailModel.GOLD) {
                "广告余额"
            } else {
                "糖豆"
            }
        }

        //赠送糖豆显示的提示文字
        fun getSendNumHint(): String {
            return if (memberId == UserDetailModel.GOLD) {
                "输入您要赠送多少广告余额/当前广告余额${beanNum.toLong()}"
            } else {
                "输入您要赠送多少糖豆/当前糖豆${beanNum.toLong()}"
            }
        }

        fun getSendNum(): String {
            return if (memberId == UserDetailModel.GOLD) {
                "赠送广告余额"
            } else {
                "赠送糖豆"
            }
        }
    }

    companion object {
        // 青铜
        const val BRONZE = "d90e0fdc-ec0b-11e8-82ac-00163e08f013"

        // 白银
        const val SILVER = "db31c102-ec0b-11e8-82ac-00163e08f013"

        // 黄金
        const val GOLD = "dbb702f0-ec0b-11e8-82ac-00163e08f013"
    }
}