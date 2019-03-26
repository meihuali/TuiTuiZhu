/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-24 下午9:53
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.databinding.ActivityJellyBeanBinding
import com.tonghechuanmei.android.model.JellyBeanModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.dialog.ConversionDialog
import kotlinx.android.synthetic.main.activity_jelly_bean.*
import org.jetbrains.anko.startActivity

/**
 * 糖豆记录
 */
class JellyBeanActivity : SwipeBackActivity<ActivityJellyBeanBinding>() {
    @SuppressLint("SetTextI18n")
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        rv_jelly.linear().setup {
            addType<JellyBeanModel.Data.Bean>(R.layout.item_jelly_layout)
        }
        binding.v = this
        initRefresh()

        tv_title.text = intent.getStringExtra("title")
        content.text = intent.getStringExtra("title")
        tv1.text = intent.getStringExtra("title") + "记录"

        getUserDetail().net(this) {
            tv_jelly_bean.text = it.data.beanNum.toLong().toString()
        }
    }

    private fun initRefresh() {
        refreshLayout.onRefresh {
            post<JellyBeanModel>("user/getUserBeanList.json") {
                param("userId", UserModel.userId)
                param("page", refreshLayout.page)
            }.page(refreshLayout) {
                if (it.msg == "-4" || it.data.beanList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.beanList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getUserDetail().net(this) {
            tv_jelly_bean.text = it.data.beanNum.toLong().toString()
        }
    }

    @Subscribe(tags = [Tag("refresh_jelly_bean_event")])
    fun subscribe(event: String) {
        initData()
    }

    override fun initData() {
        refreshLayout.autoRefresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jelly_bean)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.action_back -> {
                finish()
            }
            R.id.tv_recharge -> {        //兑换
                ConversionDialog().show(supportFragmentManager, null)
            }
            R.id.tv_edit -> {            //赠送
                startActivity<SendJellyBeanPhoneActivity>()
            }
        }
    }
}
