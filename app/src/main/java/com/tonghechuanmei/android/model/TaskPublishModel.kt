package com.tonghechuanmei.android.model

/**
 * Author     : shandirong
 * Date       : 2018/11/17 16:50
 */
data class TaskPublishModel(
    var `data`: Data = Data(),
    var msg: String = "",
    var success: Boolean = false
) {
    data class Data(
        var pageInfo: PageInfo = PageInfo(),
        var resultList: List<Result> = listOf()
    ) {
        data class Result(
            var allTaskPrice: Long = 0L,
            var content: String = "",
            var contentFileId: String = "",
            var contentId: String = "",
            var createTime: Long = 0,
            var createUserId: String = "",
            var endTime: Long = 0,
            var explicitLink: String = "",
            var haveAudit: String = "",
            var haveObj: String = "",
            var haveVoucher: String = "",
            var id: String = "",
            var isNovice: String = "",
            var lat: String = "",
            var lng: String = "",
            var memberId: String = "",
            var name: String = "",
            var nickName: String = "",
            var objName: String = "",
            var objPrice: String = "",
            var reason: String? = "",
            var requirements: String = "",
            var serverAreaCityId: String = "",
            var serverAreaCountyId: String = "",
            var serverAreaProvinceId: String = "",
            var signUpAleady: String = "",
            var signUpNum: Int = 0,
            var sort: String = "",
            var startTime: Long = 0,
            var state: String = "",
            var taskCategoryId: String = "",
            var taskPrice: Long = 0L,
            var taskSignCount: Int = 0,
            var taskSignId: String = "",
            var taskSignList: List<TaskSign> = listOf(),
            var taskSignState: String = "",
            var userId: String = "",
            var userList: String = ""
        ) {

            fun getReasonVisible(): Boolean {
                return state == "-2"
            }

            fun getReasonStr(): String {
                if (reason.isNullOrEmpty()) {
                    reason = "无"
                }
                return "原因: $reason"
            }

            fun getTaskSignCountStr(): String {
                return "已报名 $taskSignCount 人"
            }

            fun getApplyPersonCount(): String {
                return "$signUpNum 人"
            }

            fun getStateStr(): String {
                //state：-1新建，未支付 1已支付带审核 2已经审核 3已经完成 -2 审核不通过 -3退款 -4退款成功 ,-5已下架,
                return when (state) {
                    "-2" -> "未通过"
                    "-1" -> "待支付"
                    "1" -> "报名中"
                    "2" -> "报名中"
                    "-5" -> "已结束"
                    else -> {
                        ""
                    }
                }
            }

            fun getBtnStr(): String {
                //state：-1新建，未支付 1已支付带审核 2已经审核 3已经完成 -2 审核不通过 -3退款 -4退款成功 ,-5已下架,
                return when (state) {
                    "-2", "-1" -> "取消发布"
                    "1", "2", "-5" -> "复制"
                    else -> {
                        ""
                    }
                }
            }

            fun getSureBtnStr(): String {
                //state：-1新建，未支付 1已支付带审核 2已经审核 3已经完成 -2 审核不通过 -3退款 -4退款成功 ,-5已下架,
                return when (state) {
                    "-2" -> "重新发布"
                    "-1" -> "立即支付"
                    "1" -> "审核活动"
                    "2" -> "审核活动"
                    "-5" -> "删除活动"
                    else -> {
                        ""
                    }
                }
            }

            fun showSureBtn(): Boolean {
                when (state) {
                    "-2" -> {     // "重新发布"
                        return true
                    }
                    "-1" -> {       // "立即支付"
                        return true
                    }
                    "1", "2" -> {       //"审核活动"
                        return haveAudit == "1"
                    }
                    "-5" -> {     //"删除活动"
                        return true
                    }
                }
                return false
            }

            fun showPeopleLayout(): Boolean {
                return state == "1" || state == "2" || state == "-5"
            }

            data class TaskSign(
                var createTime: Long = 0,
                var haveAudit: String = "",
                var haveObj: String = "",
                var haveVoucher: String = "",
                var headImg: String = "",
                var id: String = "",
                var reason: String = "",
                var remark: String = "",
                var signUpNum: String = "",
                var state: String = "",
                var subContent: String = "",
                var subFiles: String = "",
                var taskId: String = "",
                var taskName: String = "",
                var taskPrice: String = "",
                var userId: String = "",
                var userName: String = ""
            )
        }

        data class PageInfo(
            var allcount: Int = 0,
            var allpage: Int = 0,
            var contentFileId: String = "",
            var contentId: String = "",
            var createTime: String = "",
            var createUserId: String = "",
            var currentUserId: String = "",
            var endTime: String = "",
            var explicitLink: String = "",
            var haveObj: String = "",
            var id: String = "",
            var isNovice: String = "",
            var lat: String = "",
            var lng: String = "",
            var name: String = "",
            var nowTime: String = "",
            var objName: String = "",
            var objPrice: String = "",
            var orderby: String = "",
            var page: Int = 0,
            var payAdFee: String = "",
            var payPrice: String = "",
            var reason: String = "",
            var requirements: String = "",
            var rows: Int = 0,
            var serverAreaCityId: String = "",
            var serverAreaCountyId: String = "",
            var serverAreaProvinceId: String = "",
            var signUpAleady: String = "",
            var signUpNum: String = "",
            var sort: String = "",
            var start: Int = 0,
            var startTime: String = "",
            var state: String = "",
            var states: List<String> = listOf(),
            var taskCategoryId: String = "",
            var taskPrice: String = "",
            var userId: String = ""
        )
    }
}