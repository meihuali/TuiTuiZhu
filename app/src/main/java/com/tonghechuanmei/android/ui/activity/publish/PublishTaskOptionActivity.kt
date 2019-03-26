/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/23/18 3:36 AM
 */

package com.tonghechuanmei.android.ui.activity.publish

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.component.span.DigitsInputFilter
import com.designer.library.utils.TimeUtils
import com.designer.library.utils.YearMonthDayTimePicker
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPublishTaskOptionBinding
import com.tonghechuanmei.android.model.*
import kotlinx.android.synthetic.main.activity_publish_task_option.*
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

class PublishTaskOptionActivity : BaseToolbarActivity<ActivityPublishTaskOptionBinding>() {

    var taskId: String? = null
    lateinit var model: PublishTaskModel

    override fun initView() {
        title = "发布活动"
        binding.v = this
        swipeEnable = false

        et_task_price.filters = arrayOf(DigitsInputFilter())

    }

    override fun initData() {

        taskId = intent.getStringExtra("taskId")
        val modify = intent.getBooleanExtra("modify", false)

        if (taskId != null) { // 恢复活动数据
            restoreData(modify)
        } else {
            model = intent.getSerializableExtra("model") as PublishTaskModel
            binding.m = model

            if (model.beanNum > 0) {
                et_task_price.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }

    override fun close() {
        discard()
    }

    override fun onBackPressed() {
        discard()
    }

    private fun discard() {
        alert {
            message = "是否丢弃当前编辑内容"
            cancelButton {
                it.dismiss()
            }
            okButton {
                finishTransition()
            }
        }.show()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_next -> {
                when {
                    model.name.isEmpty() -> toast("请填写活动名称")
                    model.startTime.isEmpty() -> toast("请选择活动开始时间")
                    model.endTime.isEmpty() -> toast("请选择活动结束时间")
                    model.signUpNum.isEmpty() || model.signUpNum == "0" -> toast("请输入报名人数")
                    model.userInputAwardMoney.isEmpty() || model.userInputAwardMoney.toDouble() == 0.0 -> toast(
                            "请输入悬赏金额"
                    )
                    model.beanNum > 0 && model.userInputAwardMoney.toDouble() < 2 -> toast(
                            "悬赏金额必须大于等于 2"
                    )
                    else -> startActivity<PublishContentEditActivity>("model" to model)
                }
            }
            R.id.tv_start_time -> {
                taskStartTime()
            }
            R.id.tv_end_time -> {
                taskEndTime()
            }
        }
    }

    private fun taskStartTime() {
        val yearMonthDayTimePicker = YearMonthDayTimePicker()
        yearMonthDayTimePicker.showYearMonthDayAndTimePicker(this)
        yearMonthDayTimePicker.onYearMonthDayTime {
            val millis = TimeUtils.string2Millis(it, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            if (model.endTime.isNotEmpty() && millis / 60 >= model.endTime.toLong() / 60) {
                toast("活动开始时间不能和结束时间等于或者大于")
            } else {
                model.startTime = millis.toString()
            }
        }
    }

    private fun taskEndTime() {
        val yearMonthDayTimePicker = YearMonthDayTimePicker()
        yearMonthDayTimePicker.showYearMonthDayAndTimePicker(this)
        yearMonthDayTimePicker.onYearMonthDayTime {
            val millis = TimeUtils.string2Millis(it, SimpleDateFormat("yyyy-MM-dd HH:mm"))

            if (model.startTime.isNotEmpty() && millis / 60 <= model.startTime.toLong() / 60) {
                toast("活动结束时间不能和开始时间等于或者小于")
            } else {
                model.endTime = millis.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_task_option)
    }

    /**
     * 恢复任务
     * @param modify Boolean 是否是修改任务
     */
    private fun restoreData(modify: Boolean) {
        post<TaskTypeModel>("taskCate/getPublishTaskCateList.json").flatMap {
            getUserDetail()
        }.flatMap {
            model = PublishTaskModel()
            model.setUser(it.data)

            post<TaskDetailModel>("/task/getTaskModeDetail.json") {
                param("id", taskId)
                param("userId", UserModel.userId)
                param("lat", LocationModel.latitude)
                param("lng", LocationModel.longitude)
            }
        }.state(rootView) { it ->
            model.restore(this@PublishTaskOptionActivity, it.data, modify)

            if (model.beanNum > 0) {
                et_task_price.inputType = InputType.TYPE_CLASS_NUMBER
            }
            binding.m = model
        }
    }
}
