/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/9/18 9:54 PM
 */

package com.tonghechuanmei.android.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseDialogFragment
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.designer.library.utils.setTransparent
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.DialogNewbieGiftBinding
import com.tonghechuanmei.android.model.MainDialogModel
import com.tonghechuanmei.android.model.UserModel


class NewbieGiftDialog : BaseDialogFragment<DialogNewbieGiftBinding>() {

    var model: MainDialogModel.Data.Welfare? = null

    override fun initData() {
        model = arguments?.getParcelable("model")
    }

    override fun initView() {
        dialog.setTransparent()
        isCancelable = false
        binding.v = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> {
                dismiss()
            }
            R.id.btn_confirm -> {
                post<String>("/welfare/userwelfareEdit.json") {
                    param("id", model?.id ?: "")
                    param("userId", UserModel.userId)
                }.net(activity)

                val newbieGiftTangdouDialog = NewbieGiftTangdouDialog()
                val bundle = Bundle()
                bundle.putParcelable("model", model)
                newbieGiftTangdouDialog.arguments = bundle
                newbieGiftTangdouDialog.show(fragmentManager, null)
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_newbie_gift, container, false)
    }
}
