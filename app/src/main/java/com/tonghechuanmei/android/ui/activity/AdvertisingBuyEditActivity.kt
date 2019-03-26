/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig
 * Author：马靖乘
 * Date：18-11-25 下午10:04
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.designer.library.component.databinding.loadImage
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.designer.library.utils.DateUtils
import com.designer.library.utils.permission
import com.designer.library.widget.PriceUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType.ofImage
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.upLoad
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityAdvertisingBuyEditBinding
import com.tonghechuanmei.android.model.BaseModel
import com.tonghechuanmei.android.model.SpaceTaskInfoModel
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_advertising_buy_edit.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import java.io.File


/**
 * 购买活动位回显数据
 */
class AdvertisingBuyEditActivity : BaseToolbarActivity<ActivityAdvertisingBuyEditBinding>() {
    var taskId = ""
    var imgUrl = ""
    lateinit var model: SpaceTaskInfoModel.Data
    override fun initView() {
        title = "活动位编辑"
        binding.v = this
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        post<SpaceTaskInfoModel>("adspace/getSpaceTaskinfo.json") {
            param("spaceTaskId", intent.getStringExtra("spaceTaskId"))
        }.dialog(this) {
            model = it.data
            adv_buy_title.text = model.spaceName
            adv_buy_price.text = "¥${PriceUtils.getPrice(model.amount / model.duration.toInt())}"
            adv_buy_right_tv4.text = DateUtils.formatDate(model.startTime, "yyyy-MM-dd HH:mm")
            adv_buy_right_tv5.text = DateUtils.formatDate(model.endTime, "yyyy-MM-dd HH:mm")
            adv_buy_all_time_tv.text = model.duration
            adv_buy_et6.setText(model.name)
            adv_buy_choice.text = model.taskName
            taskId = model.taskId
            image.loadImage(model.adImage)
            imgUrl = model.adImage
            val params = adv_cl.layoutParams
            params.width = dip(170)
            params.height = dip(((170 / model.width.toDouble()) * model.height.toInt()).toInt())
            adv_cl.layoutParams = params
            add_image_hint.text = "点击添加${model.width}*${model.height}的图片"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertising_buy_edit)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.adv_cl -> {
                permission(Permission.WRITE_EXTERNAL_STORAGE) {
                    PictureSelector.create(this)
                        .openGallery(ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageSpanCount(4)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .enableCrop(true)// 是否裁剪 true or false
                        .compress(true)// 是否压缩 true or false
                        .cropCompressQuality(70)// 裁剪压缩质量 默认90 int
                        .withAspectRatio(model.width.toInt(), model.height.toInt())// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        .cropWH(model.width.toInt(), model.height.toInt())// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                        .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                        .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                        .isDragFrame(false)// 是否可拖动裁剪框(固定)
                        .forResult(PictureConfig.CHOOSE_REQUEST)
                }
            }
            R.id.adv_buy_confirm -> {
                when {
                    adv_buy_right_tv4.text.toString().isEmpty() -> toast("开始时间不能为空")
                    adv_buy_right_tv5.text.toString().isEmpty() -> toast("结束时间不能为空")
                    adv_buy_et6.text.toString().isEmpty() -> toast("活动标题不能为空")
                    adv_buy_choice.text.toString().isEmpty() -> toast("请选择活动")
                    adv_buy_choice.text.toString().isEmpty() -> toast("请选择活动")
                    imgUrl.isEmpty() -> toast("请选择图片")
                    else -> {
                        /*   val m = AdvertisingBuyModel()
                           m.adImage = imgUrl
                           m.adSpaceId = model.id
                           m.amount = model.amount.toLong()
                           m.duration = adv_buy_all_time_tv.text.toString()
                           m.endTime = adv_buy_right_tv5.text.toString() + ":00"
                           m.name = adv_buy_et6.text.toString()
                           m.payPrice = model.amount.toLong()
                           m.startTime = adv_buy_right_tv4.text.toString() + ":00"
                           m.taskId = taskId
                           m.location = adv_buy_title.text.toString()*/
                        post<BaseModel>("adspace/updateAdSpaceTask.json") {
                            param("adImage", imgUrl)
                            param("id", intent.getStringExtra("spaceTaskId"))
                            param("name", adv_buy_et6.text.toString())
                            param("taskId", intent.getStringExtra("taskId"))
                        }.dialog(this) {
                            toast("修改成功")
                            finish()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    upLoad(File(selectList[0].compressPath)).dialog(this) {
                        image.loadImage(it.data)
                        imgUrl = it.data
                    }
                }
                else -> {
                }
            }
        }
    }
}
