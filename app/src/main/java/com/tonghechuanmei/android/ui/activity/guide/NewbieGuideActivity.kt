/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/11/18 7:32 PM
 */

package com.tonghechuanmei.android.ui.activity.guide

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.designer.library.component.BaseFragmentPagerAdapter
import com.designer.library.component.net.observer.net
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getNewbieGuideBanner
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityNewbieGuideBinding
import com.tonghechuanmei.android.model.NewbieGuideBannerModel
import com.tonghechuanmei.android.ui.fragment.NewbieGuideFragment
import kotlinx.android.synthetic.main.activity_newbie_guide.*

class NewbieGuideActivity : BaseToolbarActivity<ActivityNewbieGuideBinding>() {

    override fun initView() {
        title = "新手指南"

        rv_banner.linear(RecyclerView.HORIZONTAL).setup {
            addType<NewbieGuideBannerModel.Data.Result>(R.layout.item_newbie_guide_banner)
            onClick(R.id.item) {
                val model = getModel<NewbieGuideBannerModel.Data.Result>()
                if (model.type == "2") {
                    JzvdStd.startFullscreen(
                        this@NewbieGuideActivity,
                        JzvdStd::class.java,
                        model.url,
                        model.title
                    )
                }
            }
        }
    }

    override fun initData() {
        getNewbieGuideBanner().net(this) {
            rv_banner.baseAdapter.models = it.data.resultList
        }

        val newbieGuideTaskFragment =
            NewbieGuideFragment().apply { arguments = Bundle().apply { putString("flag", "新手任务") } }
        val newbieGuideQuestionFragment =
            NewbieGuideFragment().apply { arguments = Bundle().apply { putString("flag", "新手问答") } }

        vp_newbie_guide.adapter =
                BaseFragmentPagerAdapter(
                    supportFragmentManager,
                    arrayOf(newbieGuideTaskFragment, newbieGuideQuestionFragment)
                )

        rg_tab.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_newbie_task -> {
                    vp_newbie_guide.setCurrentItem(0, false)
                }
                R.id.rb_question -> {
                    vp_newbie_guide.setCurrentItem(1, false)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        RxBus.get().post("refresh_newbie_guide_event", "刷新手任务")
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newbie_guide)
    }
}
