/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午6:21
 */

package com.tonghechuanmei.android.ui.activity.otherserver

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.databinding.ActivityExChangeDetailBinding
import com.tonghechuanmei.android.model.ExChangeDetailModel
import com.tonghechuanmei.android.model.ProductSearchDetailModel
import com.tonghechuanmei.android.model.UserDetailModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_ex_change_detail.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ExChangeDetailActivity : SwipeBackActivity<ActivityExChangeDetailBinding>() {
    lateinit var model: ProductSearchDetailModel.Data
    lateinit var userModel: UserDetailModel.Data
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        binding.v = this
    }

    override fun initData() {
        getUserDetail().dialog(this) {
            if (it.msg == "1") {
                userModel = it.data
            } else {
                UserModel.clear()
                startActivity<LoginActivity>()
                com.designer.library.base.BaseActivity.finishAll("LoginActivity")
            }
        }
        rv_ex_change.linear().setup {
            addType<ExChangeDetailModel>(R.layout.item_ex_change_detail_layout)
        }
        OtherServer.getInfoById(intent.getStringExtra("id")).dialog(this) {
            model = it.data
            val detailModel = it.data
            detailModel.number = 1
            binding.m = detailModel
            binding.v = this@ExChangeDetailActivity
            val list = ArrayList<ExChangeDetailModel>()
            if (it.data.productImageDatail != null) {
                val productImageList = it.data.productImageDatail!!.split("_")
                for (bean in productImageList) {
                    if (!bean.isEmpty()) {
                        var s = ""
                        var image = ""
                        if (bean.contains("http:")) {
                            image = bean
                        } else {
                            s = bean
                        }
                        val detailBean = ExChangeDetailModel(s, image)
                        list.add(detailBean)
                    }
                }
                rv_ex_change.baseAdapter.models = list
            }
            ex_change_ll.isGone = model.inventory <= 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex_change_detail)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.confirm -> {
                if (this::userModel.isInitialized) {
                    if (tv_ex_change_price.text.toString().contains("糖豆")) {
                        if (userModel.beanNum.toLong() >=
                                tv_ex_change_price.text.toString().replace("糖豆", "").toLong()
                        ) {    //判断用户糖豆数是否大于或等于可支付糖豆数
                            startActivity<ConfirmExChangeActivity>("m" to model)
                        } else {
                            toast("糖豆不足，不可兑换")
                        }
                    } else if (UserModel.memberLevelId != UserDetailModel.GOLD && UserModel.allContributeNum.toDouble() < (if (model.contributeValue.isNullOrEmpty()) "0" else model.contributeValue).toDouble()) {
                        toast("当前贡献值不够无法兑换该商品哦，快去赚取贡献值吧！")
                    } else {
                        startActivity<ConfirmExChangeActivity>("m" to model)
                    }
                }
            }
            R.id.action_back -> finish()
        }
    }
}
