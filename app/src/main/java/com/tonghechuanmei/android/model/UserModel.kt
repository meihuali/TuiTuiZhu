/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/23/18 9:45 AM
 */

package com.tonghechuanmei.android.model

import androidx.core.content.edit
import com.designer.library.base.getApp
import org.jetbrains.anko.defaultSharedPreferences

object UserModel {

    var userId: String
        get() {
            return getApp().defaultSharedPreferences.getString("userId", "")!!
        }
        set(value) {
            getApp().defaultSharedPreferences.edit {
                putString("userId", value)
            }
        }

    var headImg: String
        get() {
            return getApp().defaultSharedPreferences.getString("headImg", "")!!
        }
        set(value) {
            getApp().defaultSharedPreferences.edit {
                putString("headImg", value)
            }
        }

    var nickName: String
        get() {
            return getApp().defaultSharedPreferences.getString("nickName", "")!!
        }
        set(value) {
            getApp().defaultSharedPreferences.edit {
                putString("nickName", value)
            }
        }

    fun clear() {
        getApp().defaultSharedPreferences.edit().clear().apply()
    }


    fun saveUser(user: UserDetailModel.Data) {
        userId = user.id
        headImg = user.headImg
        memberLevelId = if (user.memberId.isNullOrEmpty()) "" else user.memberId!!
        nickName = user.nickName
        allContributeNum = user.allContributeNum
    }

    var memberLevelId: String
        get() {
            return getApp().defaultSharedPreferences.getString("memberLevelId", "")!!
        }
        set(value) {
            getApp().defaultSharedPreferences.edit {
                putString("memberLevelId", value)
            }
        }

    //贡献值
    var allContributeNum: String
        get() {
            return getApp().defaultSharedPreferences.getString("allContributeNum", "0.0")!!
        }
        set(value) {
            getApp().defaultSharedPreferences.edit {
                putString("allContributeNum", value)
            }
        }

    var isFirst: Boolean
        get() {
            return getApp().defaultSharedPreferences.getBoolean("isFirst", true)
        }
        set(value) {
            getApp().defaultSharedPreferences.edit {
                putBoolean("isFirst", value)
            }
        }
}