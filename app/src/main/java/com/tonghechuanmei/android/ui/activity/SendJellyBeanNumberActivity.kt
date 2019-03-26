/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-7 上午10:09
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivitySendJellyBeanNumberBinding
import com.tonghechuanmei.android.model.BaseModel
import com.tonghechuanmei.android.model.SendJellyBeanPhoneModel
import com.tonghechuanmei.android.model.UserDetailModel
import com.tonghechuanmei.android.model.UserModel
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_send_jelly_bean_number.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SendJellyBeanNumberActivity : BaseToolbarActivity<ActivitySendJellyBeanNumberBinding>() {
    var tag = 0
    lateinit var model: UserDetailModel.Data
    override fun initView() {
        title = "赠送"
    }

    override fun initData() {
        getUserDetail().net(this) {
            if (it.msg == "1") {
                binding.m = it.data
                model = it.data
                tag = if ((if (model.memberId.isNullOrEmpty()) "" else model.memberId) == UserDetailModel.GOLD) {
                    0
                } else {
                    1
                }
                binding.v = this@SendJellyBeanNumberActivity
            } else {
                UserModel.clear()
                val shareApi = UMShareAPI.get(this@SendJellyBeanNumberActivity)
                shareApi.deleteOauth(this@SendJellyBeanNumberActivity, SHARE_MEDIA.WEIXIN, null)
                startActivity<LoginActivity>()
                com.designer.library.base.BaseActivity.finishAll("LoginActivity")
                return@net
            }
        }
        binding.model = intent.getSerializableExtra("model") as SendJellyBeanPhoneModel.Data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_jelly_bean_number)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tv_confirm -> {
                when {
                    et_send_number.text.toString().trim().isEmpty() -> toast("赠送${if (tag == 0) "广告余额" else "糖豆"}不能为空")
                    et_send_number.text.toString().trim().toLong() > model.beanNum -> toast("赠送${if (tag == 0) "广告余额" else "糖豆"}不能超过用户${if (tag == 0) "广告余额" else "糖豆"}")
                    et_send_number.text.toString().trim().toLong() <= 0 -> toast("赠送${if (tag == 0) "广告余额" else "糖豆"}不能小于或等于0")
                    else -> {
                        post<BaseModel>("userBean/sendBean.json") {
                            param("sendNum", et_send_number.text.toString().trim())
                            param("sendPhone", binding.model!!.phone)
                            param("userId", UserModel.userId)
                        }.dialog(this) {
                            toast("赠送成功")
                            RxBus.get().post("refresh_jelly_bean_event ", "刷新糖豆")
                            startActivity<JellyBeanActivity>()
                        }
                    }
                }
            }
        }
    }
}
