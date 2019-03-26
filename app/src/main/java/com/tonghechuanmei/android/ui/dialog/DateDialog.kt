/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/28/18 10:55 PM
 */

package com.tonghechuanmei.android.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.designer.library.base.BaseBottomSheetDialogFragment
import com.designer.library.component.picker.DatePickerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.DialogDateBinding
import kotlinx.android.synthetic.main.dialog_date.*
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DateDialog : BaseBottomSheetDialogFragment<DialogDateBinding>() {

    override fun initData() {
    }

    override fun initView() {
        binding.v = this

        val doubleDigitFormat = DecimalFormat("00")
        sp_year.setAdapter(DatePickerAdapter(2018, 2050))
        sp_year.isLoopEnable = true
        sp_month.setAdapter(DatePickerAdapter(1, 12, doubleDigitFormat))
        sp_day.setAdapter(DatePickerAdapter(1, 31, doubleDigitFormat))
        sp_hour.setAdapter(DatePickerAdapter(0, 23, doubleDigitFormat))
        sp_minute.setAdapter(DatePickerAdapter(0, 59, doubleDigitFormat))

        val dialog: BottomSheetDialog = dialog as BottomSheetDialog
        dialog.setCancelable(false)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_confirm -> {
            }
            R.id.tv_cancel -> {
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_date, container, false)
    }


}
