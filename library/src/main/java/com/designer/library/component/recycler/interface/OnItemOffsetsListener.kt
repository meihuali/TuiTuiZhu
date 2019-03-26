/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component.recycler.`interface`

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnItemOffsetsListener {

    fun onItemOffset(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State): Boolean
}
