package com.tonghechuanmei.android.ui.activity.recharge

import android.os.Bundle
import android.view.View
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.grid
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityRechargeMobileBinding
import com.tonghechuanmei.android.model.RechargeMobileOptionModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.UserAuthActivity
import com.tonghechuanmei.android.ui.dialog.RechargePayDialog
import kotlinx.android.synthetic.main.activity_recharge_mobile.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class RechargeMobileActivity : SwipeBackActivity<ActivityRechargeMobileBinding>() {

    override fun initView() {

        darkMode()
        toolbar.setStatusBarPadding()
        rv_recharge_specific.grid(3).setup {
            addType<RechargeMobileOptionModel.Data.RechargeKindModel>(R.layout.item_rechage_mobile)
            onClick(R.id.item) {
                if (tv_phone.text.isEmpty()) {
                    toast("请先实名认证")
                    return@onClick
                }
                val model = getModel<RechargeMobileOptionModel.Data.RechargeKindModel>()
                RechargePayDialog().apply {
                    arguments = Bundle().apply { putParcelable("model", model) }
                }.show(supportFragmentManager, null)
            }
        }
    }

    override fun initData() {

        post<RechargeMobileOptionModel>("/recharge/getVirtualRecharge.json") {
            param("userId", UserModel.userId)
            param("type", 1)
        }.state(ll_container) {
            if (!it.data.phone.isNullOrEmpty()) {
                binding.m = it.data
            }
            rv_recharge_specific.baseAdapter.models = it.data.rechargeKindModels
            binding.v = this@RechargeMobileActivity
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_recharge_flow -> {
                startActivity<RechargeFlowActivity>()
            }
            R.id.iv_recharge_record -> {
                startActivity<RechargeRecordActivity>()
            }
            R.id.tv_phone -> {
                if (tv_phone.text.isEmpty()) {
                    startActivity<UserAuthActivity>(
                        "tag" to false,
                        "confirm" to "确认提交",
                        "isCheck" to true,
                        "isFail" to false
                    )
                }
            }
        }
    }

    @Subscribe(tags = [Tag("certification_finish_event")])
    fun finishCertification(event: String) {
        initData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_mobile)
    }
}
