/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-26 下午3:31
 */

package com.tonghechuanmei.android.model

data class VipUpgradeModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val auditTime: Any,
        val chargeId: Any,
        val createTime: Long,
        val dealTime: Any,
        val id: String,
        val memberLevelSort: Int,
        val memberLevelTargetSort: Int,
        val orderNo: String,
        val orderPrice: Int,
        val payBeanNum: Any,
        val payCouponId: Any,
        val payCouponVal: Any,
        val payIncome: Any,
        val payNo: String,
        val payPrice: Long,
        val payRedEnvelope: Any,
        val payTime: Any,
        val payType: Any,
        val pingOrderId: Any,
        val remark: String,
        val state: String,
        val targetId: Any,
        val title: String,
        val tradeNo: Any,
        val type: String,
        val userId: String
    )
}