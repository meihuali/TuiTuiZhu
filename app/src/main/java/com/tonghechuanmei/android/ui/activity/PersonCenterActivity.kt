/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-16 下午7:23
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.net
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.google.android.material.appbar.AppBarLayout
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.databinding.ActivityPersonCenterBinding
import com.tonghechuanmei.android.model.UserModel
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_person_center.*
import org.jetbrains.anko.startActivity

/**
 * 个人中心
 */
class PersonCenterActivity : SwipeBackActivity<ActivityPersonCenterBinding>() {
    override fun initView() {
        darkMode(false)
        toolbar.setStatusBarPadding()
        binding.v = this
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        getUserDetail().net(this) {
            if (it.msg == "-4") {
                UserModel.clear()
                val shareApi = UMShareAPI.get(this@PersonCenterActivity)
                shareApi.deleteOauth(this@PersonCenterActivity, SHARE_MEDIA.WEIXIN, null)
                startActivity<LoginActivity>()
                com.designer.library.base.BaseActivity.finishAll("LoginActivity")
                return@net
            }

            binding.m = it.data
            if (it.data.memberLevelLogo != null) {
                person_rv.linear(RecyclerView.HORIZONTAL).setup {
                    addType<String>(R.layout.item_person_center)
                }
                person_rv.baseAdapter.models = it.data.memberLevelLogo!!.split(",")
            }
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            iv.background.alpha =
                    (255 * (1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange)).toInt()
            if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                iv.visibility = View.GONE
                darkMode(true)
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                person_return.setImageResource(R.drawable.ic_action_back)
                tv_title.text = "个人中心"
            } else {
                iv.visibility = View.VISIBLE
                darkMode(false)
                person_return.setImageResource(R.drawable.icon_return_mine)
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                tv_title.text = ""
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_center)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.cl_person -> startActivity<VipCenterActivity>()
            R.id.tv_vip_upgrade -> startActivity<VipCenterActivity>()
            R.id.ll_person_ll1 -> startActivity<MoneyPackageActivity>()
            R.id.ll_person_ll2 -> startActivity<JellyBeanActivity>("title" to tv_bottom2.text.toString().trim())
            R.id.ll_person_ll3 -> startActivity<ContributeActivity>("tag" to "贡献值", "num" to tv3.text.toString())
            R.id.ll_person_ll4 -> startActivity<ContributeActivity>("tag" to "今日贡献值", "num" to tv4.text.toString())
            R.id.person_return -> finish()
            R.id.person_setting -> startActivity<PersonDataActivity>()
            R.id.fatigue_tv -> startActivity<FatigueActivity>()
            R.id.my_task -> startActivity<MyTaskActivity>()                  //我的活动
            R.id.my_exchange -> startActivity<MyChangeActivity>()             //我的兑换
            R.id.auth_tv -> {
                getUserDetail().net(this) {
                    when (it.data.identification) {    //是否实名认证（0：未认证；1：已认证; -1:审核中；-2：审核失败）
                        "0" -> {
                            startActivity<UserAuthActivity>(
                                "tag" to false,
                                "confirm" to "确认提交",
                                "isCheck" to true,
                                "isFail" to false
                            )                //用户认证
                        }
                        "1" -> {
                            startActivity<UserAuthActivity>(
                                "tag" to true,
                                "confirm" to "已认证",
                                "isCheck" to false,
                                "isFail" to false
                            )
                        }
                        "-1" -> {
                            startActivity<UserAuthActivity>(
                                "tag" to true,
                                "confirm" to "正在审核",
                                "isCheck" to false,
                                "isFail" to false
                            )
                        }
                        "-2" -> {
                            startActivity<UserAuthActivity>(
                                "tag" to true,
                                "confirm" to "审核失败（继续审核）",
                                "isCheck" to true,
                                "isFail" to true
                            )
                        }
                    }
                }

            }
            R.id.fans_tv -> startActivity<FansActivity>()                    //我的粉丝
            R.id.adv_tv -> startActivity<MyAdvertisingActivity>()            //我的广告位位
            R.id.person_cl -> startActivity<InviteFriendActivity>()         //邀请有礼
        }
    }
}
