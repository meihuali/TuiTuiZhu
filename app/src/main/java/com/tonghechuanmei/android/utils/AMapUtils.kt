/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/23/18 8:54 AM
 */

package com.tonghechuanmei.android.utils

import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.designer.library.base.getApp
import com.tonghechuanmei.android.model.LocationModel
import io.reactivex.Observable

/**
 * 获取当前地址信息
 * @return Observable<AMapLocation>
 */
fun getLocation(): Observable<LocationModel> {
    return Observable.create<LocationModel> {
        val aMapLocationClient = AMapLocationClient(getApp()).apply {
            setLocationListener { aMapLocation ->
                if (!it.isDisposed) {
                    if (aMapLocation != null && aMapLocation.errorCode == 0) {
                        LocationModel.aMapLocation = aMapLocation
                    } else {
                        Log.d(
                            "日志",
                            "定位错误信息 = ${aMapLocation.errorInfo} 错误码 = ${aMapLocation.errorCode}"
                        )
                    }
                    it.onNext(LocationModel)
                    it.onComplete()
                }
                onDestroy()
            }
        }
        val aMapLocationClientOption = AMapLocationClientOption().apply {
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            isOnceLocation = true
        }
        aMapLocationClient.setLocationOption(aMapLocationClientOption)
        aMapLocationClient.startLocation()
    }
}