/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 下午6:20
 */

package com.tonghechuanmei.android.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.databinding.loadImage
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.FragmentVipCenterBinding
import com.tonghechuanmei.android.model.UserMemberListModel
import com.tonghechuanmei.android.ui.activity.VipCenterActivity
import com.tonghechuanmei.android.ui.activity.VipUpgradeActivity
import kotlinx.android.synthetic.main.fragment_vip_center.*
import org.jetbrains.anko.startActivity

/**
 * 会员中心Fragment
 */
class VipCenterFragment : BaseFragment<FragmentVipCenterBinding>() {
    lateinit var b: UserMemberListModel.Data.Result
    lateinit var m: UserMemberListModel.Data.Result.TjUserMemberLevel
    var currentPos = 0
    private var rootView: View? = null

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        if (this::b.isInitialized) {
            binding.model = b
            for (i in b.tjUserMemberLevelList.indices) {
                if (b.currentMemberLevelSort == b.tjUserMemberLevelList[i].sort) {
                    currentPos = i
                }
            }
            settingData(currentPos)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun settingData(i: Int) {
        when (i) {
            0 -> {
                setChecked(true, false, false, false, false)
            }
            1 -> {
                setChecked(false, true, false, false, false)
            }
            2 -> {
                setChecked(false, false, true, false, false)
            }
            3 -> {
                setChecked(false, false, false, true, false)
            }
            4 -> {
                setChecked(false, false, false, false, true)
            }
        }
        m = b.tjUserMemberLevelList[i]
        if (m.sort > 10) {
            iv_vip_bg.loadImage(b.tjUserMemberLevelList[0].levelImgUrl)
            iv_up_bg.visibility = View.INVISIBLE
        } else {
            iv_vip_bg.loadImage(b.tjUserMemberLevelList[i].levelImgUrl)
            iv_up_bg.visibility = View.VISIBLE
        }
        if (m.sort > 10) {
            cl_vip.visibility = View.INVISIBLE
        } else {
            cl_vip.visibility = View.VISIBLE
        }
        tv_progress.text = "${b.allContributeNum}/${m.contributeValue}"
        try {
            progressBar.progress =
                    ((b.allContributeNum.toDouble() / m.contributeValue.toDouble()) * 100).toInt()
        } catch (e: Exception) {
            progressBar.progress = 0
        }
        if (b.currentMemberLevelSort < m.sort) { //用户等级小于当前等级才可以升级
            iv_up_bg.setBackgroundResource(R.drawable.upgrade_bg)
        } else {
            iv_up_bg.setBackgroundResource(R.drawable.upgrade_gray_bg)
        }
        binding.m = m

        binding.v = this

    }

    private fun setChecked(b1: Boolean, b2: Boolean, b3: Boolean, b4: Boolean, b5: Boolean) {
        rb1.isChecked = b1
        rb2.isChecked = b2
        rb3.isChecked = b3
        rb4.isChecked = b4
        rb5.isChecked = b5
    }

    override fun initData() {
        rg_vip.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> {
                    settingData(0)
                }
                R.id.rb2 -> {
                    settingData(1)
                }
                R.id.rb3 -> {
                    settingData(2)
                }
                R.id.rb4 -> {
                    settingData(3)
                }
                R.id.rb5 -> {
                    settingData(4)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_vip_center, container, false)
        return rootView
    }

    fun setModel(model: UserMemberListModel.Data.Result) {
        b = model
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.iv_up_bg -> {
                if (b.currentMemberLevelSort < m.sort) { //用户等级小于当前等级才可以升级
                    (activity as VipCenterActivity).startActivity<VipUpgradeActivity>(
                        "sort" to b.currentMemberLevelSort,
                        "levelName" to m.levelName,
                        "price" to m.price,
                        "targetSort" to m.sort
                    )
                }
            }
        }
    }
}
