/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：两津勘吉
 * Date：11/25/18 5:01 AM
 */

package com.tonghechuanmei.android.ui.activity.task

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityTaskTypeListBinding
import com.tonghechuanmei.android.model.RegionSelectorModel
import com.tonghechuanmei.android.model.TaskFilterModel
import com.tonghechuanmei.android.model.TaskListModel
import com.tonghechuanmei.android.model.TaskTypeModel
import com.tonghechuanmei.android.ui.dialog.TaskRegionSelectorDialog
import kotlinx.android.synthetic.main.activity_task_type_list.*
import org.jetbrains.anko.startActivity

class TaskTypeListActivity : BaseToolbarActivity<ActivityTaskTypeListBinding>() {

    private val taskFilterModel = TaskFilterModel()
    private lateinit var currentTaskType: TaskTypeModel.Data

    override fun initView() {

        rv_task_lobby.linear().setup {
            addClickable(R.id.item)
            addType<TaskListModel.Data.Result>(R.layout.item_task_lobby)

            onClick {
                val model = getModel<TaskListModel.Data.Result>()
                startActivity<TaskDetailActivity>("contentId" to model.contentId, "taskId" to model.id)
            }
        }

        binding.v = this
        binding.m = taskFilterModel
    }

    override fun initData() {
        currentTaskType = intent.getSerializableExtra("taskTypeModel") as TaskTypeModel.Data
        taskFilterModel.setTypeFilter(currentTaskType)
        taskFilterModel.setComprehensiveFilter("综合排序")

        title = currentTaskType.name

        refresh.onRefresh {
            post<TaskListModel>("/task/getTaskModelList.json") {
                params(taskFilterModel.get())
                param("isNovice", "0")
                param("page", refresh.page)
            }.page(refresh) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refresh.page }
                }
            }
        }
        refresh.autoRefresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_type_list)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.constraint_comprehensive_order -> {
                taskFilterModel.removeRegoinFilter()
                comprehensiveFilter()
            }
            R.id.constraint_area -> {
                taskFilterModel.removeComprehensiveFilter()
                TaskRegionSelectorDialog().apply {
                    arguments = Bundle().apply { putString("flag", "task_address_selector_type_event") }
                }.show(supportFragmentManager, null)
            }
        }
    }


    private fun comprehensiveFilter() {
        val list = arrayListOf("综合排序", "时间排序", "奖励排序", "距离排序")

        ListPopupWindow(this).apply {
            val adapter =
                ArrayAdapter(this@TaskTypeListActivity, android.R.layout.simple_list_item_1, list)
            setAdapter(adapter)

            anchorView = constraint_comprehensive_order
            setOnItemClickListener { parent, view, position, id ->
                taskFilterModel.removeRegoinFilter()
                taskFilterModel.setComprehensiveFilter(list[position])
                refresh.autoRefresh()
                dismiss()
            }
        }.show()
    }

    @Subscribe(tags = [Tag("task_address_selector_type_event")])
    fun regionFilter(model: RegionSelectorModel) {
        taskFilterModel.removeComprehensiveFilter()
        taskFilterModel.setRegionFilter(model)
        refresh.autoRefresh()
    }
}
