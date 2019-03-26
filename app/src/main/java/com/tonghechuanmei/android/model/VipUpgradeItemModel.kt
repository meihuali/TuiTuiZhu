/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/28/18 1:50 AM
 */

package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.tonghechuanmei.android.BR

class VipUpgradeItemModel(val currentSort: Int, val sortName: String) : BaseObservable() {

    @Bindable
    var checked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }
}