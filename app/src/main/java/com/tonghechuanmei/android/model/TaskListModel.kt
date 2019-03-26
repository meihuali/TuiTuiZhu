/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/23/18 11:41 AM
 */

package com.tonghechuanmei.android.model

import androidx.databinding.BaseObservable
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng
import com.designer.library.utils.UnitUtils
import com.designer.library.utils.format

data class TaskListModel(
    var `data`: Data,
    var msg: String,
    var success: Boolean
) {
    data class Data(
        var pageInfo: PageInfo,
        var resultList: List<Result>
    ) : BaseObservable() {
        data class Result(
            var allTaskPrice: Int,
            var award: String,
            var cateName: String,
            var content: String,
            var contentFileId: String,
            var contentId: String,
            var createTime: String,
            var createUserId: String,
            var distance: Double,
            var endTime: Long,
            var explicitLink: String,
            var haveAudit: String,
            var haveObj: String,
            var haveVoucher: String,
            var headImg: String,
            var id: String,
            var isNovice: String,
            var lat: Double,
            var lng: Double,
            var memberId: String,
            var memberLevelLogo: String,
            var name: String,
            var nickName: String,
            var objName: String,
            var objPrice: String,
            var reason: String,
            var requirements: String,
            var serverAreaCityId: String,
            var serverAreaCountyId: String,
            var serverAreaProvinceId: String,
            var signState: String?,
            var signUpAleady: String,
            var signUpNum: Int,
            var signedNum: String,
            var sort: String,
            var startTime: Long,
            var state: String,
            var taskCategoryId: String,
            var taskFileList: String,
            var taskPrice: Long,
            var userId: String,
            var adress: String?
        ) {

            /*  var award: String? = null
                  get() {
                      val stringBuffer = StringBuffer("获得 ${taskPrice / 100.0} 个奖励")
                      if (haveObj == "1") {
                          stringBuffer.append(" + $objName")
                      }
                      return stringBuffer.toString()
                  }*/

            var applyPersonCount: String? = null
                get() = "$signedNum/${signUpNum}人"


            var taskStatus: String? = null
                get() = when (signState) {
                    "-2" -> "审核失败"
                    "0" -> "已报名"
                    "1" -> "待审核"
                    "2" -> "已通过"
                    else -> "去报名"
                }

            var goApplyVisible: Boolean = false
                get() {
                    return signState.isNullOrEmpty()
                }

            fun getAddress(): String {
                return if (adress.isNullOrEmpty()) {
                    calculateDistance()
                } else {
                    adress!!
                }
            }

            fun getDistance(): String {
                if (adress.isNullOrEmpty()) {
                    return ""
                }
                return calculateDistance()
            }

            private fun calculateDistance(): String {
                val calculateLineDistance = AMapUtils.calculateLineDistance(
                        LatLng(LocationModel.latitude, LocationModel.longitude),
                        LatLng(lat, lng)
                )
                return if (adress.isNullOrEmpty()) {
                    UnitUtils.distance(calculateLineDistance)
                } else {
                    " • ${UnitUtils.distance(calculateLineDistance)}"
                }
            }

            fun getPriceTv(): String {
                return "¥${taskPrice.format()}"
            }
        }

        data class PageInfo(
            var allcount: Int,
            var allpage: Int,
            var contentFileId: String,
            var contentId: String,
            var createTime: String,
            var createUserId: String,
            var currentUserId: String,
            var endTime: String,
            var explicitLink: String,
            var haveObj: String,
            var id: String,
            var isNovice: String,
            var lat: String,
            var lng: String,
            var name: String,
            var nowTime: String,
            var objName: String,
            var objPrice: String,
            var orderby: String,
            var page: Int,
            var payAdFee: String,
            var payPrice: String,
            var reason: String,
            var requirements: String,
            var rows: Int,
            var serverAreaCityId: String,
            var serverAreaCountyId: String,
            var serverAreaProvinceId: String,
            var signUpAleady: String,
            var signUpNum: String,
            var sort: String,
            var start: Int,
            var startTime: String,
            var state: String,
            var states: String,
            var taskCategoryId: String,
            var taskPrice: String,
            var userId: String
        )
    }
}