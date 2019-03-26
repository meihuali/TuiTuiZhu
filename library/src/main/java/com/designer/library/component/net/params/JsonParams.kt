/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.net.params

import org.json.JSONArray

/**
 * 请求参数的接口
 *
 * @param <R> 请求成功的Model
</R> */
interface JsonParams<R> {

    val isPrintString: String

    fun put(name: String, value: Boolean): R

    fun put(name: String, value: Int): R

    fun put(name: String, value: Float): R

    fun put(name: String, value: Double): R

    fun put(name: String, value: String): R

    fun put(name: String, value: JSONArray): R

    fun printString(): R
}
