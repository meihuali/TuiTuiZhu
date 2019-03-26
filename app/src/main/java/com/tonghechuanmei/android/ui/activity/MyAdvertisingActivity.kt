/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午2:17
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.designer.library.component.net.observer.net
import com.luck.picture.lib.rxbus2.Subscribe
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserSpaceCateList
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityMyAdvertisingBinding
import com.tonghechuanmei.android.model.MyAdvertisingModel
import com.tonghechuanmei.android.ui.fragment.MyAdvertisingFragment
import com.tonghechuanmei.android.utils.SelectTabUtils
import kotlinx.android.synthetic.main.activity_my_advertising.*
import org.jetbrains.anko.startActivity

/**
 * 我的广告位
 */
class MyAdvertisingActivity : BaseToolbarActivity<ActivityMyAdvertisingBinding>() {
    private lateinit var mAdapter: ContentPagerAdapter
    private lateinit var tabIndicators: ArrayList<String>
    private lateinit var tabFragments: ArrayList<Fragment>

    override fun initView() {
        title = "我的广告位"
        binding.v = this
        tabIndicators = ArrayList()
        tabFragments = ArrayList()

        mAdapter = ContentPagerAdapter(supportFragmentManager)
        my_advertising_vp.adapter = mAdapter
        my_advertising_tb.setupWithViewPager(my_advertising_vp)
    }

    @Subscribe
    fun refreshTab(event: MyAdvertisingModel) {
        initData()
    }

    override fun initData() {

        getUserSpaceCateList().net(this) {
            it.data.forEach {
                tabIndicators.add(it.stateName + "\n" + it.stateNum)
                val fragment = MyAdvertisingFragment()
                fragment.setState(it.state)
                tabFragments.add(fragment)
            }
            mAdapter.notifyDataSetChanged()
            SelectTabUtils().setTab(my_advertising_tb, this@MyAdvertisingActivity, tabIndicators, 10, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_advertising)
    }

    inner class ContentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = tabFragments[position] as MyAdvertisingFragment
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators[position]
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                startActivity<AdvertisingActivity>()
            }
            else -> {
            }
        }
    }
}
