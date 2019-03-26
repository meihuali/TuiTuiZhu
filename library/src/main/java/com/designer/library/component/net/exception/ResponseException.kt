/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.net.exception

/**
 *  对应网络请求后台定义的错误信息
 * @property errorMessage String 网络请求错误信息
 * @property errorCode Int 网络请求错误码
 * @constructor
 */
class ResponseException(val errorMessage: String, val errorCode: Int) : Throwable()
