/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 下午3:22
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.component.databinding.inflate
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.NestedGridLayoutManager
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.widget.GridSpacingItemDecoration
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getComeList
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityRedPacketBinding
import com.tonghechuanmei.android.databinding.HeadRedPacketLayoutBinding
import com.tonghechuanmei.android.model.*
import com.tonghechuanmei.android.utils.AlertUtils
import com.tonghechuanmei.android.utils.RedPacketAdapter
import kotlinx.android.synthetic.main.activity_red_packet.*
import org.jetbrains.anko.*


class RedPacketActivity : BaseToolbarActivity<ActivityRedPacketBinding>() {
    lateinit var mAdapter: RedPacketAdapter
    lateinit var model: UserDetailModel.Data
    val list = ArrayList<RedPacketEntity>()

    val moneyList = arrayOf(1, 10, 50, 100, 200, 500)
    var currentPos = -1
    override fun initView() {
        title = "零钱兑换"
        moneyList.forEach {
            val entity = RedPacketEntity()
            entity.money = it
            entity.isClick = it.toLong() < intent.getLongExtra("price", 0L) / 100
            list.add(entity)
        }
        refreshLayout.onRefresh {
            changeData()
        }
    }

    private fun changeData() {
        getUserDetail().net(this) {
            model = it.data
            binding.v = this@RedPacketActivity
        }
        getComeList(refreshLayout.page, "3").page(refreshLayout) {
            if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                showEmpty()
            } else {
                refreshLayout.refreshData(it.data.resultList)
                { it.data.pageInfo.allpage >= refreshLayout.page }
                empirical_tv.setDifferentText(it.data.user.surplusIncome)
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        empirical_rv.linear().setup {
            addType<UserIncomeModel.Data.Result>(R.layout.record_layout)
            empirical_rv.inflate<HeadRedPacketLayoutBinding>(R.layout.head_red_packet_layout)
        }
        choice_rv.layoutManager = NestedGridLayoutManager(this, 3)
        mAdapter = RedPacketAdapter(this)
        choice_rv.adapter = mAdapter
        choice_rv.addItemDecoration(GridSpacingItemDecoration(3, dip(15f), false))
        mAdapter.setNewData(list)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (mAdapter.getItem(position)!!.isClick) {
                currentPos = position
                mAdapter.notifyDataSetChanged()
            }
        }
        empirical_tv.setDifferentText(intent.getLongExtra("price", 0L))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_packet)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                if (model.shanghejiaId.isNullOrEmpty()) {
                    alert {
                        message = "没有绑定上禾佳帐号，不能兑换"
                        cancelButton {

                        }
                        positiveButton("去绑定") {
                            startActivity<BindShjActivity>()
                        }
                    }.show()
                } else {
                    if (currentPos == -1) {
                        toast("请选择兑换金额")
                    } else {
                        post<BaseModel>("userincome/exchange.json") {
                            param("incomePrice", moneyList[currentPos] * 100)
                            param("userId", UserModel.userId)
                        }.net(this) { it ->
                            AlertUtils.exchangeAlert(this@RedPacketActivity) {
                                AlertUtils.dismiss()
                                finish()
                            }
                            refreshLayout.autoRefresh()
                        }
                    }
                }

            }
        }
    }
}
