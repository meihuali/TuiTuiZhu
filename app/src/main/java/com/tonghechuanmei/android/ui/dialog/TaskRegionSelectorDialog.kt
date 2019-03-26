/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：两津勘吉
 * Date：11/25/18 2:01 AM
 */

package com.tonghechuanmei.android.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseBottomSheetDialogFragment
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.DialogTaskRegionSelectorBinding
import com.tonghechuanmei.android.model.ProvinceModel
import com.tonghechuanmei.android.model.RegionSelectorModel
import kotlinx.android.synthetic.main.dialog_task_region_selector.*

class TaskRegionSelectorDialog : BaseBottomSheetDialogFragment<DialogTaskRegionSelectorBinding>() {

    var regionSelectorModel = RegionSelectorModel()

    override fun initData() {
        post<ProvinceModel>("/area/getcurrentdata.json").net {
            rv_province.baseAdapter.models = it.data
        }
    }

    override fun initView() {
        binding.v = this
        val bottomSheetDialog = dialog as BottomSheetDialog
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(true)

        rv_province.linear().setup {
            addType<ProvinceModel.Data>(R.layout.item_address_selector)
            addClickable(R.id.item)
            onClick {
                val model = getModel<ProvinceModel.Data>()
                post<ProvinceModel>("/area/getcurrentdata.json") {
                    param("parentId", model.id)
                }.net(this@TaskRegionSelectorDialog) {
                    rv_city.baseAdapter.models = it.data
                }
                regionSelectorModel.provinceId = model.id
                regionSelectorModel.cityId = null
                regionSelectorModel.regionId = null
                regionSelectorModel.addressName = model.name

                setChecked(adapterPosition, !model.selected)
                rv_area.baseAdapter.models = null
            }
            singleMode = true
            onCheckedChange { _, position, isChecked, isAllChecked ->
                val model = getModel<ProvinceModel.Data>(position)
                model?.selected = isChecked
            }
        }

        rv_city.linear().setup {
            addType<ProvinceModel.Data>(R.layout.item_address_selector)
            addClickable(R.id.item)
            onClick {
                val model = getModel<ProvinceModel.Data>()

                post<ProvinceModel>("/area/getcurrentdata.json") {
                    param("parentId", model.id)
                }.net(this@TaskRegionSelectorDialog) {
                    rv_area.baseAdapter.models = it.data
                }
                regionSelectorModel.regionId = null
                regionSelectorModel.cityId = model.id
                regionSelectorModel.addressName = model.name
                setChecked(adapterPosition, !model.selected)
            }
            singleMode = true
            onCheckedChange { _, position, isChecked, isAllChecked ->
                val model = getModel<ProvinceModel.Data>(position)
                model?.selected = isChecked
            }
        }

        rv_area.linear().setup {
            addType<ProvinceModel.Data>(R.layout.item_address_selector)
            addClickable(R.id.item)
            onClick {
                val model = getModel<ProvinceModel.Data>()
                regionSelectorModel.regionId = model.id
                regionSelectorModel.addressName = model.name
                setChecked(adapterPosition, !model.selected)
            }
            singleMode = true
            onCheckedChange { _, position, isChecked, isAllChecked ->
                val model = getModel<ProvinceModel.Data>(position)
                model?.selected = isChecked
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_cancel -> {
                dialog.dismiss()
            }
            R.id.tv_confirm -> {
                val flag = arguments?.getString("flag", "")
                regionSelectorModel?.also {
                    RxBus.get().post(flag, regionSelectorModel)
                }
                dialog.dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_task_region_selector, container, false)
    }

}
