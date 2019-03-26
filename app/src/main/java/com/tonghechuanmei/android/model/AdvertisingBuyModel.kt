/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-21 下午2:01
 */

package com.tonghechuanmei.android.model

import android.os.Parcel
import android.os.Parcelable
import com.designer.library.widget.PriceUtils

/**
 * 购买活动位
 */
class AdvertisingBuyModel() : Parcelable {
    var location: String = ""
    var adImage: String = ""
    var adSpaceId: String = ""    //活动位id
    var amount: Long = 0L
    var duration: String = ""
    var endTime: String = ""
    var name: String = ""
    var payPrice: Long = 0L
    var payType: String = ""
    var startTime: String = ""
    var taskId: String = ""
    var taskName: String = ""
    var userId: String = ""
    fun getPrice(): String {
        return "¥${PriceUtils.getPrice(payPrice)}"
    }

    fun getConfirmPay(): String {
        return "确认并支付${PriceUtils.getPrice(payPrice)}"
    }

    constructor(parcel: Parcel) : this() {
        location = parcel.readString()
        adImage = parcel.readString()
        adSpaceId = parcel.readString()
        amount = parcel.readLong()
        duration = parcel.readString()
        endTime = parcel.readString()
        name = parcel.readString()
        payPrice = parcel.readLong()
        payType = parcel.readString()
        startTime = parcel.readString()
        taskId = parcel.readString()
        taskName = parcel.readString()
        userId = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(location)
        parcel.writeString(adImage)
        parcel.writeString(adSpaceId)
        parcel.writeLong(amount)
        parcel.writeString(duration)
        parcel.writeString(endTime)
        parcel.writeString(name)
        parcel.writeLong(payPrice)
        parcel.writeString(payType)
        parcel.writeString(startTime)
        parcel.writeString(taskId)
        parcel.writeString(taskName)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdvertisingBuyModel> {
        override fun createFromParcel(parcel: Parcel): AdvertisingBuyModel {
            return AdvertisingBuyModel(parcel)
        }

        override fun newArray(size: Int): Array<AdvertisingBuyModel?> {
            return arrayOfNulls(size)
        }
    }
}