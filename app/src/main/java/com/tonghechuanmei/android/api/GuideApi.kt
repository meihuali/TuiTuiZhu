package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.NewbieGuideBannerModel
import com.tonghechuanmei.android.model.NewbieGuideQuestionModel
import io.reactivex.Observable

/**
 * Author     : shandirong
 * Date       : 2018/11/19 18:45
 */

fun getNewbieGuideBanner(): Observable<NewbieGuideBannerModel> {
    return post("noviceguidebanner/list.json") {
    }
}

fun getNewbieGuideQuestion(): Observable<NewbieGuideQuestionModel> {
    return post("questionandanswer/list.json") {
        param("rows", 9999)
    }
}


