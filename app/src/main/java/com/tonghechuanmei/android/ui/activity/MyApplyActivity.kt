package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.designer.library.component.net.observer.net
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getApplyStateStatistics
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityMyApplyBinding
import com.tonghechuanmei.android.ui.fragment.MyApplyFragment
import com.tonghechuanmei.android.utils.SelectTabUtils
import kotlinx.android.synthetic.main.activity_my_apply.*

/**
 * Author     : shandirong
 * Date       : 2018/11/20 10:46
 * 我的报名
 */
class MyApplyActivity : BaseToolbarActivity<ActivityMyApplyBinding>() {

    private lateinit var mAdapter: ContentPagerAdapter
    private var tabIndicators = ArrayList<String>()
    private var tabFragments = ArrayList<Fragment>()

    override fun initView() {
        title = "我的报名"
    }

    override fun initData() {
        getApplyStateStatistics().net(this) {
            tabIndicators.apply {
                add("进行中\n" + it.data.jxzFlag)
                add("待审核\n" + it.data.dshFlag)
                add("未通过\n" + it.data.wtgFlag)
                add("已通过\n" + it.data.ytgFlag)
            }

            tabFragments.apply {
                add(MyApplyFragment())
                add(MyApplyFragment())
                add(MyApplyFragment())
                add(MyApplyFragment())
            }

            mAdapter = ContentPagerAdapter(supportFragmentManager)
            my_apply_vp.adapter = mAdapter
            my_apply_tb.setupWithViewPager(my_apply_vp)

            SelectTabUtils().setTab(my_apply_tb, this@MyApplyActivity, tabIndicators, 10, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_apply)
    }

    inner class ContentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = tabFragments[position] as MyApplyFragment
            when (position) {
                0 -> {
                    fragment.setState("0")
                }
                1 -> {
                    fragment.setState("1")
                }
                2 -> {
                    fragment.setState("-2")
                }
                3 -> {
                    fragment.setState("2")
                }
            }
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators[position]
        }
    }
}
