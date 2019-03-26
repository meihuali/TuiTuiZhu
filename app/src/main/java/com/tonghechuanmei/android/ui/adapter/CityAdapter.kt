/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTZ
 * Author：马靖乘
 * Date：18-12-4 上午9:34
 */

package com.tonghechuanmei.android.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.model.AreaEntity

/**
 * Author     : shandirong
 * Date       : 2018/7/11 10:45
 */
class CityAdapter : BaseQuickAdapter<AreaEntity.DataBean, BaseViewHolder>(R.layout.item_city) {

    private var position = -1

    fun setPosition(position: Int) {
        this.position = position
        notifyDataSetChanged()
    }

    fun getPosition(): Int {
        return position
    }

    override fun convert(helper: BaseViewHolder, item: AreaEntity.DataBean) {
        helper.setText(R.id.city, item.name)
        if (helper.layoutPosition == position) {
            helper.setTextColor(R.id.city, Color.parseColor("#C6212D"))
        } else {
            helper.setTextColor(R.id.city, Color.parseColor("#3F3939"))
        }
    }
}
