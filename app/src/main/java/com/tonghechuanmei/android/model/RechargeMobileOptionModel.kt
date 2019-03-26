/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/9/18 10:31 AM
 */

package com.tonghechuanmei.android.model

import android.os.Parcelable
import android.text.SpannableStringBuilder
import com.designer.library.utils.SpanUtils
import com.designer.library.utils.format
import kotlinx.android.parcel.Parcelize

data class RechargeMobileOptionModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val phone: String?,
        val ratio: Double,
        val rechargeKindModels: List<RechargeKindModel>,
        val userId: String
    ) {

        @Parcelize
        data class RechargeKindModel(
            val createTime: String,
            val id: String,
            val imgUrl: String,
            val name: String,
            val pirce: String,
            val realPrice: Long,
            val sortNo: String,
            val state: String,
            val title: String,
            val type: String
        ) : Parcelable {

            var balance: Long = 0
                get() {
                    return if (field < 0) {
                        0
                    } else {
                        field
                    }
                }


            var phone: String = ""
            var payWay: String? = null

            var aliPayCheked: Boolean = false
                set(value) {
                    if (value) {
                        payWay = "1"
                    }
                }

            var wxPayChecked: Boolean = false
                set(value) {
                    if (value) {
                        payWay = "2"
                    }
                }

            fun getPriceStr(): String {
                return "售价${realPrice.format()}元"
            }

            fun getOrderPayPriceStr(): SpannableStringBuilder {
                return SpanUtils().append("¥ ").setFontSize(16, true).append(realPrice.format()).create()
            }

            fun getOrderInfo(): String {
                return "$phone ${name}充值 自动充值"
            }

            fun getPayInfo(): String {
                return if ((balance > 0)) {
                    "零钱 (${balance.format()}元)"
                } else "第三方支付"
            }

            fun getRealPayMoney(): String {
                return if (realPrice <= balance) {
                    "立即支付"
                } else {
                    return "立即支付 ${(realPrice - balance).format()} 元"
                }
            }

            /**
             * 显示第三方支付方式
             * @return Boolean
             */
            fun getThirdPayWayVisible(): Boolean {
                return if (balance < realPrice) {
                    true
                } else {
                    payWay = "4"
                    false
                }
            }

        }
    }
}