/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/9/18 3:59 PM
 */

package com.tonghechuanmei.android.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseBottomSheetDialogFragment
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.api.pay
import com.tonghechuanmei.android.databinding.FragmentRechargePayDialogBinding
import com.tonghechuanmei.android.model.PinPayModel
import com.tonghechuanmei.android.model.PingPlusPayWayModel
import com.tonghechuanmei.android.model.RechargeMobileOptionModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.PaySuccessActivity
import com.tonghechuanmei.android.utils.handlePinPlusResult
import kotlinx.android.synthetic.main.fragment_recharge_pay_dialog.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class RechargePayDialog : BaseBottomSheetDialogFragment<FragmentRechargePayDialogBinding>() {

    override fun initData() {
        val model =
            arguments?.getParcelable<RechargeMobileOptionModel.Data.RechargeKindModel>("model") ?: return

        getUserDetail().state(container) {
            model.phone = it.data.phone
            model.balance = it.data.surplusIncome
            binding.m = model
        }
    }

    override fun initView() {
        binding.v = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> dismiss()
            R.id.confirm -> confirm()
            R.id.iv_pay_way_alipay -> rb_pay_way_ali.isChecked = true
            R.id.iv_pay_way_wx -> rb_pay_way_wx.isChecked = true
        }
    }


    lateinit var pinPayModel: PinPayModel

    fun confirm() {
        val model = binding.m ?: return

        if (model.payWay.isNullOrEmpty()) {
            activity?.toast("请选择支付方式")
            return
        }

        val payWayObservable = post<PingPlusPayWayModel>("/recharge/addRechargeOrder.json") {
            param("kindId", model.id)
            param("payType", model.payWay)
            param("userId", UserModel.userId)
        }


        if (model.getThirdPayWayVisible()) {
            payWayObservable.flatMap {
                pay(it)
            }.dialog(activity!!) {
                pinPayModel = it
                Pingpp.createPayment(this@RechargePayDialog, it.data.data)
            }
        } else {
            payWayObservable.dialog(activity!!) {
                activity.startActivity<PaySuccessActivity>("payState" to true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recharge_pay_dialog, container, false)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            handlePinPlusResult(data) {
                post<String>("/recharge/payFailByRecharge.json") {
                    param("payNo", pinPayModel.data.orderNo)
                }.dialog(this, false) {
                    startActivity<PaySuccessActivity>()
                }
            }
        }
    }

}
