/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-15 下午5:23
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
import com.tonghechuanmei.android.databinding.FragmentUseRecordBinding
import kotlinx.android.synthetic.main.fragment_use_record.*

/**
 * 使用记录
 */
class UseRecordFragment : BaseFragment<FragmentUseRecordBinding>() {
    lateinit var adapter: BaseRecyclerAdapter
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        use_record_rv.linear().setup<String>(R.layout.use_record_item_layout).models = arrayListOf("1", "2")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_use_record, container, false)
    }


}
