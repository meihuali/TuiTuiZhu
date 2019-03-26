/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/25/18 1:57 PM
 */

package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.designer.library.BR

data class ProvinceModel(
    val `data`: List<Data>,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val cityAreaId: String,
        val cityAreaName: String,
        val firstLetter: String,
        val id: String,
        val level: Int,
        val name: String,
        val parentId: String
    ) : BaseObservable() {
        @Bindable
        var selected: Boolean = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.selected)
            }
    }
}