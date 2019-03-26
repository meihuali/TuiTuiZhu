/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：两津勘吉
 * Date：11/25/18 1:37 AM
 */

package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.amap.api.location.AMapLocation
import java.io.Serializable

object LocationModel : Serializable, BaseObservable() {

    var latitude = 30.129748
    var longitude = 120.080841
    var cityCode = "330100"
    var regionCode = "330100"
    var provinceCode = "330000"
    var city = "杭州市"

    @Bindable
    var address = "浙江省杭州市西湖区"
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }

    /**
     * 高德地图坐标转成当前任务所需坐标信息
     * @param aMapLocation AMapLocation
     */

    var aMapLocation: AMapLocation? = null
        set(value) {
            field = value
            if (field != null) {
                latitude = field!!.latitude
                longitude = field!!.longitude
                provinceCode = field!!.adCode.substring(0, 2) + "0000"
                cityCode = field!!.adCode.substring(0, 4) + "00"
                regionCode = field!!.adCode
                address = field!!.address
                city = field!!.city
            }
        }
}