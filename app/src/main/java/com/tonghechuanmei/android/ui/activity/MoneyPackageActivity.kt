/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 上午10:34
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getAuth
import com.tonghechuanmei.android.api.getComeList
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityMoneyPackageBinding
import com.tonghechuanmei.android.model.UserIncomeModel
import kotlinx.android.synthetic.main.activity_money_package.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.startActivity

/**
 * 我的零钱
 */
class MoneyPackageActivity : BaseToolbarActivity<ActivityMoneyPackageBinding>() {
    lateinit var m: UserIncomeModel.Data.User
    override fun initView() {
        title = "我的零钱"
        refreshLayout.onRefresh {
            getComeList(refreshLayout.page, "0,1").page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
                empirical_tv.setDifferentText(it.data.user.surplusIncome)
                m = it.data.user
                binding.v = this@MoneyPackageActivity
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        money_package_rv.linear().setup {
            addType<UserIncomeModel.Data.Result>(R.layout.money_package_item_layout)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_package)
    }

    override fun onClick(v: View) {
        when (v.id) {
            /*   R.id.money_right_tv -> {
                   getAuth {
                       startActivity<RedPacketActivity>("price" to m.surplusIncome)
                   }
               }*/
            R.id.money_left_tv -> {
                getAuth {
                    if (alipayNo == null) {
                        alert {
                            message = resources.getString(R.string.no_bind)
                            cancelButton {}
                            positiveButton(resources.getString(R.string.add_account)) {
                                startActivity<AlipayAccountActivity>("tag" to "1")
                            }
                        }.show()
                    } else {
                        startActivity<MoneyActivity>("price" to m.surplusIncome)
                    }
                }
            }
        }
    }
}
