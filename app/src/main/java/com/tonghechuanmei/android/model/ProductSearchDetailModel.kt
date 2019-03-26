/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TTPig
 * Author：马靖乘
 * Date：18-11-29 上午11:10
 */

package com.tonghechuanmei.android.model

import androidx.core.content.ContextCompat
import com.designer.library.base.getApp
import com.designer.library.widget.PriceUtils
import com.tonghechuanmei.android.R
import java.io.Serializable

data class ProductSearchDetailModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) : Serializable {
    data class Data(
        val activityCode: Any,
        val activityId: Any,
        val activityType: String,
        val articleDetail: Any,
        val beanPrice: Long,
        val brandId: Any,
        val brandName: Any,
        val categoryAreaId: String,
        val categoryId: String,
        val categoryName: String,
        val categoryWapName: Any,
        val collectNum: Int,
        val commentCount: Int,
        val commentNegativeCount: Int,
        val commentNo: Int,
        val commentPicCount: Int,
        val commission: Any,
        val commissionRate: String,
        val contributeValue: String,
        val costPrice: Long,
        val createTime: Long,
        val delState: String,
        val detail: String,
        val firstCategoryId: String,
        val firstCategoryName: String,
        val firstCategoryWapName: Any,
        val freightName: Any,
        val grade: String,
        val gradeNum: Int,
        val id: String,
        val imUid: Any,
        val integral: String,
        val intro: String,
        val inventory: Int,
        val isBoutiquePinkage: String,
        val isChoiceness: String,
        val isCollected: String,
        val isGuess: String,
        val isHome: String,
        val isHot: String,
        val isHotGrade: String,
        val isNew: String,
        val isNewStrange: String,
        val isOnlyIntegralPay: String,
        val isPinkage: String,
        val isProperty: String,
        val isRecommend: String,
        val isStandards: String,
        val keyword: Any,
        val marketPrice: Long,
        val modelId: String,
        val modelName: Any,
        val name: String,
        val pCategoryId: Any,
        val photoAlbum: String,
        val price: Long,
        val productImage: String,
        val productImageDatail: String,
        val productNo: String,
        val recommendImage: Any,
        val recommendImageHeight: Any,
        val recommendImageWidth: Any,
        val remark: String,
        val salesMonthNum: Int,
        val salesNum: Int,
        val salesQuota: Int,
        val secondCategoryId: Any,
        val secondCategoryName: Any,
        val sku: String,
        val sort: String,
        val state: String,
        val storeCategoryId: Any,
        val storeFreightId: String?,
        val storeId: String,
        val storeImage: Any,
        val storeIntro: Any,
        val storeLogisticsDescribeDegrees: Any,
        val storeName: Any,
        val storeNavigationId: String,
        val storeNavigationName: Any,
        val storeProductDescribeDegrees: Any,
        val storeServerDescribeDegrees: Any,
        val storekeyWords: Any,
        val suppliersId: Any,
        val tjProductPropertyDataList: Any,
        val tjProductStandardDataList: Any,
        val tjPropertyDataModelList: Any,
        val updateTime: Long,
        val weight: String,
        var number: Int,
        var surplusIncome: Long,
        var beanNum: Double
    ) : Serializable {
        fun getPayPrice(): String {
            val money =
                if (surplusIncome >= price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong()) {
                    price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong()
                } else {
                    surplusIncome
                }
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {       //黄金会员显示金额，非黄金会员显示糖豆
                "¥${PriceUtils.getPrice(price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong() - money)}"
            } else {
                "${beanPrice * number + ((if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong()) / 100}糖豆"
            }
        }

        fun getUpPayPrice(): String {
            val money = if (surplusIncome >= price * number) {
                price * number
            } else {
                surplusIncome
            }
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {       //黄金会员显示金额，非黄金会员显示糖豆
                "¥${PriceUtils.getPrice(price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong())}"
            } else {
                "${beanPrice * number + ((if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong()) / 100}糖豆"
            }
        }

        fun getDetailPayPrice(): String {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {       //黄金会员显示金额，非黄金会员显示糖豆
                "¥${PriceUtils.getPrice(price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong())}"
            } else {
                "${beanPrice * number + ((if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong()) / 100}糖豆"
            }
        }

        fun getNumberTv(): String {
            return "×$number"
        }

        fun getCommodityNumber(): String {
            return "共${number}件商品，合计"
        }

        fun getDeletePrice(): String {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {       //黄金会员显示金额，非黄金会员显示糖豆
                "¥${PriceUtils.getPrice(marketPrice * number)}"
            } else {
                if (contributeValue.isNullOrEmpty()) {
                    ("(贡献值≥0)")
                } else {
                    ("(贡献值≥$contributeValue)")
                }
            }
        }

        fun getStoreFreight(): String {
            return if (storeFreightId.isNullOrEmpty()) {
                "快递免邮"
            } else {
                if (storeFreightId.toLong() == 0L) {
                    "快递免邮"
                } else {
                    if (UserModel.memberLevelId == UserDetailModel.GOLD) {
                        "¥${PriceUtils.getPrice(storeFreightId.toLong())}"
                    } else {
                        "${(storeFreightId.toLong() / 100)}糖豆"
                    }
                }
            }
        }

        fun getIsAddMiddleLine(): Boolean {
            return UserModel.memberLevelId == UserDetailModel.GOLD
        }

        fun getBeanTv(): String {
            return "糖豆：(剩余${beanNum}个)"
        }

        //糖豆右边
        fun getRightBean(): String {
            return "${beanPrice * number + if (storeFreightId.isNullOrEmpty()) 0 else storeFreightId.toLong() / 100}糖豆"
        }

        fun getIncome(): String {
            return "零钱：(剩余${PriceUtils.getPrice(surplusIncome)})"
        }

        //零钱
        fun getDetailPrice(): String {
            return if (surplusIncome >= price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong()) {
                "${PriceUtils.getPrice(price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong())}元"
            } else {
                "${PriceUtils.getPrice(surplusIncome)}元"
            }
        }

        //判断立即兑换能否点击
        fun getIsClick(): Boolean {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {   //黄金会员
                true
            } else {
                beanPrice * number <= beanNum      //如果当前价格大于用户剩余的糖豆则不能点击并置灰
            }
        }

        //判断立即兑换显示哪种图片
        fun getConfirmColor(): Int {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {   //黄金会员
                ContextCompat.getColor(getApp(), R.color.colorAccent)
            } else {
                if (beanPrice * number > beanNum) {
                    ContextCompat.getColor(getApp(), R.color.mainBottomNavigation)
                } else {
                    ContextCompat.getColor(getApp(), R.color.colorAccent)
                }
            }
        }

        //判断详情页面立即兑换能否点击
        fun getIsDetailClick(): Boolean {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {   //黄金会员
                true
            } else {
                UserModel.allContributeNum.toDouble() >= (if (contributeValue.isNullOrEmpty()) "0" else contributeValue).toDouble()
                //如果当前价格大于用户剩余的糖豆则不能点击并置灰
            }
        }

        //判断详情页面立即兑换显示哪种图片
        fun getConfirmDetailColor(): Int {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {   //黄金会员
                ContextCompat.getColor(getApp(), R.color.colorAccent)
            } else {
                if (UserModel.allContributeNum.toDouble() < (if (contributeValue.isNullOrEmpty()) "0" else contributeValue).toDouble()) {
                    ContextCompat.getColor(getApp(), R.color.mainBottomNavigation)
                } else {
                    ContextCompat.getColor(getApp(), R.color.colorAccent)
                }
            }
        }

        //是否显示支付宝和微信支付,黄金会员如果零钱足够的话不需要显示，如果不够则显示；非黄金会员如果糖豆足够的话不需要显示，如果不够则显示
        fun getIsShowPay(): Boolean {
            return if (UserModel.memberLevelId == UserDetailModel.GOLD) {      //黄金会员判断零钱
                price * number + (if (storeFreightId.isNullOrEmpty()) "0" else storeFreightId).toLong() >= surplusIncome
            } else {                                                   //非黄金会员判断糖豆
                false
            }
        }

    }
}