/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-16 下午3:59
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.grid
import com.designer.library.component.recycler.setup
import com.designer.library.widget.GridSpacingItemDecoration
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityTaskReviewBinding
import com.tonghechuanmei.android.model.TaskReviewEntity
import kotlinx.android.synthetic.main.activity_task_review.*
import org.jetbrains.anko.dip

/**
 * 活动审核、充值凭证
 */
class TaskReviewActivity : BaseToolbarActivity<ActivityTaskReviewBinding>() {
    lateinit var adapter: BaseRecyclerAdapter
    val list = ArrayList<Any>()
    override fun initView() {
        title = "活动审核"
    }

    override fun initData() {
        list.add(TaskReviewEntity())
        task_review_rv.addItemDecoration(GridSpacingItemDecoration(3, dip(15f), false))
        task_review_rv.grid(3).setup {
            addType<TaskReviewEntity>(R.layout.task_review_add_layout)
            addType<String>(R.layout.task_review_item_layout)
            addClickable(R.id.iv, R.id.delete)
            onClick {
                when (it) {
                    R.id.iv -> {
                        //添加图片
                    }
                    R.id.delete -> {
                        //删除图片
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_review)
    }
}
