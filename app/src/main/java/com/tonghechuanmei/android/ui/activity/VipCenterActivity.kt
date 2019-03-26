/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 上午10:16
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.dialog
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.userMemberList
import com.tonghechuanmei.android.databinding.ActivityVipCenterBinding
import com.tonghechuanmei.android.ui.fragment.CommonVipCenterFragment
import com.tonghechuanmei.android.ui.fragment.VipCenterFragment
import kotlinx.android.synthetic.main.activity_vip_center.*

class VipCenterActivity : SwipeBackActivity<ActivityVipCenterBinding>() {
    private var tabIndicators = ArrayList<String>()
    private var tabFragments = ArrayList<Fragment>()
    private lateinit var mAdapter: ContentPagerAdapter
    override fun initView() {
        darkMode(false)
        toolbar.setStatusBarPadding()
        binding.v = this
    }

    override fun initData() {
        vp_vip.offscreenPageLimit = 4
        mAdapter = ContentPagerAdapter(supportFragmentManager)
        vp_vip.adapter = mAdapter
        tl_vip.setupWithViewPager(vp_vip)
        userMemberList().dialog(this) {
            tabIndicators.add("普通会员")
            val fragment = CommonVipCenterFragment()
            val list = it.data.resultList[0]
            list.currentMemberLevelSort = it.data.user.currentMemberLevelSort
            list.allContributeNum =
                    if (it.data.user.allContributeNum.isNullOrEmpty()) "0" else it.data.user.allContributeNum!!
            list.nextContributeValue = it.data.user.nextContributeValue.toString()
            fragment.setModel(list)
            tabFragments.add(fragment)
            for (i in it.data.resultList.indices) {
                tabIndicators.add("${it.data.resultList[i].name}会员")
                val fragment1 = VipCenterFragment()
                val list1 = it.data.resultList[i]
                list1.currentMemberLevelSort = it.data.user.currentMemberLevelSort
                list1.allContributeNum =
                        if (it.data.user.allContributeNum.isNullOrEmpty()) "0" else it.data.user.allContributeNum!!
                list1.nextContributeValue = it.data.user.nextContributeValue.toString()
                list1.currentPos = 10
                fragment1.setModel(list1)
                tabFragments.add(fragment1)
            }
            mAdapter.notifyDataSetChanged()
            when {
                it.data.user.currentMemberLevelSort == 0 -> vp_vip.currentItem = 0
                it.data.user.currentMemberLevelSort in 1..5 -> vp_vip.currentItem = 1
                it.data.user.currentMemberLevelSort in 6..10 -> vp_vip.currentItem = 2
                else -> vp_vip.currentItem = 3
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip_center)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.person_return -> finish()
        }
    }

    private inner class ContentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            return if (tabFragments[position] is CommonVipCenterFragment) {
                tabFragments[position] as CommonVipCenterFragment
            } else {
                tabFragments[position]
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators[position]
        }
    }
}
