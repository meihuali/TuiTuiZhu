/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-17 下午4:39
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.immersive
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getRankingList
import com.tonghechuanmei.android.databinding.ActivityRankingBinding
import com.tonghechuanmei.android.model.RankModel
import kotlinx.android.synthetic.main.activity_ranking.*

/**
 * 排行榜
 */
class RankingActivity : SwipeBackActivity<ActivityRankingBinding>() {
    override fun initView() {
        immersive()
        toolbar.setStatusBarPadding()
        title = "排行榜"
        binding.v = this
        rank_rv.linear().setup {
            addType<RankModel.Data.Result>(R.layout.item_rank_layout)
            onBind {
                val model = getModel<RankModel.Data.Result>()
                model.position = adapterPosition
                false
            }
        }
    }

    override fun initData() {
        refreshLayout.onRefresh {
            getRankingList(refreshLayout.page).page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                    binding.m = it.data.selfCensus
                }
            }
        }
        refreshLayout.autoRefresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.person_return -> {
                finish()
            }
            else -> {
            }
        }
    }
}
