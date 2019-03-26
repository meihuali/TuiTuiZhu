/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 上午11:53
 */

package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.BaseModel
import com.tonghechuanmei.android.model.LoginInfoModel
import io.reactivex.Observable
import java.io.File

fun login(
    unionId: String? = null,
    openId: String? = null,
    headImage: String? = null,
    nickName: String? = null
): Observable<LoginInfoModel> {
    return post("userinfo/login.json") {
        param("weixinUnionid", unionId)
        param("weixinOpenid", openId)
        param("headImg", headImage)
        param("nickName", nickName)
    }
}

fun userInfo(): Observable<String> {
    return post<String>("shanghejia/combination/combinationlist.json") {
        param("", "")
        param("", "")
    }
}

/**
 * 手机短信验证码发送-江波-ok
 */
fun sendSms(phone: String): Observable<Any> {
    return post("userinfo/sentsms.json") {
        param("phone", phone)
    }
}

/**
 * 手机短信验证码发送-江波-ok
 */
fun checkSms(code: String, phone: String): Observable<BaseModel> {
    return post("userinfo/checksms.json") {
        param("code", code)
        param("phone", phone)
    }
}

/**
 * 上传文件
 */
fun upLoad(file: File): Observable<BaseModel> {
    return post("upload/uploadindex.html") {
        file("file", file)
    }
}