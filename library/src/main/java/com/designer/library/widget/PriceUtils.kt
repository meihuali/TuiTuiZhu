/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.widget


import java.text.DecimalFormat

object PriceUtils {
    fun getPrice(price: Int): String {
        val decimalFormat = DecimalFormat("######0.00")
        return decimalFormat.format(price.toDouble() / 100)
    }

    fun getPrice(price: Long): String {
        val decimalFormat = DecimalFormat("######0.00")
        return if (price % 100 == 0L) {
            // 如果整除不需要小数点后两位
            (price / 100).toString()
        } else {
            decimalFormat.format(price.toDouble() / 100)
        }
    }

    fun getPriceForTenThousand(price: Int): String {
        val decimalFormat = DecimalFormat("######0.00")
        return decimalFormat.format(price.toDouble() / 1000000)
    }

    fun getPriceForTenThousand(price: Long): String {
        val decimalFormat = DecimalFormat("######0.00")
        return if (price % 1000000 == 0L) {
            // 如果整除不需要小数点后两位
            (price / 1000000).toString()
        } else {
            decimalFormat.format(price.toDouble() / 1000000)
        }
    }
}
