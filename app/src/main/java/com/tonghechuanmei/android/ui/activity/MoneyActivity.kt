/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 上午9:41
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ListPopupWindow
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.component.span.DigitsInputFilter
import com.designer.library.utils.darkMode
import com.designer.library.utils.pullMenu
import com.designer.library.utils.setStatusBarPadding
import com.designer.library.widget.PriceUtils
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getComeList
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.databinding.ActivityMoneyBinding
import com.tonghechuanmei.android.model.BaseModel
import com.tonghechuanmei.android.model.UserIncomeModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.model.WithdrawServiceChargeModel
import kotlinx.android.synthetic.main.activity_money.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * 零钱提现
 */
class MoneyActivity : SwipeBackActivity<ActivityMoneyBinding>() {
    @SuppressLint("SetTextI18n")
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        input_alipay.filters = arrayOf(DigitsInputFilter())

        binding.v = this
        refreshLayout.onRefresh {
            getComeList(refreshLayout.page, "2").page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
            }
        }

        empirical_rv.linear().setup {
            addType<UserIncomeModel.Data.Result>(R.layout.money_item_layout)
        }
        input_alipay.textChangeEvents().subscribe {
            if (it.text.toString().isEmpty()) {
                confirm.setBackgroundResource(R.drawable.confirm_gray_price)
            } else {
                confirm.setBackgroundResource(R.drawable.confirm_bright_price)
            }
        }
    }

    private var pullMenu: ListPopupWindow? = null
    var withdrawChargeId: String? = null

    override fun initData() {
        refreshLayout.autoRefresh()

        getUserDetail().flatMap {
            account_tv.text = "支付宝帐号(尾号${it.data.alipayNo})"
            post<WithdrawServiceChargeModel>("userincome/getwithdrawrateandtime.json")
        }.net(this) {
            if (it.msg != "-4" && it.data.isNotEmpty()) {
                val list = it.data.map {
                    it.getStr()
                }
                pullMenu = tv_service_charge.pullMenu(list)
                pullMenu?.setOnItemClickListener { parent, view, position, id ->
                    withdrawChargeId = it.data[position].type
                    tv_service_charge.text = list[position]
                    pullMenu?.dismiss()
                }
                tv_service_charge.text = list[0]
                withdrawChargeId = it.data[0].type
            }
        }

        /*RxUtils.clickView(btnClick/*your view*/)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(getActivity(), "rx click triggered", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                //  println("价格=${intent.getLongExtra("price", 0L) / 100}")
                when {
                    input_alipay.text.toString().isEmpty() -> toast(resources.getString(R.string.money_no_empty))
                    intent.getLongExtra(
                        "price",
                        0L
                    ) < input_alipay.text.toString().toDouble() * 100.toLong() -> toast("零钱不足")
                    !input_alipay.text.toString().isEmpty() && input_alipay.text.toString().toDouble() <= 0 -> toast(
                        "提现金额不能小于或等于0"
                    )
                    else -> post<BaseModel>("userincome/newwithdraw.json") {
                        param(
                            "incomePrice",
                            (input_alipay.text.toString().toDouble() * 100).toLong()
                        )
                        param("userId", UserModel.userId)
                        param("rateType", withdrawChargeId)
                    }.dialog(this) {
                        input_alipay.setText("")
                        toast("提现成功")
                        confirm.setBackgroundResource(R.drawable.confirm_gray_price)
                        refreshLayout.autoRefresh()
                    }
                }
            }
            R.id.all_price -> {
                input_alipay.setText(PriceUtils.getPrice(intent.getLongExtra("price", 0L)))
                input_alipay.setSelection(input_alipay.text.length)
                confirm.setBackgroundResource(R.drawable.confirm_bright_price)
            }
            R.id.tv_edit -> {
                startActivity<AlipayAccountActivity>("tag" to "0")
            }
            R.id.action_back -> finish()
            R.id.tv_service_charge -> pullMenu?.show()
        }
    }
}
