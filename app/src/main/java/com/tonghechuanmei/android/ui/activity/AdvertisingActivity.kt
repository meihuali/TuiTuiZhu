/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-17 下午6:34
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getAdSpaceList
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityAdvertisingBinding
import com.tonghechuanmei.android.model.AdSpaceListModel
import kotlinx.android.synthetic.main.activity_advertising.*
import org.jetbrains.anko.startActivity

/**
 * 广告位
 */
class AdvertisingActivity : BaseToolbarActivity<ActivityAdvertisingBinding>() {
    override fun initView() {
        title = "广告位"
        refreshLayout.onRefresh {
            getAdSpaceList(page).page(refreshLayout) {
                if (it.msg == "-4" || it.data.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data) {
                        it.data[0].allpage >= refreshLayout.page
                    }
                }
            }
        }
    }

    override fun initData() {
        darkMode()
        refreshLayout.autoRefresh()
        advertising_rv.linear().setup {
            addType<AdSpaceListModel.Data>(R.layout.item_advertising_layout)
            addClickable(R.id.advertising_cl)
            onClick {
                startActivity<AdvertisingBuyActivity>("id" to (models!![layoutPosition] as AdSpaceListModel.Data).id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertising)
    }
}
