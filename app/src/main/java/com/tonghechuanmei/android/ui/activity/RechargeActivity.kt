/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-16 上午10:30
 */

package com.tonghechuanmei.android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityRechargeBinding
import kotlinx.android.synthetic.main.activity_recharge.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

/**
 * 活动余额充值
 */
class RechargeActivity : BaseToolbarActivity<ActivityRechargeBinding>(),
    RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    override fun initView() {
        title = "活动余额充值"
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)
        binding.v = this
        radio.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.zfb_rb -> {
                startActivityForResult<RechargeMoneyActivity>(1, "tag" to "支付宝")
            }
            R.id.wx_rb -> {
                startActivityForResult<RechargeMoneyActivity>(2, "tag" to "微信")
            }
            R.id.yl_rb -> {
                startActivityForResult<RechargeMoneyActivity>(3, "tag" to "银联")
            }
            R.id.offline_pay_rb -> {
                startActivityForResult<TaskReviewActivity>(4)
            }
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        if (v.id == R.id.confirm) {
            toast("确定")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                1 -> {
                    toast("支付宝")
                }
                2 -> {
                    toast("微信")
                }
                3 -> {
                    toast("银联")
                }
                else -> {
                    toast("上传凭证")
                }
            }
        }
    }
}
