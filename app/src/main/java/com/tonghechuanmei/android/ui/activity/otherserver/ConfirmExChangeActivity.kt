/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-29 下午4:33
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityConfirmExChangeBinding
import com.tonghechuanmei.android.model.*
import com.tonghechuanmei.android.ui.activity.LoginActivity
import com.tonghechuanmei.android.ui.activity.PaySuccessActivity
import com.tonghechuanmei.android.utils.AlertUtils
import com.tonghechuanmei.android.utils.handlePinPlusResult
import kotlinx.android.synthetic.main.activity_confirm_ex_change.*
import kotlinx.android.synthetic.main.add_shop_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class ConfirmExChangeActivity : BaseToolbarActivity<ActivityConfirmExChangeBinding>() {
    var payType = "1"
    var userAddressId = ""
    var orderNo = ""
    lateinit var m: ProductSearchDetailModel.Data
    override fun initView() {
        title = "确认兑换"
        m = intent.getSerializableExtra("m") as ProductSearchDetailModel.Data
        m.number = 1
        binding.v = this
    }

    override fun initData() {
        addAddress()
        getUserDetail().net(this) {
            if (it.msg != "-4") {
                val model = it.data
                model.detailBean = m.beanPrice
                model.detailPrice = m.price
                binding.user = model

                //把用户信息的糖豆和零钱传递给详情Model里面
                m.beanNum = model.beanNum
                m.surplusIncome = model.surplusIncome
                binding.m = m
            } else {
                UserModel.clear()
                startActivity<LoginActivity>()
                com.designer.library.base.BaseActivity.finishAll("LoginActivity")
            }
        }

        adv_pay_rg.setOnCheckedChangeListener { group, checkedId ->
            payType = if (checkedId == R.id.adv_pay_rb1) {
                "1"
            } else {
                "2"
            }
        }
    }

    @Subscribe(tags = [Tag("refresh_address_event")])
    fun subscribe(event: String) {
        addAddress()
    }

    private fun addAddress() {
        OtherServer.getAddressList().net(this) {
            if (it.msg != "-4") {
                binding.show = true
                binding.address = it.data[0]
                userAddressId = it.data[0].id
            } else {
                userAddressId = ""
                binding.show = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_ex_change)
    }

    @SuppressLint("CheckResult")
    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tv_confirm -> {
                if (this::m.isInitialized) {
                    if (userAddressId.isEmpty()) {
                        toast("请选择地址")
                    } else {
                        post<ConfirmOrderModel>("neworder/createorder.json") {
                            param(
                                "freightPrice",
                                if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong()
                            )        //运费
                            if (UserModel.memberLevelId == UserDetailModel.GOLD) {
                                param(
                                    "orderPrice",
                                    m.price * add_shop_number.text.toString().toInt() + if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong()
                                )
                                if (binding.user!!.surplusIncome >= m.price * add_shop_number.text.toString().toInt() + if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong()) {
                                    param(
                                        "payIncome",
                                        m.price * add_shop_number.text.toString().toInt() + if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong()
                                    )       //零钱支付价格(零钱不需要加运费)
                                    param("payPrice", 0)
                                } else {
                                    param("payIncome", binding.user!!.surplusIncome)       //零钱支付价格
                                    param(
                                        "payPrice",
                                        m.price * add_shop_number.text.toString().toInt() + if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong() - binding.user!!.surplusIncome
                                    )
                                }
                            } else {                    //糖豆（需要加运费）
                                param(
                                    "beanPrice",
                                    m.beanPrice * add_shop_number.text.toString().toInt() + if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong() / 100
                                )
                                param(
                                    "orderPrice",
                                    m.beanPrice * add_shop_number.text.toString().toInt() + if (m.storeFreightId.isNullOrEmpty()) 0L else m.storeFreightId!!.toLong() / 100
                                )
                            }
                            param("payType", payType)
                            param("productId", m.id)
                            param("productNum", add_shop_number.text.toString().toInt())
                            param("remark", tv_remark.text.toString())
                            param("userAddressId", userAddressId)
                            param("userId", UserModel.userId)
                        }.net(this) {
                            val payNo = it.data.order.payNo
                            val amount = it.data.order.payPrice
                            if (it.data.order.payPrice == 0L) {               //不需要调支付
                                startActivity<PaySuccessActivity>("payState" to true, "tag" to "AdvertisingBuyActivity")
                            } else {
                                post<PinPayModel>("ping/productpingpay.json") {
                                    param("orderNo", payNo)
                                    param("fromType", "app")
                                    param("amount", amount)
                                }.net(this@ConfirmExChangeActivity) { b ->
                                    if (b.msg != "-4") {
                                        orderNo = b.data.orderNo
                                        Pingpp.createPayment(this@ConfirmExChangeActivity, b.data.data)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            R.id.iv_pay_way_alipay -> {          //支付宝
                adv_pay_rb1.isChecked = true
                adv_pay_rb2.isChecked = false
                payType = "1"
            }
            R.id.iv_pay_way_wx -> {              //微信
                adv_pay_rb1.isChecked = false
                adv_pay_rb2.isChecked = true
                payType = "2"
            }
            R.id.iv_left -> {
                if (m.number > 1) {
                    m.number--
                    binding.m = m
                }
            }
            R.id.iv_right -> {
                m.number++
                binding.m = m
            }
            R.id.add_shop_number -> {
                val dialog = AlertUtils.getDialog(this, R.layout.add_shop_layout)
                dialog.add_shop_number_et.setText(add_shop_number.text)
                dialog.cancle.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.confirm.setOnClickListener {
                    if (dialog.add_shop_number_et.text.toString().trim().toInt() >= 1) {
                        m.number = dialog.add_shop_number_et.text.toString().trim().toInt()
                        binding.m = m
                    }
                    dialog.dismiss()
                }
            }
            R.id.cl_confirm -> {
                if (userAddressId.isEmpty()) {   //跳转到添加地址
                    startActivity<ShippingAddressDetailsActivity>()
                } else {                       //跳转到添加地址
                    startActivityForResult<ShippingAddressActivity>(1, "fromProduct" to true)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                1 -> {
                    userAddressId = data.getStringExtra("id")
                    if (!userAddressId.isEmpty()) {
                        tv_name.text = data.getStringExtra("name")
                        tv_phone.text = data.getStringExtra("phone")
                        if (data.getIntExtra("position", -1) == 0) {
                            tv_address.setDefaultText(data.getStringExtra("address"))
                        } else {
                            tv_address.text = data.getStringExtra("address")
                        }
                    }
                }


                Pingpp.REQUEST_CODE_PAYMENT -> {
                    handlePinPlusResult(data) {
                        post<BaseModel>("neworder/payerrorback.json") {
                            param("payNo", orderNo)
                        }.dialog(this, false) {
                            startActivity<PaySuccessActivity>("payState" to false)
                        }
                    }
                    /*  if (resultCode == Activity.RESULT_OK) {
                          if (data != null) {
                              val result = data.extras!!.getString("pay_result")
                              if (result == "success") {
                                  startActivity<PaySuccessActivity>("payState" to true, "tag" to "AdvertisingBuyActivity")
                              } else {
                                  handlePinPlusResult(data){

                                  }

                              }
                              *//* 处理返回值
                     * "success" - 支付
                     * 成功
                     * "fail"    - 支付失败
                     * "cancel"  - 取消支付
                     * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                     * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
                     *//*

                        }
                    }*/
                }
            }
        }
    }
}
