/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/28/18 1:50 AM
 */

package com.tonghechuanmei.android.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.amap.api.services.core.PoiItem
import com.tonghechuanmei.android.BR

class SearchAddressModel(var poi: PoiItem) : BaseObservable(), Parcelable {

    @Bindable
    var checked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }

    constructor(source: Parcel) : this(
        source.readParcelable<PoiItem>(PoiItem::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(poi, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SearchAddressModel> = object : Parcelable.Creator<SearchAddressModel> {
            override fun createFromParcel(source: Parcel): SearchAddressModel = SearchAddressModel(source)
            override fun newArray(size: Int): Array<SearchAddressModel?> = arrayOfNulls(size)
        }
    }
}