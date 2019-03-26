/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/9/18 9:55 PM
 */

package com.tonghechuanmei.android.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseDialogFragment
import com.designer.library.base.getApp
import com.designer.library.component.databinding.loadCircular
import com.designer.library.utils.setTransparent
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.DialogAdvertisingBinding
import com.tonghechuanmei.android.model.MainDialogModel
import com.tonghechuanmei.android.ui.activity.otherserver.ExChangeActivity
import com.tonghechuanmei.android.ui.activity.otherserver.ExChangeDetailActivity
import com.tonghechuanmei.android.ui.activity.task.TaskDetailActivity
import com.tonghechuanmei.android.ui.activity.task.TaskLobbyActivity
import kotlinx.android.synthetic.main.dialog_advertising.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity


class AdvertisingDialog : BaseDialogFragment<DialogAdvertisingBinding>() {
    var model: MainDialogModel.Data.Ad? = null
    override fun initData() {
        model = arguments?.getParcelable("model")
        model?.let {
            iv_advertising.loadCircular(
                model?.imgUrl,
                getApp().resources.getDrawable(R.color.windowBackground),
                roundedCorners = activity!!.dip(8)
            )
            binding.v = this
        }
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
            R.id.iv_advertising -> {
                when (model?.type) {
                    "1" -> {
                        activity!!.startActivity<TaskLobbyActivity>()
                    }
                    "2" -> {
                        activity!!.startActivity<TaskDetailActivity>("taskId" to model!!.typeCode)
                    }
                    "3" -> {
                        activity!!.startActivity<ExChangeActivity>()
                    }
                    "4" -> {
                        activity!!.startActivity<ExChangeDetailActivity>("id" to model!!.typeCode)
                    }
                }
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_advertising, container, false)
    }

}
