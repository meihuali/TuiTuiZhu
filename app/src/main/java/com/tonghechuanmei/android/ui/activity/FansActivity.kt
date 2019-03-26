/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 下午8:59
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getFansList
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityFansBinding
import com.tonghechuanmei.android.model.FansListModel
import kotlinx.android.synthetic.main.activity_fans.*

/**
 * 粉丝
 */
class FansActivity : BaseToolbarActivity<ActivityFansBinding>() {
    lateinit var adapter: BaseRecyclerAdapter
    override fun initView() {
        title = "粉丝"
        fans_rv.linear().setup {
            addType<FansListModel.Data.Result>(R.layout.item_fans_layout)
        }
    }

    override fun initData() {
        refreshLayout.onRefresh {
            getFansList(refreshLayout.page).page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    binding.s = "已有${it.data.pageInfo.allcount}位粉丝"
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
                binding.s = "已有${it.data.pageInfo.allcount}位粉丝"
            }
        }
        refreshLayout.autoRefresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fans)
    }
}
