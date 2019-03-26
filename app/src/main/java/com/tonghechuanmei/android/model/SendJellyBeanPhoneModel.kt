/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-9 下午2:18
 */

package com.tonghechuanmei.android.model

import java.io.Serializable

data class SendJellyBeanPhoneModel(
    val `data`: Data?,
    val msg: String?,
    val success: Boolean?
) : Serializable {
    data class Data(
        val address: Any?,
        val age: Int?,
        val alipayName: Any?,
        val alipayNo: Any?,
        val allContributeNum: Double?,
        val appletOpenid: Any?,
        val bankCardNo: Any?,
        val bankOfDeposit: Any?,
        val beanNum: Double?,
        val birthday: Any?,
        val cardholder: Any?,
        val createTime: Long?,
        val currentContributeValue: Any?,
        val currentLevelNo: Any?,
        val currentMemberLevelSort: Int?,
        val email: Any?,
        val getuiCid: Any?,
        val giveBeanDiscount: Double?,
        val headImg: String?,
        val id: String?,
        val idCard: Any?,
        val idCardBehindImg: Any?,
        val idCardFrontImg: Any?,
        val identification: Any?,
        val isBind: Any?,
        val isStore: Any?,
        val isSupplier: String?,
        val keyword: Any?,
        val lat: Any?,
        val levelName: String?,
        val lng: Any?,
        val loginTimes: Int?,
        val memberId: String?,
        val memberLevelId: String?,
        val memberLevelLogo: Any?,
        val memberName: Any?,
        val name: String?,
        val nextContributeValue: Any?,
        val nextLevelNo: Any?,
        val nextMemberLevelSort: Int?,
        val nickName: String?,
        val noRecorded: Any?,
        val phone: String?,
        val phoneType: Any?,
        val postcode: Any?,
        val pwd: String?,
        val qq: Any?,
        val qqOpenid: Any?,
        val qqUid: Any?,
        val realName: Any?,
        val refereeUserId: Any?,
        val refereeUserName: Any?,
        val refereeUserNum: Int?,
        val referrerUserCount: Int?,
        val remark: Any?,
        val serverArea: Any?,
        val serverAreaId: Any?,
        val serverAreaName: Any?,
        val sex: String?,
        val shanghejiaId: String?,
        val sort: Int?,
        val state: String?,
        val storeId: Any?,
        val surplusIncome: Int?,
        val todayContributeNum: Double?,
        val todayContributeUpNum: Double?,
        val totalIncome: Int?,
        val userLink: String?,
        val uuid: Any?,
        val weixinNo: Any?,
        val weixinOpenid: Any?,
        val weixinUid: Any?,
        val weixinUnionid: String?,
        val xinlangOpenid: String?,
        val xinlangUid: Any?
    ) : Serializable
}