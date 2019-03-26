/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-12-2 下午11:44
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.net
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.databinding.ActivityShippingAddressBinding
import com.tonghechuanmei.android.ui.adapter.ShippingAddressAdapter
import kotlinx.android.synthetic.main.activity_shipping_address.*
import org.jetbrains.anko.startActivity

/**
 * 地址管理
 */
class ShippingAddressActivity : SwipeBackActivity<ActivityShippingAddressBinding>() {
    var fromProduct: Boolean = false
    private lateinit var mAdapter: ShippingAddressAdapter
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        mAdapter = ShippingAddressAdapter()
        recyclerView.adapter = mAdapter
        binding.v = this
        fromProduct = intent.getBooleanExtra("fromProduct", false)
        backReturn()
    }

    private fun backReturn() {
        if (fromProduct) {       //选择地址，点击返回传回默认的第一个数据
            action_back.setOnClickListener {
                val intent = Intent()
                intent.putExtra("id", "")
                setResult(200, intent)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        OtherServer.getAddressList().net(this) {
            mAdapter.setNewData(it.data)
        }
    }

    @SuppressLint("InflateParams")
    override fun initData() {

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (fromProduct) {
                val intent = Intent()
                intent.putExtra("position", position)
                intent.putExtra("id", mAdapter.data[position].id)
                intent.putExtra("name", mAdapter.data[position].name)
                intent.putExtra(
                    "address",
                    mAdapter.data[position].cityArea.replace(",", "") + mAdapter.data[position].address
                )
                intent.putExtra("phone", mAdapter.data[position].phone)
                setResult(200, intent)
                finish()
            } else {
                startActivity<ShippingAddressDetailsActivity>("data" to mAdapter.data[position])
            }
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            startActivity<ShippingAddressDetailsActivity>("data" to mAdapter.data[position])
        }
        refreshLayout.setOnRefreshListener {
            it.layout.postDelayed({
                onResume()
                refreshLayout.finishRefresh()
            }, 2000)
        }
        mAdapter.emptyView = layoutInflater.inflate(R.layout.layout_empty, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping_address)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.action_back -> {
                finish()
            }
            R.id.tv_manage -> {
                startActivity<ShippingAddressManagerActivity>()
            }
            R.id.add_btn -> {
                startActivity<ShippingAddressDetailsActivity>()
            }
        }
    }
}
