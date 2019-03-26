/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/23/18 3:36 AM
 */

package com.tonghechuanmei.android.ui.activity.publish

import android.os.Bundle
import android.view.View
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPublishTaskAdvanceOptionBinding
import com.tonghechuanmei.android.model.PublishTaskModel
import org.jetbrains.anko.toast

class PublishTaskAdvanceOptionActivity :
        BaseToolbarActivity<ActivityPublishTaskAdvanceOptionBinding>() {

    lateinit var model: PublishTaskModel

    override fun initView() {
        title = "高级设置"
        binding.v = this
    }

    override fun initData() {
        model = intent.getSerializableExtra("model") as PublishTaskModel
        binding.m = model
    }

    override fun onClick(v: View) {

        when {
            model.hasGoodsAward && (!model.performCheck || !model.requireUserUpdateCertification) -> toast(
                    "勾选实物奖励必须审核以及用户提交审核凭证"
            )
            model.objName.isEmpty() && model.haveObj == "1" -> toast("请输入实体物品的名称")
            model.goodsPrice.isEmpty() && model.haveObj == "1" -> toast("请输入物品的实体参考价")
            else -> {
                RxBus.get().post(model)
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_task_advance_option)
    }
}
