/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.hwangjr.rxbus.RxBus

abstract class BaseDialogFragment<B : ViewDataBinding> : DialogFragment(), OnClickListener {

    lateinit var binding: B

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = DataBindingUtil.bind(view!!)!!
        if (!RxBus.get().hasRegistered(this)) {
            RxBus.get().register(this)
        }
        try {
            initView()
            initData()
        } catch (e: Exception) {
            Log.e("日志", "初始化失败")
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {

    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

    abstract fun initData()

    abstract fun initView()
}
