/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-15 下午7:21
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityAlipayAccountBinding
import com.tonghechuanmei.android.model.UserDetailModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.activity_alipay_account.*
import org.jetbrains.anko.toast

/**
 * 修改帐户-添加帐户  0修改帐户  1添加帐户
 */
class AlipayAccountActivity : BaseToolbarActivity<ActivityAlipayAccountBinding>() {
    override fun initView() {
        title = if (intent.getStringExtra("tag") == "0") {
            getUserDetail().dialog(this) {
                if (it.msg != "-4") {
                    tv_name.setText(it.data.realName)
                    tv_account.setText(it.data.alipayNo)
                }
            }
            "修改帐户"
        } else {
            "添加帐户"
        }
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alipay_account)
        binding.v = this
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                when {
                    tv_name.text.toString().isEmpty() -> toast("姓名不能为空")
                    tv_account.text.toString().isEmpty() -> toast("支付宝帐户不能为空")
                    else -> {
                        post<UserDetailModel>("user/model.json") {
                            param("id", UserModel.userId)
                            param("realName", tv_name.text.toString())
                            param("alipayNo", tv_account.text.toString())
                        }.dialog(this) {
                            if (intent.getStringExtra("tag") == "0") {
                                toast("修改成功")
                            } else {
                                toast("添加成功")
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }
}
