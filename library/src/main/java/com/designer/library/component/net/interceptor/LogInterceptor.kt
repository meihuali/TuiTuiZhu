/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.net.interceptor

import android.util.Log
import com.yanzhenjie.kalle.Response
import com.yanzhenjie.kalle.connect.Interceptor
import com.yanzhenjie.kalle.connect.http.Chain
import java.io.IOException

/**
 * 响应体的日志打印器
 */
class LogInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        Log.d(url, request.copyParams().entrySet().toString())

        val response = chain.proceed(request)
        response.headers().add("url", url)
        return response
    }
}
