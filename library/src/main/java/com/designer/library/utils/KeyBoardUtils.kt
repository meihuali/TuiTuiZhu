/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/12/18 4:49 PM
 */

package com.designer.library.utils

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.designer.library.base.Library
import com.designer.library.base.getApp


/**
 * 支持键盘的工具类
 */
class KeyBoardUtils private constructor() {

    companion object {

        var keyboardVisibleMinHeight = 100

        /**
         * 切换键盘 显示/隐藏
         */
        fun toggleKeyboard() {
            val imm =
                getApp().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        /**
         * 键盘是否显示
         *
         */
        fun keyboardVisible(window: Window?): Boolean {
            return getKeyboardHeight(window) > keyboardVisibleMinHeight
        }

        /**
         * 返回键盘高度
         */
        private fun getKeyboardHeight(window: Window?): Int {
            window ?: return 0

            val outRect = Rect()
            val decorView = window.decorView
            decorView.getWindowVisibleDisplayFrame(outRect)
            return decorView.bottom - outRect.bottom
        }


        /**
         * 设置View始终浮动于键盘之上
         *
         *
         * 要求必须添加Window属性
         * `android:windowSoftInputMode="stateHidden|adjustResize"`
         */
        @JvmOverloads
        fun setViewScrollToFloatKeyboard(
            window: Window?,
            view: View,
            onKeyboardChanged: ((keyboardVisible: Boolean, keyboardHeight: Int) -> Unit)? = null
        ) {

            window ?: return
            view.parent ?: throw NullPointerException("View must has parent")

            val viewParent = view.parent as ViewGroup
            val parentScrollY = viewParent.scrollY
            val tempStatusBarHeight = statusBarHeight
            val decorView = window.decorView

            var keyboardVisible = false

            view.viewTreeObserver.addOnGlobalLayoutListener {

                val displayRect = Rect()
                decorView.getWindowVisibleDisplayFrame(displayRect)

                val keyboardHeight = decorView.bottom - (displayRect.bottom - tempStatusBarHeight)

                if (keyboardHeight > keyboardVisibleMinHeight) {
                    val viewLocation = IntArray(2)
                    view.getLocationInWindow(viewLocation)

                    val scrollY =
                        viewLocation[1] + view.height - (displayRect.bottom - tempStatusBarHeight) + parentScrollY

                    // 表示并未被键盘遮挡
                    if (scrollY <= 0) {
                        return@addOnGlobalLayoutListener
                    }

                    if (!keyboardVisible) {
                        keyboardVisible = true
                        onKeyboardChanged?.invoke(true, keyboardHeight)
                        viewParent.scrollTo(viewParent.scrollX, scrollY)
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false
                        onKeyboardChanged?.invoke(false, keyboardHeight)
                        viewParent.scrollTo(viewParent.scrollX, parentScrollY)
                    }
                }
            }
        }

        @JvmOverloads
        fun setViewPaddingToFloatKeyboard(
            window: Window?,
            view: View,
            onKeyboardChanged: ((keyboardVisible: Boolean, keyboardHeight: Int) -> Unit)? = null
        ) {

            window ?: return
            view.parent ?: throw NullPointerException("View must has parent")

            val viewParent = view.parent as ViewGroup
            val tempStatusBarHeight = statusBarHeight
            val decorView = window.decorView
            var keyboardVisible = false

            view.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
                val displayRect = Rect()
                decorView.getWindowVisibleDisplayFrame(displayRect)


                val keyboardHeight =
                    decorView.bottom - (displayRect.bottom - tempStatusBarHeight)


                if (keyboardHeight > keyboardVisibleMinHeight + tempStatusBarHeight) {
                    val viewLocation = IntArray(2)
                    view.getLocationInWindow(viewLocation)

                    val paddingHeight =
                        viewLocation[1] + view.height - (displayRect.bottom - tempStatusBarHeight)

                    // 表示并未被键盘遮挡
                    if (paddingHeight <= 0) {
                        return@OnGlobalLayoutListener
                    }

                    if (!keyboardVisible) {
                        keyboardVisible = true

                        onKeyboardChanged?.invoke(true, keyboardHeight)
                        viewParent.setPadding(
                            viewParent.paddingLeft,
                            viewParent.paddingTop,
                            viewParent.paddingRight,
                            paddingHeight
                        )
                    }

                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false
                        onKeyboardChanged?.invoke(false, keyboardHeight)
                        viewParent.setPadding(
                            viewParent.paddingLeft,
                            viewParent.paddingTop,
                            viewParent.paddingRight,
                            0
                        )
                    }
                }
            })
        }


        /**
         * 修复在 15 < api < 23 中存在因为键盘持有Activity引用而导致内存泄漏的问题
         *
         * @param context The context.
         */
        fun fixKeyboardLeak(context: Context?) {
            if (context == null) {
                return
            }
            val imm = Library.app!!
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val strArr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
            for (i in 0..2) {
                try {
                    val declaredField = imm.javaClass.getDeclaredField(strArr[i]) ?: continue
                    if (!declaredField.isAccessible) {
                        declaredField.isAccessible = true
                    }
                    val obj = declaredField.get(imm)
                    if (obj == null || obj !is View) {
                        continue
                    }
                    if (obj.context === context) {
                        declaredField.set(imm, null)
                    } else {
                        return
                    }
                } catch (th: Throwable) {
                    th.printStackTrace()
                }

            }
        }
    }
}

/**
 * 隐藏软键盘
 */
fun Activity.hideKeyboard() {
    val im = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus
    if (view != null) {
        if (im.isActive) {
            im.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}


/**
 * 显示键盘
 *
 * @param activity The activity.
 */
fun Activity.showKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.showSoftInput(view, 0)
}

/**
 * 强制弹出输入法
 */
fun EditText.showKeyboard() {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    val imm = getApp().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}

fun EditText.hideKeyboard() {
    val imm = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}


