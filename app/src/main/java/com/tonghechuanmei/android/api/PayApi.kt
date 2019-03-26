package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.PinPayModel
import com.tonghechuanmei.android.model.PingPlusPayWayModel
import io.reactivex.Observable

/**
 * Author     : shandirong
 * Date       : 2018/11/24 13:23
 */

/**
 * 通过活动
 */
fun pay(pingPlusPayWayModel: PingPlusPayWayModel): Observable<PinPayModel> {
    return post("ping/pingpay.json") {
        param("orderNo", pingPlusPayWayModel.data.payNo)
        param("fromType", "app")
        param("amount", pingPlusPayWayModel.data.payPrice)
    }
}