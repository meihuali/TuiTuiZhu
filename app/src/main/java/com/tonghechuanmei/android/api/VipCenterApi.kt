/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-22 下午6:02
 */

package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.UserMemberListModel
import com.tonghechuanmei.android.model.UserModel
import io.reactivex.Observable

/**
 * 会员等级列表 -ok- 刘海龙
 */
fun userMemberList(): Observable<UserMemberListModel> {
    return post("usermember/usermemberlist.json") {
        param("userId", UserModel.userId)
    }
}