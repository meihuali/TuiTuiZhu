/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-8 下午10:29
 */

package com.tonghechuanmei.android.base

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixApplication
import com.taobao.sophix.SophixEntry
import com.taobao.sophix.SophixManager


class SophixStubApplication : SophixApplication() {
    private val TAG = "SophixStubApplication"

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(AndroidApplication::class)
    internal class RealApplicationStub

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //         如果需要使用MultiDex，需要在此处调用。
        // MultiDex.install(this)
        initSophix()
    }

    private fun initSophix() {
        var appVersion = "0.0.0"
        try {
            appVersion = this.packageManager
                .getPackageInfo(this.packageName, 0)
                .versionName
        } catch (e: Exception) {
        }

        // initialize必须放在attachBaseContext最前面，初始化代码直接写在Application类里面，切勿封装到其他类。
        SophixManager.getInstance().setContext(this)
            .setAppVersion(appVersion).setSecretMetaData(null, null, null)
            .setEnableDebug(true)
            .setEnableFullLog()
            .setPatchLoadStatusStub { mode, code, info, handlePatchVersion ->
                Log.d(TAG, "code=$code")

                // 补丁加载回调通知
                when (code) {
                    PatchStatus.CODE_LOAD_SUCCESS -> {
                        // 表明补丁加载成功

                    }
                    PatchStatus.CODE_LOAD_RELAUNCH -> {

                        // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                        // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                    }
                    else -> {
                        // 其它错误信息, 查看PatchStatus类说明
                    }
                }
            }.initialize()
    }

    override fun onCreate() {
        super.onCreate()
        //TODO 每月只能调用600次
        // SophixManager.getInstance().queryAndLoadNewPatch()
    }
}