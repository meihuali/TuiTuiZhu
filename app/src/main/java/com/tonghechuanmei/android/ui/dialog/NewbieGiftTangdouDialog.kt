/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/10/18 1:25 AM
 */

package com.tonghechuanmei.android.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseDialogFragment
import com.designer.library.utils.setTransparent
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.DialogNewbieGiftTangdouBinding
import com.tonghechuanmei.android.model.MainDialogModel


class NewbieGiftTangdouDialog : BaseDialogFragment<DialogNewbieGiftTangdouBinding>() {
    var model: MainDialogModel.Data.Welfare? = null

    override fun initData() {
        model = arguments?.getParcelable("model")
        binding.m = model
    }

    override fun initView() {
        dialog.setTransparent()
        isCancelable = false
        binding.v = this
    }

    override fun onClick(v: View) {
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_newbie_gift_tangdou, container, false)
    }

}
