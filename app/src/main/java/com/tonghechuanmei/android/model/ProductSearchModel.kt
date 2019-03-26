/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午3:08
 */

package com.tonghechuanmei.android.model

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.designer.library.base.getApp
import com.designer.library.widget.PriceUtils
import com.tonghechuanmei.android.R

data class ProductSearchModel(
    val `data`: Data,
    val msg: String,
    val success: Boolean
) {
    data class Data(
        val pageInfo: PageInfo,
        val resultList: List<Result>
    ) {
        data class Result(
            val activityCode: Any,
            val activityId: Any,
            val activityType: String,
            val articleDetail: Any,
            val beanPrice: Long,
            val brandId: Any,
            val brandName: Any,
            val categoryAreaId: String,
            val categoryId: String,
            val categoryName: Any,
            val categoryWapName: Any,
            val collectNum: Int,
            val commentCount: Int,
            val commentNegativeCount: Int,
            val commentNo: Int,
            val commentPicCount: Int,
            val commission: Any,
            val commissionRate: String,
            val contributeValue: String,
            val costPrice: Int,
            val createTime: Long,
            val delState: String,
            val detail: Any,
            val firstCategoryId: String,
            val firstCategoryName: Any,
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
            val photoAlbum: Any,
            val price: Long,
            val productImage: String,
            val productImageDatail: Any,
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
            val storeFreightId: String,
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
            val weight: String
        ) {
            fun getPayPrice(): String {
                return if (UserModel.memberLevelId == UserDetailModel.GOLD) {  //黄金显示金额
                    "¥${PriceUtils.getPrice(price!!)}"
                } else {                                                 //青铜或白银显示糖豆
                    "${beanPrice}糖豆"
                }
            }

            fun getPayMarkPrice(): String {
                return if (UserModel.memberLevelId == UserDetailModel.GOLD) {  //黄金显示金额
                    "¥${PriceUtils.getPrice(marketPrice!!)}"
                } else {                                                 //青铜或白银显示糖豆
                    if (contributeValue.isNullOrEmpty()) {
                        ("(贡献值≥0)")
                    } else {
                        ("(贡献值≥$contributeValue)")
                    }
                }
            }

            fun getIsAddMiddlerLine(): Boolean {
                return UserModel.memberLevelId == UserDetailModel.GOLD
            }

            //判断立即兑换显示哪种图片
            fun getConfirmBg(): Drawable {
                return if (inventory > 0) {
                    ContextCompat.getDrawable(getApp(), R.drawable.icon_zt_ljgm)!!
                } else {
                    ContextCompat.getDrawable(getApp(), R.drawable.icon_zt_gray_ljgm)!!
                }
            }

            fun getConfirmTv(): String {
                return if (inventory > 0) {
                    "立即兑换"
                } else {
                    "已售罄"
                }
            }
        }

        data class PageInfo(
            val activityId: Any,
            val activityType: Any,
            val allcount: Int,
            val allpage: Int,
            val brandId: Any,
            val categoryAreaId: Any,
            val categoryId: Any,
            val categoryIds: List<Any>,
            val collectNum: Any,
            val commentCount: Any,
            val commentNegativeCount: Any,
            val commentNo: Any,
            val commentPicCount: Any,
            val commission: Any,
            val commissionRate: Any,
            val costPrice: Any,
            val createTime: Any,
            val dateType: Any,
            val delState: Any,
            val detail: Any,
            val firstCategoryId: Any,
            val grade: Any,
            val gradeNum: Any,
            val id: Any,
            val integral: Any,
            val intro: Any,
            val inventory: Any,
            val isBoutiquePinkage: Any,
            val isChoiceness: Any,
            val isGuess: Any,
            val isHome: Any,
            val isHot: Any,
            val isHotGrade: Any,
            val isNew: Any,
            val isNewStrange: Any,
            val isOnlyIntegralPay: Any,
            val isPinkage: Any,
            val isProperty: Any,
            val isRecommend: Any,
            val isStandards: Any,
            val keyword: Any,
            val marketPrice: Long,
            val maxprice: Any,
            val minprice: Any,
            val modelId: Any,
            val name: Any,
            val orderby: String,
            val page: Int,
            val photoAlbum: Any,
            val price: Any,
            val productIds: List<Any>,
            val productImage: Any,
            val productImageDatail: Any,
            val productNo: Any,
            val qkeyword: Any,
            val recommendImage: Any,
            val recommendImageHeight: Any,
            val recommendImageWidth: Any,
            val remark: Any,
            val rows: Int,
            val salesMonthNum: Any,
            val salesNum: Any,
            val salesQuota: Any,
            val searchWord: Any,
            val secondCategoryId: Any,
            val sellerName: Any,
            val sku: Any,
            val sort: Any,
            val start: Int,
            val state: String,
            val stateList: List<Any>,
            val statisticsDate: Any,
            val statisticsDateEnd: Any,
            val statisticsDateStart: Any,
            val statisticsMonth: Any,
            val storeCategoryId: Any,
            val storeFreightId: Any,
            val storeId: Any,
            val storeIds: Any,
            val storeName: Any,
            val storeNavigationId: Any,
            val suppliersId: Any,
            val updateTime: Any,
            val weight: Any
        )
    }
}