/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/29/18 4:08 PM
 */

package com.tonghechuanmei.android.ui.activity.publish

import android.os.Bundle
import android.view.View
import com.designer.library.base.SwipeBackActivity
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityTaskRequireEditBinding
import com.tonghechuanmei.android.model.PublishTaskModel
import kotlinx.android.synthetic.main.activity_task_require_edit.*
import org.jetbrains.anko.toast

class TaskRequireEditActivity : SwipeBackActivity<ActivityTaskRequireEditBinding>() {
    var model: PublishTaskModel? = null
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
    }

    override fun initData() {
        model = intent.getSerializableExtra("model") as PublishTaskModel

        model?.let {
            binding.m = it
            binding.v = this
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_save -> {
                if (model!!.requirements.isEmpty()) {
                    toast("请输入您对活动的转发要求")
                    return
                }
                RxBus.get().post(model)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_require_edit)
    }
}
