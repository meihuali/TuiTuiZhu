package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.state
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getTaskStatistics
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityMyTaskBinding
import kotlinx.android.synthetic.main.activity_my_task.*
import org.jetbrains.anko.startActivity

/**
 * Author     : shandirong
 * Date       : 2018/11/17 16:03
 */
class MyTaskActivity : BaseToolbarActivity<ActivityMyTaskBinding>() {
    override fun initView() {
        binding.click = this
        title = "我的活动"
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_task)
        getTaskStatistics().state(my_task_layout) {
            binding.bean = it.data
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.my_task_iv1 -> {
                // 我的发布
                startActivity<TaskPublishActivity>()
            }
            R.id.my_task_iv2 -> {
                // 我的活动
                startActivity<MyApplyActivity>()
            }
        }
    }
}
