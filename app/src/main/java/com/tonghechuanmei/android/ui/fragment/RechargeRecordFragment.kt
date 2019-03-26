/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-15 下午5:21
 */

package com.tonghechuanmei.android.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.FragmentRechargeRecordBinding
import kotlinx.android.synthetic.main.fragment_recharge_record.*

/**
 * 充值记录
 */
class RechargeRecordFragment : BaseFragment<FragmentRechargeRecordBinding>() {
    lateinit var adapter: BaseRecyclerAdapter
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        recharge_record_rv.linear().setup<String>(R.layout.recharge_record_item_layout).models = listOf("1", "2")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recharge_record, container, false)
    }
}
