/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/12/18 9:58 AM
 */

package com.tonghechuanmei.android.ui.activity.guide

import android.os.Bundle
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityNewbieGuideQuestionBinding
import com.tonghechuanmei.android.model.NewbieGuideQuestionModel

class NewbieGuideQuestionActivity : BaseToolbarActivity<ActivityNewbieGuideQuestionBinding>() {
    override fun initView() {
        title = "问题解答"
    }

    override fun initData() {
        val model = intent.getParcelableExtra<NewbieGuideQuestionModel.Data.Result>("model")
        binding.m = model
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newbie_guide_question)
    }
}
