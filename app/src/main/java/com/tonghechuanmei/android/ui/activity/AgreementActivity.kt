/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-30 下午12:55
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityAgreementBinding
import kotlinx.android.synthetic.main.activity_agreement.*

class AgreementActivity : BaseToolbarActivity<ActivityAgreementBinding>() {
    override fun initView() {
        title = "用户许可协议"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl("http://www.tonghechuanmei.com/tongheweb/html/protocol.html")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)
    }
}
