package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.designer.library.base.BaseActivity
import com.designer.library.component.net.observer.net
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.applyEmail
import com.tonghechuanmei.android.api.getTaskStatistics
import com.tonghechuanmei.android.databinding.ActivityTaskAuditBinding
import com.tonghechuanmei.android.ui.fragment.TaskAuditPassFragment
import com.tonghechuanmei.android.ui.fragment.TaskUnAuditFragment
import com.tonghechuanmei.android.utils.AlertUtils
import com.tonghechuanmei.android.utils.SelectTabUtils
import kotlinx.android.synthetic.main.activity_task_audit.*
import org.jetbrains.anko.toast

/**
 * Author     : shandirong
 * Date       : 2018/11/19 10:44
 * 活动审核
 */
class TaskAuditActivity : BaseActivity<ActivityTaskAuditBinding>() {

    private lateinit var mAdapter: ContentPagerAdapter
    private var tabIndicators = ArrayList<String>()
    private var tabFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_audit)
        darkMode()
        activity_task_audit_top_layout.setStatusBarPadding()
        binding.v = this
    }

    override fun initView() {

    }

    override fun initData() {
        getTaskStatistics(intent.getStringExtra("id")).net(this) {
            tabIndicators.clear()
            tabFragments.clear()
            tabIndicators.apply {
                add("未审核\n" + it.data.wshFlag)
                add("已通过\n" + it.data.ytgFlag)
                add("未通过\n" + it.data.btgFlag)
            }
            tabFragments.apply {
                add(TaskUnAuditFragment())
                add(TaskAuditPassFragment())
                add(TaskAuditPassFragment())
            }
            mAdapter = ContentPagerAdapter(supportFragmentManager)
            task_audit_vp.adapter = mAdapter
            task_audit_tb.setupWithViewPager(task_audit_vp)

            SelectTabUtils().setTab(task_audit_tb, this@TaskAuditActivity, tabIndicators, 20, 20)
        }
    }

    fun notifyData() {
        initData()
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.task_audit_top_btn -> {
                AlertUtils.editAlert(this@TaskAuditActivity, "请填写电子邮件") {
                    applyEmail(it, intent.getStringExtra("id")).net(this) {
                        toast("导出成功，请注意查收")
                    }
                }
            }
        }
    }

    inner class ContentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            return if (tabFragments[position] is TaskAuditPassFragment) {
                val fragment = tabFragments[position] as TaskAuditPassFragment
                fragment.setId(intent.getStringExtra("id"))
                if (position == 1) {
                    fragment.setState("2")
                } else {
                    fragment.setState("-2")
                }
                fragment
            } else {
                val fragment = tabFragments[position] as TaskUnAuditFragment
                fragment.setId(intent.getStringExtra("id"))
                fragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators[position]
        }
    }
}
