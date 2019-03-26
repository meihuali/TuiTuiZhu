/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/23/18 3:33 AM
 */

package com.tonghechuanmei.android.ui.activity.publish

import android.os.Bundle
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPublishTaskTypeBinding
import com.tonghechuanmei.android.model.PublishTaskModel
import com.tonghechuanmei.android.model.TaskTypeModel
import com.tonghechuanmei.android.ui.activity.EmptyActivity
import com.tonghechuanmei.android.utils.getLocation
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_publish_task_type.*
import org.jetbrains.anko.startActivity

class PublishTaskTypeActivity : BaseToolbarActivity<ActivityPublishTaskTypeBinding>() {

    lateinit var model: PublishTaskModel

    override fun initView() {
        title = "活动类型"

        rv_task_type.linear().setup {
            addType<TaskTypeModel.Data>(R.layout.item_publish_task_type)

            onClick(R.id.item) {
                model.taskCategory = getModel<TaskTypeModel.Data>()
                when (model.taskCategory!!.categoryCode) {
                    "simpleModel" -> {
                        startActivity<PublishTaskOptionActivity>("model" to model)
                    }
                    "ActivityModel" -> {
                        startActivity<EmptyActivity>()
                    }
                }
            }
        }
    }

    override fun initData() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted { data: MutableList<String> -> getData() }
                .onDenied { data -> getDataForDefaultLocation() }
                .rationale { context, data, executor -> executor.execute() }
                .start()
    }

    /**
     * 默认位置定位
     */
    private fun getDataForDefaultLocation() {
        getUserDetail().flatMap {
            model = PublishTaskModel()
            model.setUser(it.data)
            post<TaskTypeModel>("taskCate/getPublishTaskCateList.json")
        }.state(state_task_type) {
            if (it.msg == "-4") {
                showEmpty()
            } else {
                rv_task_type.baseAdapter.models = it.data
            }
        }
    }


    /**
     * 获取数据
     */
    private fun getData() {
        getLocation().flatMap {
            model = PublishTaskModel()
            getUserDetail()
        }.flatMap {
            model.setUser(it.data)
            post<TaskTypeModel>("taskCate/getPublishTaskCateList.json")
        }.state(state_task_type) {
            if (it.msg == "-4") {
                showEmpty()
            } else {
                rv_task_type.baseAdapter.models = it.data
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_task_type)
    }
}
