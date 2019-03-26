/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 上午9:34
 */

package com.tonghechuanmei.android.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.model.AreaEntity
import com.tonghechuanmei.android.ui.adapter.CityAdapter

class CityWindow(private val context: Context) : PopupWindow.OnDismissListener {

    private var popupWindow: PopupWindow? = null
    private var mProvinceList: List<AreaEntity.DataBean>? = null
    private var mCityList: List<AreaEntity.DataBean>? = null
    private var mDistrictList: List<AreaEntity.DataBean>? = null
    private var mClick: OnClick? = null

    private var mSpace: FrameLayout? = null
    private var provinceLayout: RecyclerView? = null
    private var cityLayout: RecyclerView? = null
    private var districtLayout: RecyclerView? = null
    private var provinceAdapter: CityAdapter? = null
    private var cityAdapter: CityAdapter? = null
    private var districtAdapter: CityAdapter? = null

    fun setClick(click: OnClick) {
        mClick = click
    }

    fun setProvinceList(provinceList: List<AreaEntity.DataBean>) {
        mProvinceList = provinceList
    }

    fun setCityList(cityList: List<AreaEntity.DataBean>) {
        mCityList = cityList
        cityAdapter!!.setNewData(cityList)
        cityLayout!!.adapter = cityAdapter
        cityAdapter!!.setOnItemClickListener { adapter, view, position ->
            cityAdapter!!.setPosition(position)
            mClick!!.onCityListener(mCityList!![position].id)
        }
    }

    fun setDistrictList(districtList: List<AreaEntity.DataBean>) {
        mDistrictList = districtList
        districtAdapter!!.setNewData(districtList)
        districtLayout!!.adapter = districtAdapter
        districtAdapter!!.setOnItemClickListener { adapter, view, position ->
            districtAdapter!!.setPosition(position)
            onDismiss()
            mClick!!.onDistrictListener(mProvinceList!![provinceAdapter!!.getPosition()].name + "," + mCityList!![cityAdapter!!.getPosition()].name + "," + mDistrictList!![districtAdapter!!.getPosition()].name)
        }
    }

    fun showPopupWindow(anchor: View) {
        val contentView = LayoutInflater.from(context).inflate(R.layout.view_city, null)
        mSpace = contentView.findViewById(R.id.space)
        provinceLayout = contentView.findViewById(R.id.tablayout)
        cityLayout = contentView.findViewById(R.id.tablayout1)
        districtLayout = contentView.findViewById(R.id.tablayout2)

        provinceLayout!!.layoutManager = LinearLayoutManager(context)
        cityLayout!!.layoutManager = LinearLayoutManager(context)
        districtLayout!!.layoutManager = LinearLayoutManager(context)

        provinceAdapter = CityAdapter()
        cityAdapter = CityAdapter()
        districtAdapter = CityAdapter()

        popupWindow =
                PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
        popupWindow!!.animationStyle = R.style.take_photo_anim
        popupWindow!!.setBackgroundDrawable(ColorDrawable(0x00000000))
        popupWindow!!.isOutsideTouchable = true
        popupWindow!!.setOnDismissListener(this)
        popupWindow!!.showAtLocation(anchor, Gravity.BOTTOM, 0, 0)

        provinceLayout!!.adapter = provinceAdapter
        provinceAdapter!!.setNewData(mProvinceList)

        mSpace!!.setOnClickListener {
            onDismiss()
        }

        provinceAdapter!!.setOnItemClickListener { adapter, view, position ->
            provinceAdapter!!.setPosition(position)
            mClick!!.onProvinceListener(mProvinceList!![position].id)
            cityAdapter = CityAdapter()
            cityLayout!!.adapter = cityAdapter
            districtAdapter = CityAdapter()
            districtLayout!!.adapter = districtAdapter
        }
    }

    override fun onDismiss() {
        popupWindow!!.dismiss()
    }

    interface OnClick {
        fun onProvinceListener(id: String?)

        fun onCityListener(id: String?)

        fun onDistrictListener(address: String?)
    }

}
