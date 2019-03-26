/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/9/18 6:35 PM
 */

package com.tonghechuanmei.android.utils

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tonghechuanmei.android.ui.activity.PaySuccessActivity
import org.jetbrains.anko.startActivity


fun FragmentActivity.handlePinPlusResult(data: Intent?, failBlock: FragmentActivity.() -> Unit) {
    if (data != null) {
        val result = data.extras!!.getString("pay_result")
        /*
        * "success" - 支付成功
        * "fail"    - 支付失败
        * "cancel"  - 取消支付
        * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
        * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
        */
        when (result) {
            "success" -> startActivity<PaySuccessActivity>("payState" to true)
            "fail", "cancel", "invalid", "unknown" -> {
                failBlock()
            }
        }
    }
}

fun Fragment.handlePinPlusResult(data: Intent?, failBlock: FragmentActivity.() -> Unit) {
    if (data != null) {
        val result = data.extras!!.getString("pay_result")
        /*
        * "success" - 支付成功
        * "fail"    - 支付失败
        * "cancel"  - 取消支付
        * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
        * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
        */
        when (result) {
            "success" -> activity?.startActivity<PaySuccessActivity>("payState" to true)
            "fail", "cancel", "invalid", "unknown" -> {
                activity?.failBlock()
            }
        }
    }
}