/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 下午1:27
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.state
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityShippingAddressManagerBinding
import com.tonghechuanmei.android.ui.adapter.ShippingAddressManagerAdapter
import com.tonghechuanmei.android.utils.AlertUtils
import kotlinx.android.synthetic.main.activity_shipping_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ShippingAddressManagerActivity : BaseToolbarActivity<ActivityShippingAddressManagerBinding>() {
    private lateinit var mAdapter: ShippingAddressManagerAdapter
    override fun initView() {
        title = "管理地址"
    }

    override fun initData() {
        mAdapter = ShippingAddressManagerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.modify -> {
                    startActivity<ShippingAddressDetailsActivity>("data" to mAdapter.data[position])
                }
                R.id.delete -> {
                    AlertUtils.normalAlert(this@ShippingAddressManagerActivity, "确认删除该地址？") {
                        OtherServer.delAddress(mAdapter.data[position].id).dialog(this) {
                            toast("删除成功")
                            onResume()
                            RxBus.get().post("refresh_address_event", "刷新地址")
                        }
                        AlertUtils.dismiss()
                    }
                }
                R.id.check_box -> {
                    OtherServer.setDefultAddress(mAdapter.data[position].id).net(this) {
                        onResume()
                    }
                }
                R.id.tv_edit -> {
                    startActivity<ShippingAddressDetailsActivity>("data" to mAdapter.data[position])
                }
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (intent.getBooleanExtra("fromProduct", false)) {
                val intent = Intent()
                intent.putExtra("position", position)
                intent.putExtra("id", mAdapter.data[position].id)
                intent.putExtra("name", mAdapter.data[position].name)
                intent.putExtra("address", mAdapter.data[position].address)
                intent.putExtra("phone", mAdapter.data[position].phone)
                setResult(200, intent)
                finish()
            }
        }
        refreshLayout.setOnRefreshListener {
            it.layout.postDelayed({
                onResume()
                refreshLayout.finishRefresh()
            }, 2000)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping_address_manager)
    }

    override fun onResume() {
        super.onResume()
        OtherServer.getAddressList().state(rootView) {
            mAdapter.setNewData(it.data)
        }
        mAdapter.emptyView = layoutInflater.inflate(R.layout.layout_empty, null)
    }
}
