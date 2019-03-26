/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-3 下午7:45
 */

package com.tonghechuanmei.android.model

data class ConfirmOrderModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val order: Order,
        val orderDetail: OrderDetail
    ) {
        data class OrderDetail(
            val acitivtyId: Any,
            val activityType: Any,
            val buyNum: Int,
            val createTime: Long,
            val giftId: Any,
            val id: String,
            val orderId: String,
            val productId: String,
            val productPrice: Int,
            val standardDataId: Any,
            val standardDataKey: String,
            val standardDataKeyName: String,
            val storeId: Any,
            val userId: String
        )

        data class Order(
            val acitivtyCode: Any,
            val acitivtyId: Any,
            val activityState: Any,
            val activityType: Any,
            val beanPrice: Int,
            val chargeId: Any,
            val checkTime: Any,
            val createTime: Long,
            val currentLevelSort: Int,
            val dealTime: Any,
            val deliverTime: Any,
            val deliveryCode: Any,
            val deliveryCompany: Any,
            val detail: String,
            val freightPrice: Any,
            val id: String,
            val invoiceId: Any,
            val invoiceState: Any,
            val isCalculated: String,
            val orderId: Any,
            val orderKeywords: Any,
            val orderNo: String,
            val orderPrice: Int,
            val payCouponId: Any,
            val payCouponPrice: Any,
            val payIncome: String,
            val payIntegral: Any,
            val payNo: String,
            val payPrice: Long,
            val payRedEnvelope: Any,
            val payTime: Any,
            val payType: String,
            val receiveTime: Any,
            val referenceUserId: String,
            val remark: String,
            val state: String,
            val storeId: Any,
            val tradeNo: Any,
            val type: String,
            val updateTime: Long,
            val userAddressId: String,
            val userDelState: String,
            val userId: String,
            val waybillNo: Any
        )
    }
}