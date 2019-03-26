package com.tonghechuanmei.android.ui.activity.recharge

import android.os.Bundle
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.divider
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityRechargeRecordBinding
import com.tonghechuanmei.android.model.RechargeRecordModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.activity_recharge_record.*

class RechargeRecordActivity : BaseToolbarActivity<ActivityRechargeRecordBinding>() {

    override fun initView() {
        title = "充值记录"

        rv_recharge_record.divider(R.drawable.divider_horizontal_padding_15dp).linear().setup {
            addType<RechargeRecordModel.Data.Result>(R.layout.item_recharge_record)
        }
    }

    override fun initData() {

        pl_recharge_record.onRefresh {
            post<RechargeRecordModel>("/recharge/getRechargeDetails.json") {
                param("userId", UserModel.userId)
                param("page", pl_recharge_record.page)
            }.page(pl_recharge_record) {
                if (it.msg == "-4") {
                    showEmpty()
                } else {
                    refreshData(it.data.resultList) {
                        it.data.pageInfo.allpage >= page
                    }
                }
            }
        }
        pl_recharge_record.autoRefresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_record)
    }
}
