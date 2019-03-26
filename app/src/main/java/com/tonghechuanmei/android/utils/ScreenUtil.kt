/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：tuituizhu
 * Author：马靖乘
 * Date：18-12-4 下午10:59
 */

package com.tonghechuanmei.android.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenUtil private constructor(private val context: Context) {

    /**
     * 得到手机屏幕的宽度, pix单位
     */
    val screenWidth: Int
        get() = width

    init {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        manager.defaultDisplay.getMetrics(dm)
        width = dm.widthPixels
        height = dm.heightPixels
    }

    companion object {
        var height: Int = 0
        var width: Int = 0
        private var instance: ScreenUtil? = null

        fun getInstance(context: Context): ScreenUtil {
            if (instance == null) {
                instance = ScreenUtil(context)
            }
            return instance!!
        }
    }


}
