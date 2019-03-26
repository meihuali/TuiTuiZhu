/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午10:02
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.designer.library.utils.RegexUtils
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivitySendJellyBeanBinding
import com.tonghechuanmei.android.model.BaseModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.activity_send_jelly_bean.*
import org.jetbrains.anko.toast

class SendJellyBeanActivity : BaseToolbarActivity<ActivitySendJellyBeanBinding>() {
    override fun initView() {
        title = "赠送"
        binding.v = this
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_jelly_bean)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                when {
                    et_phone.text.toString().isEmpty() -> toast(resources.getString(R.string.phone_no_empty))
                    et_price.text.toString().isEmpty() -> toast("赠送金额不能为空")
                    !RegexUtils.isMobileExact(et_phone.text.toString()) -> toast(resources.getString(R.string.phone_error))
                    et_price.text.toString().toLong() <= 0 -> toast("赠送金额不能小于或等于0")
                    else -> {
                        post<BaseModel>("user/sendUserBean.json") {
                            param("sendNum", et_price.text.toString().trim())
                            param("sendPhone", et_phone.text.toString().trim())
                            param("userId", UserModel.userId)
                        }.dialog(this) {
                            toast("赠送成功")
                            finish()
                        }
                    }
                }
            }
        }
    }
}
