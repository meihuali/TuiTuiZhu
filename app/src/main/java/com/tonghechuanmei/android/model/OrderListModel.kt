/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 下午3:14
 */

package com.tonghechuanmei.android.model

import com.designer.library.widget.PriceUtils

data class OrderListModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val pageInfo: PageInfo,
        val resultList: List<Result>
    ) {
        data class Result(
            val acitivtyCode: Any,
            val acitivtyId: Any,
            val activityType: Any,
            val beanPrice: Int,
            val chargeId: Any,
            val currentLevelSort: Int,
            val deliveryCompany: Any,
            val freightPrice: Any,
            val groupLackNum: Int,
            val groupState: Any,
            val imUid: Any,
            val isCalculated: String,
            val keywords: Any,
            val orderCreateTime: Long,
            val orderId: String,
            val orderNo: String,
            val orderPrice: Int,
            val orderState: String,
            val orderType: String,
            val payNo: String,
            val payPrice: Any,
            val payTime: Long,
            val payType: String,
            val products: List<Product>,
            val receiverAddress: String,
            val receiverCityArea: String,
            val receiverName: String,
            val receiverPhone: String,
            val refundId: Any,
            val remark: String,
            val storeId: Any,
            val storeImUid: String,
            val storeImage: Any,
            val storeName: Any,
            val totalBuyNum: Int,
            val tradeNo: String,
            val userAddressId: String,
            val userId: String,
            val userName: String,
            val userPhone: String,
            val waybillNo: Any
        ) {
            fun getReceiver(): String {
                return "收件人：$receiverName"
            }

            fun getStateTv(): String {
                return if (orderState == "2") {
                    "待发货"
                } else {
                    "已发货"
                }
            }

            fun getAddressTv(): String {
                return if (receiverCityArea.isNullOrEmpty()) "" else receiverCityArea + if (receiverAddress.isNullOrEmpty()) "" else receiverAddress
            }

            fun getPriceTv(): String {
                return if (currentLevelSort >= 11) {  //黄金会员
                    "¥${PriceUtils.getPrice(products[0].productPrice)} × ${products[0].buyNum}"
                } else {                            //非黄金会员
                    "${products[0].productPrice}糖豆 × ${products[0].buyNum}"
                }
            }

            fun getTradeNoTv(): String {
                return "快递单号：$waybillNo"
            }

            fun getIsShow(): Boolean {
                return orderState == "3"
            }

            fun getOrderNoTv(): String {
                return "订单编号：$orderNo"
            }

            data class Product(
                val buyNum: Int,
                val ids: Any,
                val inventory: Int,
                val orderId: String,
                val productId: String,
                val productImage: String,
                val productName: String,
                val productPrice: Long,
                val standardDataId: Any,
                val standardDataKey: Any,
                val standardDataKeyName: String
            )
        }

        data class PageInfo(
            val acitivtyCode: Any,
            val acitivtyCodes: List<Any>,
            val acitivtyId: Any,
            val activityState: Any,
            val activityType: Any,
            val allcount: Int,
            val allpage: Int,
            val beanPrice: Any,
            val chargeId: Any,
            val checkTime: Any,
            val createTime: Any,
            val currentLevelSort: Any,
            val dateType: Any,
            val dealTime: Any,
            val deliverTime: Any,
            val deliveryCode: Any,
            val deliveryCompany: Any,
            val detail: Any,
            val endTime: Any,
            val freightPrice: Any,
            val groupState: Any,
            val groupStates: List<Any>,
            val id: Any,
            val invoiceId: Any,
            val invoiceState: Any,
            val isCalculated: Any,
            val isDelivery: Any,
            val isToConfirm: Any,
            val orderId: Any,
            val orderIds: Any,
            val orderKeywords: Any,
            val orderNo: Any,
            val orderNos: Any,
            val orderPrice: Any,
            val orderby: String,
            val page: Int,
            val payCouponId: Any,
            val payCouponPrice: Any,
            val payIncome: Any,
            val payIntegral: Any,
            val payNo: Any,
            val payPrice: Any,
            val payRedEnvelope: Any,
            val payTime: Any,
            val payType: Any,
            val phone: Any,
            val productId: Any,
            val productNum: Any,
            val qkeyword: Any,
            val receiveEndTime: Any,
            val receiveName: Any,
            val receivePhone: Any,
            val receiveTime: Any,
            val referenceUserId: Any,
            val remark: Any,
            val rows: Int,
            val sellerName: Any,
            val start: Int,
            val startTime: Any,
            val state: String,
            val states: List<Any>,
            val statisticsDate: Any,
            val statisticsDateEnd: Any,
            val statisticsDateStart: Any,
            val statisticsMonth: Any,
            val storeId: Any,
            val storeIds: Any,
            val storeName: Any,
            val tradeNo: Any,
            val type: Any,
            val types: List<Any>,
            val updateTime: Any,
            val userAddressId: Any,
            val userDelState: Any,
            val userId: Any,
            val userName: Any,
            val waybillNo: Any
        )
    }
}