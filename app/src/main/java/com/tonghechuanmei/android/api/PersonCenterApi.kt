/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-19 上午10:20
 */

package com.tonghechuanmei.android.api

import androidx.databinding.library.BuildConfig
import androidx.fragment.app.FragmentActivity
import com.designer.library.component.net.get
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.model.*
import com.tonghechuanmei.android.ui.activity.UserAuthActivity
import io.reactivex.Observable
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.startActivity

/**
 * 修改购买的活动位信息-江波-ok
 */
fun upDateAdSpaceTask(adImage: String, id: String, name: String, taskId: String): Observable<BaseModel> {
    return post("adspace/updateAdSpaceTask.json") {
        param("adImage", adImage)
        param("id", id)
        param("name", name)
        param("taskId", taskId)
    }
}

/**
 * 我的活动位状态统计-江波-ok
 */
fun getUserSpaceCateList(): Observable<SpaceCateModel> {
    return post("adspace/getUserSpaceCateList.json") {
        param("userId", UserModel.userId)
    }
}

/**
 * 根据d获取活动位i详情-江波-ok
 */
fun getSpaceInfo(spaceId: String): Observable<SpaceCateListModel> {
    return post("adspace/getSpaceinfo.json") {
        param("spaceId", spaceId)
    }
}

/**
 * 获取活动位列表-江波-ok
 */
fun getAdSpaceList(page: Int): Observable<AdSpaceListModel> {
    return get("adspace/getSpaceList.json") {
        param("page", page)
    }
}

/**
 * 选择活动（活动）-吴雪青-ok
 */
fun getChooseTaskList(page: Int): Observable<ChoiceAdvModel> {
    return get("task/chooseTask.json") {
        param("page", page)
        param("userId", UserModel.userId)
    }
}

/**
 * 购买活动位-江波-ok
 */
fun buyAdSpace(
    adImage: String, adSpaceId: String, duration: Int, endTime: String,
    name: String, payType: String, startTime: String, taskId: String
): Observable<TaskPayAgainModel> {
    return post("adspace/buyAdSpace.json") {
        param("adImage", adImage)
        param("adSpaceId", adSpaceId)
        param("duration", duration)
        param("endTime", endTime)
        param("name", name)
        param("payType", payType)
        param("startTime", startTime)
        param("taskId", taskId)
        param("userId", UserModel.userId)
    }
}

/**
 * 获取粉丝列表-吴雪青-ok
 */
fun getFansList(page: Int): Observable<FansListModel> {
    return post("user/getFansList.json") {
        param("page", page)
        param("id", UserModel.userId)
    }
}

/**
 * 用户个人信息-吴雪青-ok
 */
fun getUserDetail(): Observable<UserDetailModel> {
    return post("user/getUserDetail.json") {
        param("id", UserModel.userId)
    }
}

/**
 * 获取认证信息
 */
fun FragmentActivity.getAuth(onAuth: UserDetailModel.Data.() -> Unit) {
    post<UserDetailModel>("user/getUserDetail.json") {
        param("id", UserModel.userId)
    }.dialog(this) {
        var model = it.data
        //是否实名认证（0：未认证；1：已认证; -1:审核中；-2：审核失败）
        when (it.data.identification) {
            "0" -> {            // 未认证
                alert {
                    message = resources.getString(R.string.no_auth)
                    cancelButton {
                        dismissDialog()
                    }
                    positiveButton(resources.getString(R.string.go_to_auth)) {
                        startActivity<UserAuthActivity>("tag" to false, "confirm" to "确认提交", "isCheck" to true)
                    }
                }.show()
            }
            "1" -> {  // 已认证
                it.data.onAuth()
            }
            "-1" -> {     //审核中
                alert {
                    message = resources.getString(R.string.wait_auth)
                    cancelButton {
                        dismissDialog()
                    }
                    positiveButton(resources.getString(R.string.confirm)) {
                        dismissDialog()
                    }
                }.show()
            }
            "-2" -> {
                alert {
                    message = resources.getString(R.string.fail_auth)
                    cancelButton {
                        dismissDialog()
                    }
                    positiveButton(resources.getString(R.string.again_auth)) {
                        startActivity<UserAuthActivity>("tag" to false, "confirm" to "审核失败（继续审核）", "isCheck" to true)
                    }
                }.show()
            }
        }
    }
}


/**
 * 根据版本信息来判断是否需要认证后才允许发布/报名任务
 * @receiver FragmentActivity
 * @param onAuth UserDetailModel.Data.() -> Unit
 */
fun FragmentActivity.getAuthDynamic(onAuth: UserDetailModel.Data.() -> Unit) {

    var authEnable: Boolean = true

    post<UserAuthDynamicModel>("/getVersionInfo.json") {
        param("type", "1")
        param("versionNo", BuildConfig.VERSION_CODE)
    }.flatMap {

        authEnable = it.data.isIdcardCheck != "0"

        post<UserDetailModel>("user/getUserDetail.json") {
            param("id", UserModel.userId)
        }
    }.dialog(this) {
        if (it.msg != "-4") {
            if (!authEnable) {
                it.data.identification = "1"
            }
        }

        //是否实名认证（0：未认证；1：已认证; -1:审核中；-2：审核失败）
        when (it.data.identification) {
            "0" -> {            // 未认证
                alert {
                    message = resources.getString(R.string.no_auth)
                    cancelButton {
                        dismissDialog()
                    }
                    positiveButton(resources.getString(R.string.go_to_auth)) {
                        startActivity<UserAuthActivity>("tag" to false, "confirm" to "确认提交", "isCheck" to true)
                    }
                }.show()
            }
            "1" -> {  // 已认证
                it.data.onAuth()
            }
            "-1" -> {     //审核中
                alert {
                    message = resources.getString(R.string.wait_auth)
                    cancelButton {
                        dismissDialog()
                    }
                    positiveButton(resources.getString(R.string.confirm)) {
                        dismissDialog()
                    }
                }.show()
            }
            "-2" -> {
                alert {
                    message = resources.getString(R.string.fail_auth)
                    cancelButton {
                        dismissDialog()
                    }
                    positiveButton(resources.getString(R.string.again_auth)) {
                        startActivity<UserAuthActivity>("tag" to false, "confirm" to "审核失败（继续审核）", "isCheck" to true)
                    }
                }.show()
            }
        }

    }
}


/**
 * 我的余额记录-吴雪青-ok
 */
fun getUserIncomeList(page: Int): Observable<UserIncomeListModel> {
    return post("user/getUserIncomeList.json") {
        param("userId", UserModel.userId)
        param("page", page)
    }
}

/**
 * 我的钱包提现-兑换记录
 */
fun userIncomeList(page: Int, type: Int): Observable<UserIncomeListModel> {
    return post("myad/userIncomeList.json") {
        param("userId", UserModel.userId)
        param("type", type)
        param("page", page)
    }
}

/**
 * 用户认证-吴雪青-ok
 */
fun userMsg(
    idCard: String,
    phone: String,
    realName: String
): Observable<UserIncomeListModel> {
    return post("usermsg/model.json") {
        param("userId", UserModel.userId)
        param("idCard", idCard)
        param("phone", phone)
        param("realName", realName)
        param("state", "1")   //0：未认证；1：已认证; -1:审核中；-2：审核失败
    }
}


/**
 * 用户认证-吴雪青-ok
 */
fun userEditMsg(
    birthday: String,
    idCard: String,
    idCardBehindfrontImg: String,
    idCardFrontImg: String,
    phone: String,
    realName: String,
    sex: String,
    id: String
): Observable<UserIncomeListModel> {
    return post("usermsg/model.json") {
        param("userId", UserModel.userId)
        param("birthday", birthday)
        param("idCard", idCard)
        param("idCardBehindImg", idCardBehindfrontImg)
        param("idCardFrontImg", idCardFrontImg)
        param("phone", phone)
        param("realName", realName)
        param("sex", sex)
        param("id", id)
        param("state", "-1")   //0：未认证；1：已认证; -1:审核中；-2：审核失败
    }
}

/**
 * 获取钱包提现记录-ok-刘海龙  0.支出，1.表示收入 2表示提取 3兑换
 */
fun getComeList(page: Int, type: String): Observable<UserIncomeModel> {
    return post("userincome/userincomelist.json") {
        param("userId", UserModel.userId)
        param("type", type)
        param("page", page)
    }
}





