/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午2:22
 */

package com.tonghechuanmei.android.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.hwangjr.rxbus.RxBus
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.advertisingPayAgain
import com.tonghechuanmei.android.databinding.FragmentMyAdvertisingBinding
import com.tonghechuanmei.android.model.MyAdvertisingModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.AdvertisingBuyEditActivity
import com.tonghechuanmei.android.ui.activity.MyAdvertisingActivity
import kotlinx.android.synthetic.main.fragment_my_advertising.*
import org.jetbrains.anko.startActivity

/**
 * 我的活动位
 */
class MyAdvertisingFragment : BaseFragment<FragmentMyAdvertisingBinding>() {
    var mState = ""
    override fun initView(savedInstanceState: Bundle?) {
        refreshLayout.onRefresh {
            post<MyAdvertisingModel>("adspace/getUserSpaceList.json") {
                param("userId", UserModel.userId)
                param("state", mState)
                param("page", refreshLayout.page)
            }.page(refreshLayout) {
                RxBus.get().post(it)
                if (it.msg == "-4" || it.data.mySAdInfoList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.mySAdInfoList) {
                        it.data.pager.pageTotal >= refreshLayout.page
                    }
                }
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        advertising_rv.linear().setup {
            addClickable(R.id.advertising_tv, R.id.advertising_pay_tv)
            addType<MyAdvertisingModel.Data.MySAdInfo>(R.layout.item_my_advertising_layout)
            onClick {
                val model = models!![adapterPosition] as MyAdvertisingModel.Data.MySAdInfo
                when (it) {
                    R.id.advertising_tv -> {
                        //编辑
                        (activity as MyAdvertisingActivity).startActivity<AdvertisingBuyEditActivity>(
                            "spaceTaskId" to model.id,
                            "taskId" to model.taskId
                        )
                    }
                    R.id.advertising_pay_tv -> {
                        //再次支付
                        advertisingPayAgain(model.id).dialog(activity as MyAdvertisingActivity) { b ->
                            Pingpp.createPayment(this@MyAdvertisingFragment, b.data.data)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_advertising, container, false)
    }

    fun setState(state: String) {
        mState = state
    }

}
