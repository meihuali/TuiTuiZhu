/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-28 上午9:45
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPaySuccessBinding
import kotlinx.android.synthetic.main.activity_base_toolbar.*
import kotlinx.android.synthetic.main.activity_pay_success.*
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class PaySuccessActivity : BaseToolbarActivity<ActivityPaySuccessBinding>() {
    var payState = false     //是否支付成功,true成功 false失败

    override fun initView() {
        binding.v = this
        title = "支付结果"

        payState = intent.getBooleanExtra("payState", false)
        if (payState) {
            tv_str.text = "支付成功"
            iv_pay_status.setImageResource(R.drawable.pic_ch_cg)
        } else {
            tv_str.text = "支付失败"
            iv_pay_status.setImageResource(R.drawable.pic_ch_sb)
        }

        swipeEnable = false
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        action_back.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            intoMain()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_success)
    }

    override fun onClick(v: View) {
        intoMain()
    }

    private fun intoMain() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onBackPressed() {
        intoMain()
    }
}
