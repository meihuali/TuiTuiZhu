/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 11:18 AM
 */

package com.tonghechuanmei.android.api

import com.designer.library.component.log.LogCat
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.yanzhenjie.kalle.Response
import com.yanzhenjie.kalle.simple.Converter
import com.yanzhenjie.kalle.simple.SimpleResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * 网络请求GSON转换器
 */
class StupidParseConverter : Converter {

    private val GSON = GsonBuilder().serializeNulls().create()

    @Throws(Exception::class)
    override fun <S, F> convert(
        succeed: Type,
        failed: Type,
        response: Response,
        fromCache: Boolean
    ): SimpleResponse<S, F> {
        var succeedData: S? = null
        var failedData: F? = null
        val json: String = response.body().string()

        LogCat.json(response.headers().get("url")[0], json)

        var code = response.code()
        when {
            code in 200..299 -> {

                try {
                    val jsonObject = JSONObject(json)
                    val responseCode = jsonObject.optInt("msg")

                    when (responseCode) {
                        1 -> run {
                            if (succeed == String::class.java) {
                                succeedData = json as S
                            } else {
                                try {
                                    succeedData = GSON.fromJson<S>(json, succeed)
                                } catch (e: JsonSyntaxException) {
                                    e.printStackTrace()
                                    failedData = "无法解析数据" as F
                                }
                            }
                        }
                        -4 -> {
                            if (succeed == String::class.java) {
                                succeedData = json as S
                            } else {
                                try {
                                    succeedData = GSON.fromJson<S>(
                                        "{\"data\":null,\"success\":\"true\",\"msg\":\"$responseCode\"}",
                                        succeed
                                    )
                                } catch (e: JsonSyntaxException) {
                                    e.printStackTrace()
                                    failedData = "无法解析数据" as F
                                }
                            }
                        }
                        else -> {
                            failedData = jsonObject.optString("data") as F
                            code = responseCode
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    failedData = "无法解析错误信息" as F
                }
            }
            code in 400..499 -> failedData = "发生异常错误" as F
            code >= 500 -> failedData = "服务器开小差啦" as F
        }

        return SimpleResponse.newBuilder<S, F>().code(code)
            .headers(response.headers())
            .fromCache(fromCache)
            .succeed(succeedData)
            .failed(failedData)
            .build()
    }
}
