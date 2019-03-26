/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-15 下午5:12
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityBalanceBinding
import com.tonghechuanmei.android.ui.fragment.RechargeRecordFragment
import com.tonghechuanmei.android.ui.fragment.UseRecordFragment
import kotlinx.android.synthetic.main.activity_balance.*
import org.jetbrains.anko.startActivity

/**
 * 活动余额
 */
class BalanceActivity : BaseToolbarActivity<ActivityBalanceBinding>() {
    private lateinit var mAdapter: ContentPagerAdapter
    private lateinit var tabIndicators: ArrayList<String>
    private lateinit var tabFragments: ArrayList<Fragment>
    override fun initView() {
        title = "活动余额"
        tabIndicators = ArrayList()
        tabFragments = ArrayList()
        tabIndicators.apply {
            add("充值记录")
            add("使用记录")
        }

        tabFragments.apply {
            add(RechargeRecordFragment())
            add(UseRecordFragment())
        }
        binding.v = this
    }

    override fun initData() {
        mAdapter = ContentPagerAdapter(supportFragmentManager)
        balance_vp.adapter = mAdapter
        balance_tl.setupWithViewPager(balance_vp)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)
    }

    inner class ContentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            return when {
                tabFragments[position] is RechargeRecordFragment -> {
                    val fragment = tabFragments[position] as RechargeRecordFragment
                    fragment
                }
                else -> tabFragments[position]
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators[position]
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.balance_left_tv -> {

            }
            R.id.balance_right_tv -> {
                startActivity<RechargeActivity>()
            }
        }
    }
}
