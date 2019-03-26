/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午8:53
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityContributeBinding
import com.tonghechuanmei.android.model.ContributeModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.activity_contribute.*

/**
 * 贡献值
 */
class ContributeActivity : BaseToolbarActivity<ActivityContributeBinding>() {
    override fun initView() {
        title = intent.getStringExtra("tag")
        binding.v = this
        refreshLayout.onRefresh {
            post<ContributeModel>("usercontribute/getContributeList.json") {
                param("userId", UserModel.userId)
                param("page", refreshLayout.page)
            }.page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
                val info = it.data.pageInfo
                info.middleTitle = intent.getStringExtra("tag")
                if (intent.getStringExtra("tag") == "贡献值") {
                    info.bottomTitle = "贡献值记录"

                } else {
                    info.bottomTitle = "今日贡献值记录"
                }
                binding.m = it.data.pageInfo
            }
        }
        tv_price.text = intent.getStringExtra("num")
        tv_content.text = intent.getStringExtra("tag")
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        rv_contribute.linear().setup {
            addType<ContributeModel.Data.Result>(R.layout.item_contribute_layout)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contribute)
    }
}
