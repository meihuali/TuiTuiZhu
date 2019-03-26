/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-29 下午7:55
 */

package com.tonghechuanmei.android.model

data class CheckSmsModel(
    val `data`: Data?,
    val msg: String?,
    val success: Boolean?
) {
    data class Data(
        val address: String?,
        val age: Any?,
        val alipayName: Any?,
        val alipayNo: String?,
        val allIncome: Int?,
        val amendmentNo: Int?,
        val bankName: String?,
        val bankNo: String?,
        val bankUserName: String?,
        val birthDay: String?,
        val cityArea: String?,
        val cityServerAreaId: Any?,
        val countyServerAreaId: Any?,
        val createTime: Long?,
        val email: String?,
        val headImg: String?,
        val id: String?,
        val idCard: String?,
        val idCardName: String?,
        val imUid: String?,
        val integral: String?,
        val integralAll: String?,
        val isAuthentication: String?,
        val isCityPartners: Int?,
        val isMarketCommissioner: Int?,
        val isStore: String?,
        val levelId: String?,
        val manageAreaId: String?,
        val name: String?,
        val nickName: Any?,
        val noSettlementIncome: Any?,
        val phone: String?,
        val provinceServerAreaId: Any?,
        val pwd: String?,
        val redEnvelope: Int?,
        val refereesUserId: String?,
        val sex: String?,
        val state: String?,
        val storeId: String?,
        val surplusIncome: Int?,
        val tags: String?,
        val userLink: Any?,
        val weixinAppletOpenid: Any?,
        val weixinNo: String?,
        val weixinOpenid: String?,
        val weixinUnionid: String?
    )
}