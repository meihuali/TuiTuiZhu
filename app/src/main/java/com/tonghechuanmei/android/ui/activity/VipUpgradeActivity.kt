/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-25 下午7:54
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityVipUpgradeBinding
import com.tonghechuanmei.android.model.PinPayModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.model.VipPriceModel
import com.tonghechuanmei.android.model.VipUpgradeModel
import kotlinx.android.synthetic.main.activity_vip_upgrade.*
import org.jetbrains.anko.startActivity

class VipUpgradeActivity : BaseToolbarActivity<ActivityVipUpgradeBinding>() {
    var sort = 0
    var targetSort = 0
    var payType = "1"
    var price = 0L
    lateinit var model: VipUpgradeModel
    override fun initView() {
        title = "会员升级"
        sort = intent.getIntExtra("sort", 0)
        targetSort = intent.getIntExtra("targetSort", 0)
        binding.v = this
        initPrice()
    }

    @SuppressLint("SetTextI18n")
    private fun initPrice() {
        post<VipPriceModel>("usermember/usermemberupprice.json") {
            param("targetSort", targetSort)
            param("sort", sort)
        }.net(this) {
            price = it.data!!.price!!
            binding.m = it
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        binding.v = this
        tv_right_vip.text = "会员升级(升级到${intent.getStringExtra("levelName")})"
    }

    override fun onResume() {
        super.onResume()
        adv_pay_rg.setOnCheckedChangeListener { group, checkedId ->
            payType = when (checkedId) {
                R.id.adv_pay_rg -> {
                    "1"
                }
                else -> {
                    "2"
                }
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
                        startActivity<PaySuccessActivity>("payState" to true)
                    } else {
                        startActivity<PaySuccessActivity>("payState" to false)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip_upgrade)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.adv_buy_confirm -> {
                post<VipUpgradeModel>("usermember/usermemberupandcreateorder.json") {
                    param("targetSort", targetSort)
                    param("payType", payType)
                    param("price", price)
                    param("userId", UserModel.userId)
                }.flatMap {
                    val payNo = it.data.payNo
                    val amount = it.data.payPrice
                    post<PinPayModel>("ping/pingpay.json") {
                        param("orderNo", payNo)
                        param("fromType", "app")
                        param("amount", amount)
                    }
                }.dialog(this) { b ->
                    Pingpp.createPayment(this@VipUpgradeActivity, b.data.data)
                }
            }
        }
    }
}
