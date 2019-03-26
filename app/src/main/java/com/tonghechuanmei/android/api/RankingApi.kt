/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-19 下午7:55
 */

package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.RankModel
import com.tonghechuanmei.android.model.UserModel
import io.reactivex.Observable

fun getRankingList(page: Int): Observable<RankModel> {
    return post("census/list.json") {
        param("page", page)
        param("rows", 10)
        param("userId", UserModel.userId)
    }
}
