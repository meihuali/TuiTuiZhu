/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-16 上午9:53
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityFatigueBinding
import kotlinx.android.synthetic.main.activity_fatigue.*

/**
 * 疲劳值
 */
class FatigueActivity : BaseToolbarActivity<ActivityFatigueBinding>() {
    lateinit var adapter: BaseRecyclerAdapter
    override fun initView() {
        title = "疲劳值"
    }

    override fun initData() {
        fatigue_rv.linear().setup<String>(R.layout.fatigue_item_layout).models = arrayListOf("1", "2")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fatigue)
    }
}
