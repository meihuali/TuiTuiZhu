/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：两津勘吉
 * Date：11/24/18 3:27 PM
 */

package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.FragmentActivity
import com.designer.library.component.net.observer.dialog
import com.designer.library.utils.DateUtils
import com.designer.library.utils.downloadImage
import com.designer.library.utils.format
import io.reactivex.Observable
import io.reactivex.rxkotlin.combineLatest
import org.jetbrains.anko.collections.forEachWithIndex
import java.io.File
import java.io.Serializable

class PublishTaskModel : BaseObservable(), Serializable {

    /**
     * 恢复数据
     * @param activity FragmentActivity
     * @param data Data 任务详情
     * @param modify Boolean 是否属于修改任务
     * @constructor
     */
    fun restore(activity: FragmentActivity, data: TaskDetailModel.Data, modify: Boolean) {

        content = data.content ?: ""
        endTime = data.endTime.toString()
        startTime = data.startTime.toString()
        explicitLink = data.explicitLink ?: ""
        haveAudit = data.haveAudit ?: "0"
        haveObj = data.haveObj
        haveVoucher = data.haveVoucher
        lat = data.lat
        lng = data.lng
        name = data.name
        taskCategoryId = data.taskCategoryId
        contentId = data.contentId
        objName = data.objName
        objPrice = data.objPrice
        requirements = data.requirements
        serverAreaCityId = data.serverAreaCityId
        serverAreaCountyId = data.serverAreaCountyId
        serverAreaProvinceId = data.serverAreaProvinceId
        signUpNum = data.signUpNum.toString()
        taskCategoryId = data.taskCategoryId
        taskCategoryName = data.cateName ?: ""
        taskPrice = data.taskPrice.toString()
        userId = data.userId
        isModifyTask = modify

        // 下载图片
        val joinMediaPath = StringBuilder()
        val downloadImageObservable = mutableListOf<Observable<File>>()
        data.taskFileList.forEachWithIndex { i, taskFile ->
            if (i < data.taskFileList.size) {
                joinMediaPath.append("1,${taskFile.filePath};")
            } else {
                joinMediaPath.append("1,${taskFile.filePath}")
            }
            downloadImageObservable.add(activity.downloadImage(taskFile.filePath))
        }
        downloadImageObservable.combineLatest { downloadImages ->
            downloadImages.forEach {
                mediaList.add(it.path)
            }
        }.dialog(activity, false)

        filesStr = joinMediaPath.toString()
    }


    /**
     * 保存用户信息
     */
    fun setUser(user: UserDetailModel.Data) {
        beanNum = user.beanNum
        surplusIncome = user.surplusIncome
        memberId = user.memberId
    }

    /**
     * 搜索地址结果保存
     */
    fun setPoi(poiItem: SearchAddressModel) {
        lat = poiItem.poi.latLonPoint.longitude
        lng = poiItem.poi.latLonPoint.latitude
        serverAreaCountyId = poiItem.poi.adCode
        address = "${poiItem.poi.snippet} ${poiItem.poi.title}"
    }

    // 零钱
    var surplusIncome: Long = 0
        get() {
            return if (field < 0) {
                0
            } else {
                field
            }
        }

    @Bindable
    var address: String = LocationModel.address
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }

    var tangdouName: String = "糖豆"
        get() {
            return if (memberId == UserDetailModel.GOLD) {
                "广告余额"
            } else {
                "糖豆"
            }
        }

    // 糖豆
    var beanNum: Double = 0.0
        get() {
            return if (field < 0) {
                0.0
            } else {
                field
            }
        }

    var content: String = ""

    var contentId = ""

    var totalTangDou: String? = null
        get() = "${beanNum.format()} 个"

    var totalMoney: Double = 0.0
        get() {
            return userInputAwardMoney!!.toBigDecimal().multiply(signUpNum!!.toBigDecimal())
                    .setScale(2).toDouble()
        }

    var tangdouRequirePay: String? = null
        get() {
            return if (beanNum <= totalMoney) {
                "$beanNum 个"
            } else {
                "$totalMoney 个"
            }
        }

    // 提交服务器的金额字段
    var taskPrice: String = "0"
        get() {
            return if (userInputAwardMoney.isNullOrEmpty()) {
                "0"
            } else {
                (userInputAwardMoney!!.toDouble() * 100).toLong().toString()
            }
        }
        set(value) {
            field = value
            userInputAwardMoney =
                    field.toBigDecimal().divide(100.toBigDecimal()).setScale(2).toPlainString()
        }

    var balancerequirePay: String? = null
        get() {

            val residueMoney = if (beanNum < totalMoney) {
                totalMoney - beanNum
            } else {
                0.0
            }

            return when {
                residueMoney == 0.0 -> ""
                surplusIncome / 100.0 <= residueMoney -> "${(surplusIncome / 100.0).format()}  元"
                else -> "${residueMoney.format()} 元"
            }
        }

    // 支付页面确认按钮显示的需要第三方支付的金额
    var btnShowMoney: String? = null
        get() {
            if ((beanNum + (surplusIncome / 100.0) - totalMoney) >= 0) {
                return "确认"
            }
            return "确认并支付${(totalMoney - beanNum - surplusIncome.toDouble() / 100.0).format()}元"
        }


    // 用户输入的金额
    @Bindable
    var userInputAwardMoney: String = ""

    var totalBalance: String? = null
        get() = "${(surplusIncome / 100.0).format()} 元"

    var thirdPayWayVisible: Boolean = false
        get() {
            return surplusIncome.toDouble() / 100.0 + beanNum < totalMoney
        }

    //    可编辑
    var contentEdited: Boolean? = null
        get() = !content.isEmpty()

    var payType: String = "4"

    var startTime: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.startTimeStr)
        }

    var endTime: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.endTimeStr)
        }

    @Bindable
    var explicitLink: String = ""

    var filesStr: String? = null

    var haveAudit: String = "0"

    var haveObj: String = "0"

    var haveVoucher: String = "0"

    var name: String = ""

    var lat: Double = LocationModel.latitude

    var lng: Double = LocationModel.longitude

    var serverAreaCityId: String = LocationModel.cityCode

    var serverAreaCountyId: String = LocationModel.regionCode
        set(value) {
            field = value
            serverAreaProvinceId = value.substring(0, 2) + "0000"
            serverAreaCityId = value.substring(0, 4) + "00"
        }

    var serverAreaProvinceId: String = LocationModel.provinceCode

    //    当前用户等级
    var memberId: String? = null


    //   实物奖励名称
    @Bindable
    var objName: String = ""

    var objPrice: String = "0"
        get() {
            return if (goodsPrice.isNullOrEmpty()) {
                "0"
            } else {
                (goodsPrice!!.toDouble() * 100).toLong().toString()
            }
        }
        set(value) {
            field = value
            if (haveObj == "1") {
                goodsPrice = (value.toDouble() / 100.0).toString()
            }
        }

    @Bindable
    var goodsPrice: String = ""

    @Bindable
    var requirements: String = ""

    @Bindable
    var signUpNum: String = ""

    @Bindable
    var taskCategoryId: String? = null
        get() {
            return if (field == null) {
                taskCategory?.id
            } else {
                field
            }
        }

    var taskCategory: TaskTypeModel.Data? = null


    @Bindable
    var publishLevelVisible: Boolean? = null
        get() = memberId == UserDetailModel.SILVER

    var bronzeChecked = false
        get() {
            return memberId == UserDetailModel.BRONZE && memberId == UserDetailModel.SILVER
        }
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    memberId = UserDetailModel.BRONZE
                }
            }
        }

    var silverChecked = false
        get() {
            return memberId == UserDetailModel.SILVER && memberId == UserDetailModel.SILVER
        }
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    memberId = UserDetailModel.SILVER
                }
            }
        }

    @get:Bindable
    var hasGoodsAward = false
        get() {
            return haveObj == "1"
        }
        set(value) {
            field = value
            haveObj = when (value) {
                true -> {
                    "1"
                }
                else -> {
                    "0"
                }
            }
            notifyPropertyChanged(BR.hasGoodsAward)
        }

    var userId: String? = null

    // 本地多媒体路径
    var mediaList = mutableListOf<String>()

    @Bindable
    var startTimeStr: String? = null
        get() = DateUtils.formatDate(startTime, "yyyy年MM月dd日 HH:mm")

    @Bindable
    var endTimeStr: String? = null
        get() = DateUtils.formatDate(endTime, "yyyy年MM月dd日 HH:mm")

    // 当前是否属于修改活动操作
    var isModifyTask = false

    var taskCategoryName: String = ""
        get() {
            return taskCategory?.name ?: field
        }

    var requireUserUpdateCertification: Boolean = false
        get() = when (haveVoucher) {
            "1" -> true
            else -> {
                false
            }
        }
        set(value) {
            if (field != value) {
                field = value
                haveVoucher = when (field) {
                    true -> {
                        "1"
                    }
                    else -> {
                        "0"
                    }
                }
            }
        }


    var performCheck = false
        get() {
            return when (haveAudit) {
                "1" -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
        set(value) {
            field = value
            haveAudit = when (field) {
                false -> {
                    "0"
                }
                else -> {
                    "1"
                }
            }
        }

}