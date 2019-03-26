/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-12-25 下午3:02
 */

package com.tonghechuanmei.android.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.designer.library.base.BaseBottomSheetDialogFragment
import com.designer.library.base.getApp
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.designer.library.utils.KeyBoardUtils
import com.designer.library.utils.immersive
import com.designer.library.utils.showKeyboard
import com.hwangjr.rxbus.RxBus
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.FragmentConversionDialogBinding
import com.tonghechuanmei.android.model.EmptyModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.fragment_conversion_dialog.*
import org.jetbrains.anko.toast

class ConversionDialog : BaseBottomSheetDialogFragment<FragmentConversionDialogBinding>() {
    override fun initData() {
        et_conversion.textChangeEvents().subscribe {
            if (it.text.toString().isEmpty()) {
                tv_confirm.setBackgroundResource(R.drawable.bg_conversion)
                tv_confirm.setTextColor(ContextCompat.getColor(getApp(), R.color.textGray))
            } else {
                tv_confirm.setBackgroundResource(R.drawable.bg_conversion_yellow)
                tv_confirm.setTextColor(ContextCompat.getColor(getApp(), R.color.white))
            }
        }
    }

    override fun initView() {
        binding.v = this
        KeyBoardUtils.setViewPaddingToFloatKeyboard(dialog.window, ll_conversion)
        et_conversion.post { et_conversion.showKeyboard() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_conversion_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.immersive()
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tv_confirm -> {
                val code = et_conversion.text.toString().trim()
                when {
                    code.isEmpty() -> getApp().toast("兑换码不能为空")
                    else -> {
                        post<EmptyModel>("/card/exchange.json") {
                            param("cardNo", code)
                            param("userId", UserModel.userId)
                        }.dialog(activity!!) {
                            if (it.msg == "-4") {
                                activity.toast("该兑换码不存在")
                            } else {
                                RxBus.get().post("refresh_jelly_bean_event", "兑换")
                                activity.toast("兑换成功")
                                dismiss()
                            }
                        }
                    }
                }
            }
        }
    }
}