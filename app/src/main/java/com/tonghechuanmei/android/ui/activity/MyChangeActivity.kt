/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 下午2:46
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityMyChangeBinding
import com.tonghechuanmei.android.model.OrderListModel
import kotlinx.android.synthetic.main.activity_my_change.*

class MyChangeActivity : BaseToolbarActivity<ActivityMyChangeBinding>() {
    override fun initView() {
        title = "我的兑换"
        refreshLayout.onRefresh {
            OtherServer.getOrderList("2,3").page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage > refreshLayout.page }
                }
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        recyclerView.linear().setup {
            addType<OrderListModel.Data.Result>(R.layout.item_my_change)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_change)
    }
}
