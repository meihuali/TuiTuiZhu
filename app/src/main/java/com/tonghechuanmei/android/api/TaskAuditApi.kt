package com.tonghechuanmei.android.api

import com.designer.library.component.net.post
import com.tonghechuanmei.android.model.*
import io.reactivex.Observable

/**
 * Author     : shandirong
 * Date       : 2018/11/20 16:04
 */

/**
 * 我的活动-获取我的发布总条数及我的报名总条数
 */
fun getTaskStatistics(): Observable<TaskStatisticsModel> {
    return post("task/getTaskCount.json") {
        param("userId", UserModel.userId)
    }
}

/**
 * 我的发布列表-获取各状态统计信息-吴雪青
 * 审核列表状态值，审核通过未通过
 */
fun getTaskStatistics(id: String): Observable<TaskStateStatisticsModel> {
    return post("task/getTaskStateCount.json") {
        param("id", id)
    }
}

/**
 * 我的发布-活动审核列表
 */
fun getTaskAuditList(page: Int): Observable<TaskPublishModel> {
    return post("task/myissuetasklist.json") {
        param("userId", UserModel.userId)
        param("page", page)
        param("states", "-1,-2,1,2,-5")
    }
}

/**
 * 我的报名-报名状态统计接口
 */
fun getApplyStateStatistics(): Observable<ApplyStateStatisticsModel> {
    return post("tasksign/getTaskSignStateCount.json") {
        param("userId", UserModel.userId)
    }
}

/**
 * 我的报名-我的报名列表
 */
fun getMyApplyList(state: String, page: Int): Observable<ApplyStateListModel> {
    return post("tasksign/getTaskSignList.json") {
        param("state", state)
        param("page", page)
        param("userId", UserModel.userId)
    }
}

/**
 * 活动审核列表
 */
fun getApplyStateList(id: String, state: String, page: Int): Observable<ApplyStateListModel> {
    return post("tasksign/getSignList.json") {
        param("taskId", id)
        param("state", state)
        param("page", page)
    }
}

/**
 * 报名中删除活动
 */
fun deleteTaskSign(id: String): Observable<BaseModel> {
    return post("tasksign/delete.json") {
        param("id", id)
    }
}

/**
 * 发布中删除活动
 */
fun deleteTask(id: String): Observable<BaseModel> {
    return post("task/delete.json") {
        param("id", id)
    }
}

/**
 * 取消发布
 */
fun cancelPublish(id: String): Observable<BaseModel> {
    return post("task/cancelTask.json") {
        param("id", id)
    }
}

/**
 * 放弃活动
 */
fun cancelTask(id: String): Observable<BaseModel> {
    return post("task/giveUpTaskSign.json") {
        param("signTaskId", id)
        param("userId", UserModel.userId)
    }
}

/**
 * 提交单号
 */
fun submitTaskNumber(taskSignId: String, remark: String): Observable<BaseModel> {
    return post("tasksign/submitSignTaskWaybill.json") {
        param("taskSignId", taskSignId)
        param("remark", remark)
    }
}

/**
 * 通过活动
 */
fun passTask(id: String): Observable<BaseModel> {
    return post("tasksign/auditSignTaskPass.json") {
        param("taskSignIds", id)
    }
}

/**
 * 不通过活动
 */
fun unPassTask(id: String, reason: String): Observable<BaseModel> {
    return post("tasksign/taskNotPass.json") {
        param("taskSignIds", id)
        param("reason", reason)
    }
}

/**
 * 重新支付
 */
fun taskPayAgain(taskId: String): Observable<PinPayModel> {
    return post<TaskPayAgainModel>("task/payTaskAgain.json") {
        param("taskId", taskId)
    }.flatMap { model ->
        val payNo = model.data.payNo
        val amount = model.data.payPrice
        post<PinPayModel>("ping/pingpay.json") {
            param("orderNo", payNo)
            param("fromType", "app")
            param("amount", amount)
        }
    }
}

/**
 * 活动位继续支付
 */
fun advertisingPayAgain(id: String): Observable<PinPayModel> {
    return post<TaskPayAgainModel>("adspace/payAdSpaceAgain.json") {
        param("id", id)
    }.flatMap { model ->
        val payNo = model.data.payNo
        val amount = model.data.payPrice
        post<PinPayModel>("ping/pingpay.json") {
            param("orderNo", payNo)
            param("fromType", "app")
            param("amount", amount)
        }
    }
}

/**
 * 报名活动
 */
fun applyTask(taskId: String): Observable<BaseModel> {
    return post("task/signTask.json") {
        param("taskId", taskId)
        param("userId", UserModel.userId)
    }
}

/**
 * 提交活动
 */
fun submitTask(taskId: String, subContent: String = "", subFiles: String = ""): Observable<BaseModel> {
    return post("task/submitSignTask.json") {
        param("id", taskId)
        param("subContent", subContent)
        param("subFiles", subFiles)
    }
}

/**
 * 导出
 */
fun applyEmail(email: String, taskId: String): Observable<BaseModel> {
    return post("tasksign/export.json") {
        param("email", email)
        param("taskId", taskId)
    }
}
