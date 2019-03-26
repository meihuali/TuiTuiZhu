/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-14 下午8:42
 */

package com.tonghechuanmei.android.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.model.RedPacketEntity
import com.tonghechuanmei.android.ui.activity.RedPacketActivity
import kotlinx.android.synthetic.main.activity_red_packet.*


/**
 * 红包兑换
 */
class RedPacketAdapter(val activity: RedPacketActivity) :
    BaseQuickAdapter<RedPacketEntity, BaseViewHolder>(R.layout.item_red_packet_layout) {
    override fun convert(helper: BaseViewHolder, item: RedPacketEntity) {
        val tv = helper.getView<TextView>(R.id.red_packet_tv)
        helper.setText(R.id.red_packet_tv, "¥" + item.money.toString())
        if (!item.isClick) {
            grayBg(helper)
        } else {
            if (activity.currentPos == helper.layoutPosition) {
                if (item.isSelect) {
                    normalBg(item, tv, helper)
                    activity.confirm.setBackgroundResource(R.drawable.start_end_ground_price_bg)
                } else {
                    selectBg(tv, item, helper)
                    activity.confirm.setBackgroundResource(R.drawable.start_end_ground_bg)
                }
            } else {
                normalBg(item, tv, helper)
            }
        }
    }

    private fun grayBg(helper: BaseViewHolder) {
        helper.setBackgroundRes(R.id.red_packet_tv, R.drawable.red_packet_gray_bg)
            .setTextColor(R.id.red_packet_tv, ContextCompat.getColor(mContext, R.color.gray))
    }

    private fun selectBg(tv: TextView, item: RedPacketEntity, helper: BaseViewHolder) {
        tv.isEnabled = true
        item.isSelect = true
        helper.setBackgroundRes(R.id.red_packet_tv, R.drawable.red_packet_select_bg)
            .setTextColor(R.id.red_packet_tv, ContextCompat.getColor(mContext, R.color.white))
    }

    private fun normalBg(item: RedPacketEntity, tv: TextView, helper: BaseViewHolder) {
        item.isSelect = false
        tv.isEnabled = false
        helper.setBackgroundRes(R.id.red_packet_tv, R.drawable.red_packet_normal_bg)
            .setTextColor(R.id.red_packet_tv, ContextCompat.getColor(mContext, R.color.textColor))
    }
}