/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/28/18 3:09 PM
 */

package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.tonghechuanmei.android.BR
import com.yanzhenjie.kalle.Params

/**
 * 活动筛选模型
 */
class TaskFilterModel : BaseObservable() {
    val paramsBuilder = Params.newBuilder().add("userId", UserModel.userId).add("lat", LocationModel.latitude)
        .add("lng", LocationModel.longitude)


    @get:Bindable
    var isComprehensiiveFilter = true
        set(value) {
            field = value
            if (value) {

                isTypeFilter = false
                isRegionFilter = false
                notifyPropertyChanged(BR.typeFilter)
                notifyPropertyChanged(BR.regionFilter)
                notifyPropertyChanged(BR.comprehensiiveFilter)
            }
        }

    @get:Bindable
    var isTypeFilter = false
        set(value) {
            field = value
            if (value) {
                isComprehensiiveFilter = false
                isRegionFilter = false

                notifyPropertyChanged(BR.typeFilter)
                notifyPropertyChanged(BR.regionFilter)
                notifyPropertyChanged(BR.comprehensiiveFilter)
            }
        }

    @get:Bindable
    var isRegionFilter = false
        set(value) {
            field = value
            if (value) {

                isComprehensiiveFilter = false
                isTypeFilter = false

                notifyPropertyChanged(BR.typeFilter)
                notifyPropertyChanged(BR.regionFilter)
                notifyPropertyChanged(BR.comprehensiiveFilter)
            }
        }


    @get:Bindable
    var comprehensiveFilterName: String = "综合排序"
        set(value) {
            field = value
            notifyPropertyChanged(BR.comprehensiveFilterName)
        }

    @Bindable
    var typeFilterName: String = "类型"
        set(value) {
            field = value
            notifyPropertyChanged(BR.typeFilterName)
        }

    @get:Bindable
    var regionFilterName: String = "区域"
        set(value) {
            field = value
            notifyPropertyChanged(BR.regionFilterName)
        }


    /**
     * 综合排序
     * @param name String
     */
    fun setComprehensiveFilter(name: String) {
        isComprehensiiveFilter = true
        paramsBuilder.remove("orderby")
        comprehensiveFilterName = name
        when (name) {
            "综合排序" -> {
                paramsBuilder.add("orderby", "1_0")
            }
            "时间排序" -> {
                paramsBuilder.add("orderby", "1_1")
            }
            "奖励排序" -> {
                paramsBuilder.add("orderby", "1_2")
            }
            "距离排序" -> {
                paramsBuilder.add("orderby", "1_3")
            }
            else -> {
                paramsBuilder.add("orderby", "1_0")
            }
        }
    }

    fun removeComprehensiveFilter() {
        paramsBuilder.remove("orderby")
    }

    /**
     * 类型过滤(活动分类)
     * @param name String
     */
    fun setTypeFilter(type: TaskTypeModel.Data) {
        isTypeFilter = true

        typeFilterName = type.name

        paramsBuilder.remove("taskCategoryId")
        paramsBuilder.add("taskCategoryId", type.id)
    }

    /**
     * 区域过滤
     * @param province String
     * @param city String
     * @param regoin String
     */
    fun setRegionFilter(regionModel: RegionSelectorModel) {
        isRegionFilter = true
        removeRegoinFilter()
        regionFilterName = regionModel.addressName ?: "距离"

        regionModel.provinceId?.let {
            paramsBuilder.add("serverAreaProvinceId", regionModel.provinceId)
        }
        regionModel.cityId?.let {
            paramsBuilder.add("serverAreaCityId", regionModel.cityId)
        }
        regionModel.regionId?.let {
            paramsBuilder.add("serverAreaCountyId", regionModel.regionId)
        }
    }

    fun removeRegoinFilter() {
        paramsBuilder.remove("serverAreaProvinceId")
            .remove("serverAreaCityId")
            .remove("serverAreaCountyId")
    }

    fun clear() {
        paramsBuilder.remove("orderby")
            .remove("taskCategoryId")
            .remove("serverAreaProvinceId")
            .remove("serverAreaCityId")
            .remove("serverAreaCountyId")
    }

    fun get(): Params {
        return paramsBuilder.build()
    }
}