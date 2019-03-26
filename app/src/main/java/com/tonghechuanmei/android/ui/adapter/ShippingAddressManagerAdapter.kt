/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 下午1:34
 */

package com.tonghechuanmei.android.ui.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.model.UserAddressEntity

/**
 * 管理地址item
 *
 * Author     : shandirong
 * Date       : 2018/6/28 17:09
 */
class ShippingAddressManagerAdapter :
    BaseQuickAdapter<UserAddressEntity.DataBean, BaseViewHolder>(R.layout.item_shipping_address_manager) {

    override fun convert(helper: BaseViewHolder, item: UserAddressEntity.DataBean) {
        helper.addOnClickListener(R.id.modify)
            .addOnClickListener(R.id.delete)
            .addOnClickListener(R.id.tv_edit)

        helper.setText(R.id.name, item.name)
        helper.setText(R.id.phone, item.phone)
        helper.setText(R.id.address, item.address)
        val checkBox = helper.getView<CheckBox>(R.id.check_box)
        checkBox.isEnabled = item.isDefault != "Y"
        helper.getView<CheckBox>(R.id.check_box).isChecked = item.isDefault == "Y"

        if (!helper.getView<CheckBox>(R.id.check_box).isChecked) {
            helper.addOnClickListener(R.id.check_box)
        }
    }
}
