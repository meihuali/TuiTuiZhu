package com.tonghechuanmei.android.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.designer.library.base.SwipeBackActivity
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.jakewharton.rxbinding3.view.clicks
import com.tonghechuanmei.android.R
import kotlinx.android.synthetic.main.activity_base_toolbar.*
import java.util.concurrent.TimeUnit

abstract class BaseToolbarActivity<B : ViewDataBinding> : SwipeBackActivity<B>() {


    override fun setTitle(title: CharSequence) {
        tv_title.text = title
    }

    @SuppressLint("InflateParams", "CheckResult", "AutoDispose")
    override fun setContentView(layoutResID: Int) {
        val root = layoutInflater.inflate(R.layout.activity_base_toolbar, null) as ViewGroup
        setContentView(root)

        rootView = layoutInflater.inflate(layoutResID, null)
        ll_root.addView(
            rootView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        binding = DataBindingUtil.bind(rootView)!!

        darkMode()
        toolbar.setStatusBarPadding()

        action_back.clicks().throttleFirst(500, TimeUnit.MILLISECONDS).subscribe {
            close()
        }

        init()
    }

    open fun close() {
        finishTransition()
    }
}
