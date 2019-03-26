/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午6:21
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.net
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityExchangeCenterBinding
import com.tonghechuanmei.android.model.UserDetailModel
import com.tonghechuanmei.android.model.UserModel
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ExchangeCenterActivity : BaseToolbarActivity<ActivityExchangeCenterBinding>() {
    lateinit var userModel: UserDetailModel.Data
    override fun initView() {
        title = "兑换中心"
        binding.v = this
    }

    override fun initData() {
        getUserDetail().net(this) {
            if (it.msg != "-4") {
                userModel = it.data
                UserModel.saveUser(userModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_center)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.ll1 -> {
                openApp("com.tjkj.shanghejia")
            }
            R.id.ll2 -> {
                toast("暂无开放")
            }
            R.id.ll3 -> {
                if (this::userModel.isInitialized) {
                    startActivity<ExChangeActivity>()
                }
            }
        }
    }

    private fun openApp(packageName: String) {
        var pi: PackageInfo? = null
        try {
            pi = packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(pi!!.packageName)

        val apps = packageManager.queryIntentActivities(resolveIntent, 0)

        val ri = apps.iterator().next()
        if (ri != null) {
            val className = ri.activityInfo.name
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(ri.activityInfo.packageName, className)
            intent.component = cn
            startActivity(intent)
        }
    }
}
