/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 上午10:16
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.databinding.loadImage
import com.designer.library.component.net.observer.dialog
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.userMemberList
import com.tonghechuanmei.android.databinding.ActivityVipCenterBinding
import com.tonghechuanmei.android.model.UserMemberListModel
import com.tonghechuanmei.android.ui.adapter.VipCenterAdapter
import kotlinx.android.synthetic.main.activity_vip_center1.*
import me.crosswall.lib.coverflow.CoverFlow
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class VipCenterActivity1 : SwipeBackActivity<ActivityVipCenterBinding>() {
    lateinit var mAdapter: VipCenterAdapter
    val list = ArrayList<String>()
    lateinit var model: UserMemberListModel
    override fun initView() {
        darkMode(false)
        toolbar.setStatusBarPadding()
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        vp_vip.offscreenPageLimit = 3
        mAdapter = VipCenterAdapter(this@VipCenterActivity1, list, this)
        vp_vip.adapter = mAdapter
        //就是这一行代码设置的画廊
        CoverFlow.Builder().with(vp_vip).pagerMargin(dip(10).toFloat()).scale(0f).spaceSize(0f).rotationY(0f).build()
        userMemberList().dialog(this) { it ->
            model = it//普通用户 显示普通用户图片V1文字不显示需要显示普通用户
            model.data.resultList.forEach {
                if (it.imgUrl.isNullOrEmpty()) {
                    list.add("http://47.104.247.56:8080/tuituipigapp/upload/2018-11-26/shouyebanner1.gif")
                } else {
                    list.add(it.imgUrl!!)
                }
            }
            mAdapter.notifyDataSetChanged()
            when {
                model.data.user.currentMemberLevelSort in 0..5 -> vp_vip.currentItem = 0
                model.data.user.currentMemberLevelSort in 6..10 -> vp_vip.currentItem = 1
                else -> vp_vip.currentItem = 2
            }
            val level = it.data.user.currentLevelNo.substring(1, it.data.user.currentLevelNo.length)
            text1.text = level   //当前用户等级
            tv_progress.text = (if (it.data.user.allContributeNum.isNullOrEmpty()) "0" else it.data.user.allContributeNum) +
                    "/" + it.data.user.nextContributeValue.toInt().toString()
            try {
                progressBar.progress =
                        ((it.data.user.allContributeNum!!.toDouble() / it.data.user.nextContributeValue.toDouble()) * 100).toInt()
            } catch (e: Exception) {
                progressBar.progress = 0
            }

            if (it.data.user.memberLevelLogo.isNullOrEmpty()) {
                iv_level.setBackgroundResource(R.drawable.pt_vip_bg)
            } else {
                iv_level.loadImage(
                    it.data.user.memberLevelLogo,
                    ContextCompat.getDrawable(this@VipCenterActivity1, R.drawable.pt_vip_bg)
                )
            }
            //   binding.v = this@VipCenterActivity1
        }
        vp_vip.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    tv_confirm.visibility = View.GONE
                } else {
                    tv_confirm.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip_center1)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tv_confirm -> {
                if (model.data.user.currentMemberLevelSort > 10) {
                    toast("请联系客服进行升级")
                } else {
                    startActivity<VipUpgradeActivity>("model" to model)
                }
            }
            R.id.person_return -> finish()
        }
    }
}
