/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-12-2 下午11:36
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.utils.RegexUtils
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.databinding.ActivityShippingAddressDetailsBinding
import com.tonghechuanmei.android.model.AreaEntity
import com.tonghechuanmei.android.model.UserAddressEntity
import com.tonghechuanmei.android.utils.AlertUtils
import com.tonghechuanmei.android.widget.CityWindow
import kotlinx.android.synthetic.main.activity_shipping_address_details.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * 新增地址、编辑地址
 */
class ShippingAddressDetailsActivity : SwipeBackActivity<ActivityShippingAddressDetailsBinding>(),
    CityWindow.OnClick {
    private var mCityList = ArrayList<AreaEntity.DataBean>()
    private var cityWindow = CityWindow(this)
    private var bean: UserAddressEntity.DataBean? = null
    private var type = 0
    private var mLocation = ""
    @SuppressLint("SetTextI18n")
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        binding.v = this
        if (intent.getSerializableExtra("data") == null) {
            tv_title.text = "新增地址"
            tv_delete.isGone = true
        } else {
            bean = intent.getSerializableExtra("data") as UserAddressEntity.DataBean
            tv_title.text = "编辑地址"
            tv_delete.text = "删除"
            tv_delete.isGone = false
            name.setText(bean!!.name)
            phone.setText(bean!!.phone)
            if (!bean!!.cityArea.isNullOrEmpty()) {
                location.text = bean!!.cityArea
            }
            address.setText(bean!!.address)
        }
        cityWindow.setClick(this)
    }

    override fun initData() {
        OtherServer.getAreaCurData(null).net(this) {
            mCityList = it.data!! as ArrayList<AreaEntity.DataBean>
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping_address_details)
    }

    //省
    override fun onProvinceListener(id: String?) {
        type = 1
        OtherServer.getAreaCurData(id).net(this) {
            cityWindow.setCityList(it.data)
        }
    }

    //市
    override fun onCityListener(id: String?) {
        type = 2
        OtherServer.getAreaCurData(id).net(this) {
            cityWindow.setDistrictList(it.data)
        }
    }

    //区
    override fun onDistrictListener(address: String?) {
        if (!address.isNullOrEmpty() && address!!.contains(",")) {
            val s = address.split(",")
            if (s.size == 3) {
                mLocation = s[0] + "," + s[1] + "," + s[2]
                location.text = mLocation
            }
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.action_back -> finish()
            R.id.btn -> {
                if (name.text.toString().isEmpty()) {
                    toast("请填写收货人的姓名")
                    return
                }
                if (phone.text.toString().isEmpty()) {
                    toast("请填写收货手机号码")
                    return
                }
                if (!RegexUtils.isMobileExact(phone.text.toString())) {
                    toast(resources.getString(R.string.phone_error))
                    return
                }
                if (location.text.toString() == "点击选择省、市、区") {
                    toast("请选择省市区")
                    return
                }
                if (address.text.toString().isEmpty()) {
                    toast("请填写收货的详细门牌号")
                    return
                }
                if (bean == null) {
                    OtherServer.addAddress(
                        address.text.toString().trim(), location.text.toString().trim(),
                        "Y", name.text.toString().trim(), phone.text.toString().trim(),
                        phone.text.toString().trim(), null
                    ).dialog(this) {
                        toast("保存成功")
                        RxBus.get().post("refresh_address_event", "刷新地址")
                        finish()
                    }
                } else {
                    OtherServer.addAddress(
                        address.text.toString().trim(), location.text.toString().trim(),
                        "Y", name.text.toString().trim(), phone.text.toString().trim(),
                        phone.text.toString().trim(), bean!!.id
                    ).dialog(this) {
                        toast("保存成功")
                        RxBus.get().post("refresh_address_event", "刷新地址")
                        finish()
                    }
                }
                val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val v: View = currentFocus
                im.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
            R.id.location -> {
                type = 0
                cityWindow.setProvinceList(mCityList)
                cityWindow.showPopupWindow(window.decorView)
            }
            R.id.tv_delete -> {
                AlertUtils.normalAlert(this@ShippingAddressDetailsActivity, "确认删除该地址？") { it ->
                    OtherServer.delAddress(bean!!.id).dialog(this) {
                        toast("删除成功")
                        AlertUtils.dismiss()
                        RxBus.get().post("refresh_address_event", "刷新地址")
                        finish()
                    }
                }
            }
        }
    }
}
