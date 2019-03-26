/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-7 上午9:52
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.designer.library.component.net.exception.ResponseException
import com.designer.library.component.net.observer.DialogObserver
import com.designer.library.component.net.post
import com.designer.library.utils.RegexUtils
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivitySendJellyBeanPhoneBinding
import com.tonghechuanmei.android.model.SendJellyBeanPhoneModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.activity_send_jelly_bean_phone.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SendJellyBeanPhoneActivity : BaseToolbarActivity<ActivitySendJellyBeanPhoneBinding>() {
    override fun initView() {
        title = "赠送"
        binding.v = this
    }

    override fun initData() {
        tv_hint_error.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_jelly_bean_phone)
    }

    @SuppressLint("AutoDispose")
    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tv_confirm -> {
                when {
                    tv_send_jelly_phone.text.toString().trim().isEmpty() -> toast("手机号不能为空")
                    !RegexUtils.isMobileExact(tv_send_jelly_phone.text.toString()) -> toast(resources.getString(R.string.phone_error))
                    else -> {
                        post<SendJellyBeanPhoneModel>("userBean/searchPhone.json") {
                            param("sendPhone", tv_send_jelly_phone.text.toString())
                            param("userId", UserModel.userId)
                        }.subscribe(object : DialogObserver<SendJellyBeanPhoneModel>(this) {
                            override fun onNext(t: SendJellyBeanPhoneModel) {
                                startActivity<SendJellyBeanNumberActivity>("model" to t.data)
                            }

                            override fun onError(e: Throwable) {
                                dialog.dismiss()
                                tv_hint_error.visibility = View.VISIBLE
                                if (e is ResponseException) {
                                    tv_hint_error.text = e.errorMessage
                                } else {
                                    tv_hint_error.text = "请检查网络异常"
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}
