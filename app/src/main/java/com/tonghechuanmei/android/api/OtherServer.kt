/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午6:21
 */

package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.*
import io.reactivex.Observable

object OtherServer {
    fun getProductSearch(firstCategoryId: String, stateList: String, page: Int): Observable<ProductSearchModel> {
        return post("product/search.json") {
            param("firstCategoryId", firstCategoryId)
            param("stateList", stateList)
            param("page", page)
        }
    }

    fun getInfoById(id: String): Observable<ProductSearchDetailModel> {
        return post("product/getinfobyid.json") {
            param("id", id)
            param("userId", UserModel.userId)
        }
    }

    //地址列表
    fun getAddressList(): Observable<UserAddressEntity> {
        return post("userinfo/getaddress.json") {
            param("userId", UserModel.userId)
        }
    }

    //添加地址 编辑地址
    fun addAddress(
        address: String,
        cityArea: String,
        isDefault: String,
        name: String,
        phone: String,
        tel: String,
        id: String?
    ): Observable<AddAddressModel> {
        return post("userinfo/addaddress.json") {
            param("userId", UserModel.userId)
            param("address", address)
            param("cityArea", cityArea)
            param("isDefault", isDefault)
            param("name", name)
            param("phone", phone)
            param("tel", tel)
            if (id != null) {
                param("id", id)
            }
        }
    }

    //根据上级id获取省市区-江波-ok
    fun getAreaCurData(parentId: String?): Observable<AreaEntity> {
        return post("area/getcurdata.json") {
            if (parentId != null) {
                param("parentId", parentId)
            }
        }
    }

    //根据上级id获取省市区-江波-ok
    fun delAddress(ids: String): Observable<BaseModel> {
        return post("userinfo/deladdress.json") {
            param("ids", ids)
            param("userId", UserModel.userId)
        }
    }

    //设置默认地址
    fun setDefultAddress(id: String): Observable<BaseModel> {
        return post("userinfo/setdefultaddress.json") {
            param("id", id)
            param("userId", UserModel.userId)
        }
    }

    //我的兑换列表
    fun getOrderList(state: String): Observable<OrderListModel> {
        return post("order/orderlist.json") {
            param("state", state)
            param("userId", UserModel.userId)
        }
    }
}