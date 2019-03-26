/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午6:19
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityExChangeBinding
import com.tonghechuanmei.android.model.ProductGetcategoryModel
import com.tonghechuanmei.android.ui.fragment.otherserver.ExChangeProductFragment
import kotlinx.android.synthetic.main.activity_ex_change.*

class ExChangeActivity : SwipeBackActivity<ActivityExChangeBinding>() {
    private var tabIndicators = ArrayList<String>()
    private var tabFragments = ArrayList<Fragment>()
    private var ids = ArrayList<String>()
    private lateinit var mAdapter: ContentPagerAdapter
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        title = "兑换"
        tl_ex_change.setupWithViewPager(vp_product)
        mAdapter = ContentPagerAdapter(supportFragmentManager)
        vp_product.adapter = mAdapter
        binding.v = this
    }

    override fun initData() {
        post<ProductGetcategoryModel>("product/getcategory.json").state(container) {
            for (i in it.data!!.indices) {
                ids.add(it.data[i]!!.id!!)
                tabIndicators.add(it.data[i]!!.name!!)
                tabFragments.add(ExChangeProductFragment())
            }
            mAdapter.notifyDataSetChanged()
        }
    }


    private inner class ContentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun getItem(position: Int): Fragment {
            val fragment = tabFragments[position] as ExChangeProductFragment
            fragment.setId(ids[position])
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabIndicators[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex_change)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.action_back -> {
                finish()
            }
            else -> {
            }
        }
    }
}
