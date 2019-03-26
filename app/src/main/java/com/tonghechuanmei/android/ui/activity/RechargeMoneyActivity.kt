/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-16 下午5:07
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityRechargeMoneyBinding

/**
 * 活动余额充值下一页
 */
class RechargeMoneyActivity : BaseToolbarActivity<ActivityRechargeMoneyBinding>() {
    override fun initView() {
        title = "活动余额充值"
        binding.m = intent.getStringExtra("tag")
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_money)
    }
}
