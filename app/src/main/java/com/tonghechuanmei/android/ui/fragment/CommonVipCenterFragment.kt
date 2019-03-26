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
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.FragmentCommonVipCenterBinding
import com.tonghechuanmei.android.model.UserMemberListModel
import kotlinx.android.synthetic.main.fragment_common_vip_center.*

/**
 * 普通会员中心Fragment
 */
class CommonVipCenterFragment : BaseFragment<FragmentCommonVipCenterBinding>() {
    lateinit var b: UserMemberListModel.Data.Result
    lateinit var m: UserMemberListModel.Data.Result.TjUserMemberLevel
    private var rootView: View? = null

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        if (this::b.isInitialized) {
            binding.model = b
            setChecked(true, false, false, false, false)
            m = b.tjUserMemberLevelList[0]
            tv_progress.text = "${b.allContributeNum}/${m.contributeValue}"
            try {
                progressBar.progress =
                        ((b.allContributeNum.toDouble() / m.contributeValue.toDouble()) * 100).toInt()
            } catch (e: Exception) {
                progressBar.progress = 0
            }
            binding.m = m
            binding.v = this
        }
    }

    private fun setChecked(b1: Boolean, b2: Boolean, b3: Boolean, b4: Boolean, b5: Boolean) {
        rb1.isChecked = b1
        rb2.isChecked = b2
        rb3.isChecked = b3
        rb4.isChecked = b4
        rb5.isChecked = b5
    }

    override fun initData() {
        cl_vip.visibility = View.INVISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_common_vip_center, container, false)
        return rootView
    }

    fun setModel(model: UserMemberListModel.Data.Result) {
        b = model
    }
}
