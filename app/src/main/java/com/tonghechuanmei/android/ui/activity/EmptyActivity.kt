package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.base.SwipeBackActivity
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityEmptyBinding
import kotlinx.android.synthetic.main.activity_empty.*

class EmptyActivity : SwipeBackActivity<ActivityEmptyBinding>() {
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        binding.v = this
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.iv_return -> {
                finish()
            }
        }
    }
}
