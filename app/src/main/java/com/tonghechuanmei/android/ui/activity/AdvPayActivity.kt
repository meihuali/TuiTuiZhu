/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午1:43
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.designer.library.widget.PriceUtils
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.buyAdSpace
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityAdvPayBinding
import com.tonghechuanmei.android.model.AdvertisingBuyModel
import com.tonghechuanmei.android.model.PinPayModel
import kotlinx.android.synthetic.main.activity_adv_pay.*
import org.jetbrains.anko.startActivity

class AdvPayActivity : BaseToolbarActivity<ActivityAdvPayBinding>() {

    lateinit var m: AdvertisingBuyModel

    @SuppressLint("SetTextI18n")
    override fun initView() {
        title = "活动位支付"
        m = intent.getParcelableExtra("m") as AdvertisingBuyModel
        binding.v = this
        binding.m = m
        getUserDetail().dialog(this) {
            tv_adv_pay.text = PriceUtils.getPrice(it.data.surplusIncome) + "元"
            if (it.data.surplusIncome > 0) {
                fl_pay_way_balance.visibility = View.VISIBLE
            } else {
                fl_pay_way_balance.visibility = View.GONE
            }
            if (it.data.surplusIncome >= m.payPrice) {
                ll_adv_icon.visibility = View.GONE
                adv_pay_rg.visibility = View.GONE
                m.payType = "4"
            } else {
                ll_adv_icon.visibility = View.VISIBLE
                adv_pay_rg.visibility = View.VISIBLE
                m.payType = "1"
            }
        }
    }

    override fun initData() {
        adv_pay_rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.adv_pay_rb1 -> {
                    m.payType = "1"
                }
                R.id.adv_pay_rb2 -> {
                    m.payType = "2"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adv_pay)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when {
            v.id == R.id.adv_buy_confirm -> buyAdSpace(
                m.adImage, m.adSpaceId, m.duration.toInt(), m.endTime, m.name,
                m.payType, m.startTime, m.taskId
            ).net(this@AdvPayActivity) {
                val payNo = it.data.payNo
                val amount = it.data.payPrice
                if (it.data.state == "1") {               //不需要调支付
                    startActivity<PaySuccessActivity>("payState" to true, "tag" to "AdvertisingBuyActivity")
                } else {
                    post<PinPayModel>("ping/pingpay.json") {
                        param("orderNo", payNo)
                        param("fromType", "app")
                        param("amount", amount)
                    }.net { b ->
                        Pingpp.createPayment(this@AdvPayActivity, b.data.data)
                    }
                }
            }
            v.id == R.id.iv_pay_way_alipay -> {
                adv_pay_rb1.isChecked = true
                adv_pay_rb2.isChecked = false
                m.payType = "1"
            }
            v.id == R.id.iv_pay_way_wx -> {
                adv_pay_rb1.isChecked = false
                adv_pay_rb2.isChecked = true
                m.payType = "2"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val result = data.extras!!.getString("pay_result")
                    if (result == "success") {
                        startActivity<PaySuccessActivity>("payState" to true, "tag" to "AdvertisingBuyActivity")
                    } else {
                        startActivity<PaySuccessActivity>("payState" to false, "tag" to "AdvertisingBuyActivity")
                    }
                    /* 处理返回值
             * "success" - 支付
             * 成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
                    Log.e("------", result)
                }
            }
        }
    }
}
