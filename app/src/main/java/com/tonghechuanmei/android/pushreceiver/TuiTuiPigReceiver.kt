/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-7 下午1:21
 */

package com.tonghechuanmei.android.pushreceiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.tonghechuanmei.android.ui.activity.MainActivity
import org.json.JSONObject

/**
 * 自定义接收器
 *
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
class TuiTuiPigReceiver : BroadcastReceiver() {

    private var nm: NotificationManager? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (null == nm) {
            nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        val bundle = intent.extras

        if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
            Log.d(TAG, "JPush用户注册成功")

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
            Log.d(TAG, "接受到推送下来的自定义消息")

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
            Log.d(TAG, "接受到推送下来的通知")

            receivingNotification(context, bundle!!)

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {

            Log.d(TAG, "用户点击打开了通知")
            openNotification(context, bundle!!)
            // 在这里可以自己写代码去定义用户点击后的行为
            val title = bundle.getString(JPushInterface.EXTRA_TITLE)
            val i = Intent(context, MainActivity::class.java)  //自定义打开的界面
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.action!!)
        }
    }

    private fun receivingNotification(context: Context, bundle: Bundle) {
        val title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
        Log.d(TAG, " title : " + title!!)
        val message = bundle.getString(JPushInterface.EXTRA_ALERT)
        Log.d(TAG, "message : " + message!!)
        val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
        Log.d(TAG, "extras : " + extras!!)
    }

    private fun openNotification(context: Context, bundle: Bundle) {
        val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
        var myValue = ""
        try {
            val extrasJson = JSONObject(extras)
            myValue = extrasJson.optString("myKey")
        } catch (e: Exception) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e)
            return
        }

        when (myValue) {

        }
    }

    companion object {
        private val TAG = "JPush"
    }
}
