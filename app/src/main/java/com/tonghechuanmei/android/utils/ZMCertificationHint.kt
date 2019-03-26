package com.tonghechuanmei.android.utils

import com.zmxy.ZMCertification


fun getZMCertification(errorCode: Int): String {
    return when (errorCode) {
        ZMCertification.verification_failed -> "用户人脸与数据库中的人脸比对分数较低"
        ZMCertification.device_not_support -> "手机在不支持列表里"
        ZMCertification.no_permission -> "缺少手机权限"
        ZMCertification.no_network_permission -> "没有联网权限"
        ZMCertification.no_camera_permission -> "没有打开相机的权限"
        ZMCertification.no_sensor_permission -> "无法读取运动数据的权限"
        ZMCertification.face_init_fail -> "人脸采集算法初始化失败"
        ZMCertification.network_error -> "发生网络错误"
        ZMCertification.invalid_biz_no -> "传入的bizNO有误"
        ZMCertification.invalid_bundle_id -> "此APP的bundle_id在系统的黑名单库里"
        ZMCertification.data_source_error -> "数据源错误"
        ZMCertification.internal_error -> "服务发生内部错误"
        ZMCertification.unmatched_merchant_id -> "bizNO和merchantID不匹配"
        ZMCertification.version_too_old -> "SDK版本过旧"
        ZMCertification.userinfo_error -> "身份证号和姓名的格式不正确"
        ZMCertification.bizno_limit_exceed -> "身份证号和姓名在一天内使用次数过多"
        ZMCertification.wrong_system_time -> "用户当前的设备时间与授权时间不符"
        ZMCertification.no_write_external_storage_permission -> "没有写存储空间的权限"
        ZMCertification.upload_image_error -> "商户上传的底库图像出现问题"
        ZMCertification.image_error_unsupported_format -> "用户在公安部门的证件照片不够清晰"
        else -> "无"
    }
}
