/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/12/18 4:49 PM
 */

package com.designer.library.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.designer.library.base.getApp
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.setup
import org.jetbrains.anko.dip

/**
 * 解决常见的MaterialDesignDialog需求
 */

/**
 * 设置对话框的宽度
 * 必须在对话框显示之后执行
 */
fun Dialog.setWidth(withDipValue: Int) {
    val window = window
    window?.setLayout(getApp().dip(withDipValue.toFloat()), WRAP_CONTENT)
}


/**
 * 设置对话框的高度
 * 必须在对话框显示之后执行
 */
fun Dialog.setHeight(heightDipValue: Int) {
    val window = window
    window?.setLayout(WRAP_CONTENT, getApp().dip(heightDipValue.toFloat()))
}

/**
 * 设置对话框的宽高
 * 必须在对话框显示之后执行
 */
fun Dialog.setSize(withDipValue: Int, heightDipValue: Int) {
    val window = window
    window?.setLayout(getApp().dip(withDipValue.toFloat()), getApp().dip(heightDipValue.toFloat()))
}

/**
 * 设置背景透明
 */
fun Dialog.setTransparent() {
    val window = window
    window?.setBackgroundDrawableResource(android.R.color.transparent)
}


/**
 * 设置适配器
 *
 */
fun Dialog.setAdapter(block: BaseRecyclerAdapter.(RecyclerView) -> Unit): Dialog {
    val context = context
    val recyclerView = RecyclerView(context)
    recyclerView.setup(block)
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
    setContentView(recyclerView)
    return this
}

/**
 * 设置列表对话框的分割线
 * 分割线的间距由Drawable来控制
 */
fun AlertDialog.setDivider(@DrawableRes divider: Int) {
    val listView = this.listView
    if (listView != null) {
        listView.overscrollFooter = ColorDrawable(Color.TRANSPARENT)
        listView.divider = getApp().resources.getDrawable(divider)
    }
}

/**
 * 下拉菜单
 */
fun View.pullMenu(list: List<String>): ListPopupWindow {
    return ListPopupWindow(context).apply {
        anchorView = this@pullMenu
        setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, list))
    }
}

fun View.pullMenu(vararg list: String): ListPopupWindow {
    return ListPopupWindow(context).apply {
        anchorView = this@pullMenu
        setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, list))
    }
}

/**
 * 解决DialogFragment宽度限制问题
 */
fun androidx.fragment.app.DialogFragment.setWidthNoLimit() {
    setStyle(
        androidx.fragment.app.DialogFragment.STYLE_NO_TITLE,
        android.R.style.Theme_Holo_Light_Dialog_MinWidth
    )
}
