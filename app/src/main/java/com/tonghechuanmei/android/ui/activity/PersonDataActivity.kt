/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-16 下午7:58
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.designer.library.component.net.observer.state
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPersonDataBinding
import com.tonghechuanmei.android.model.UserDetailModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.otherserver.ShippingAddressActivity
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.startActivity

/**
 * 个人资料
 */
class PersonDataActivity : BaseToolbarActivity<ActivityPersonDataBinding>() {
    lateinit var model: UserDetailModel.Data
    override fun initView() {
        title = "个人资料"
    }

    override fun initData() {
        getUserDetail().state(rootView) {
            binding.m = it.data
            model = it.data
            binding.v = this@PersonDataActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_data)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                alert {
                    message = "是否退出当前账号"
                    okButton {
                        UserModel.clear()
                        val shareApi = UMShareAPI.get(this@PersonDataActivity)
                        shareApi.deleteOauth(this@PersonDataActivity, SHARE_MEDIA.WEIXIN, null)
                        JPushInterface.deleteAlias(this@PersonDataActivity, 1)
                        startActivity<LoginActivity>()
                        com.designer.library.base.BaseActivity.finishAll("LoginActivity")
                    }
                    noButton {
                        it.dismiss()
                    }
                }.show()
            }
            R.id.address_tv -> {
                startActivity<ShippingAddressActivity>()
            }
            R.id.suggest_right -> {
                startActivity<SuggestActivity>()
            }
            R.id.suggest_return -> {
                startActivity<SuggestActivity>()
            }
            R.id.iv_return_address -> {
                startActivity<ShippingAddressActivity>()
            }
        }
    }
}
