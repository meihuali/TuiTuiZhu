/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 上午11:35
 */

package com.tonghechuanmei.android.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getChooseTaskList
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityChoiceAdvertisingBinding
import com.tonghechuanmei.android.model.ChoiceAdvModel
import kotlinx.android.synthetic.main.activity_choice_advertising.*


/**
 * 选择广告
 */
class ChoiceAdvertisingActivity : BaseToolbarActivity<ActivityChoiceAdvertisingBinding>() {
    override fun initView() {
        title = "选择广告"
        refreshLayout.onRefresh {
            getChooseTaskList(refreshLayout.page).page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        choice_rv.linear().setup {
            addType<ChoiceAdvModel.Data.Result>(R.layout.item_choice_layout)
            addClickable(R.id.choice_cl)
            onClick {
                val m = models!![adapterPosition] as ChoiceAdvModel.Data.Result
                val intent = Intent()
                intent.putExtra("id", m.id)
                intent.putExtra("name", m.name)
                setResult(1, intent)
                finish()
            }
        }
        val agentDividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        agentDividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_divider_vertical)!!)
        choice_rv.addItemDecoration(agentDividerItemDecoration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_advertising)
    }
}
