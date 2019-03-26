/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 上午10:46
 */

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.designer.library.component.databinding.loadImage
import com.designer.library.component.net.observer.dialog
import com.designer.library.utils.TimeUtils
import com.designer.library.utils.YearMonthDayTimePicker
import com.designer.library.utils.permission
import com.designer.library.widget.PriceUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType.ofImage
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getSpaceInfo
import com.tonghechuanmei.android.api.upLoad
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityAdvertisingBuyBinding
import com.tonghechuanmei.android.model.AdvertisingBuyModel
import com.tonghechuanmei.android.model.SpaceCateListModel
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_advertising_buy.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.io.File


/**
 * 购买活动位
 */
class AdvertisingBuyActivity : BaseToolbarActivity<ActivityAdvertisingBuyBinding>() {
    var taskId = ""
    var imgUrl = ""
    lateinit var model: SpaceCateListModel.Data
    override fun initView() {
        title = "广告位"
        binding.v = this
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        getSpaceInfo(intent.getStringExtra("id")).dialog(this) {
            adv_buy_title.text = it.data.name
            model = it.data
            adv_buy_price.text = "¥${PriceUtils.getPrice(model.adSpacePrice)}"
            val params = adv_cl.layoutParams
            params.width = dip(170)
            params.height = dip(((170 / it.data.width.toDouble()) * it.data.height.toInt()).toInt())
            adv_cl.layoutParams = params
            add_image_hint.text = "点击添加${it.data.width}*${it.data.height}的图片"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertising_buy)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.adv_buy_right_tv4 -> {
                val picker = YearMonthDayTimePicker()
                picker.showYearMonthDayTimePicker(this)
                picker.onYearMonthDayTime {
                    if (adv_buy_right_tv5.text.toString().isEmpty()) {
                        adv_buy_right_tv4.text = it
                    } else {              //结束时间不为空时需要判断开始时间不能大于或等于结束时间
                        val endTime = TimeUtils.getHourLong(adv_buy_right_tv5.text.toString())
                        val startTime = TimeUtils.getHourLong(it)
                        if (endTime <= startTime) {
                            toast("开始时间不能大于或等于结束时间")
                        } else {
                            adv_buy_all_time_tv.text = TimeUtils.getHourNumber(endTime - startTime).toString()
                            adv_buy_right_tv4.text = it
                        }
                    }
                }
            }
            R.id.adv_buy_right_tv5 -> {
                val picker = YearMonthDayTimePicker()
                picker.showYearMonthDayTimePicker(this)
                picker.onYearMonthDayTime {
                    if (adv_buy_right_tv4.text.toString().isEmpty()) {
                        adv_buy_right_tv5.text = it
                    } else {              //开始时间不为空时需要判断结束时间必须大于开始时间
                        val endTime = TimeUtils.getHourLong(it)
                        val startTime = TimeUtils.getHourLong(adv_buy_right_tv4.text.toString())
                        if (endTime <= startTime) {
                            toast("结束时间不能小于或等于开始时间")
                        } else {
                            adv_buy_all_time_tv.text = TimeUtils.getHourNumber(endTime - startTime).toString()
                            adv_buy_right_tv5.text = it
                        }
                    }
                }
            }
            R.id.adv_buy_choice -> {
                startActivityForResult<ChoiceAdvertisingActivity>(1)
            }
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
                        val m = AdvertisingBuyModel()
                        m.adImage = imgUrl
                        m.adSpaceId = model.id
                        m.amount = model.adSpacePrice * adv_buy_all_time_tv.text.toString().toLong()
                        m.duration = adv_buy_all_time_tv.text.toString()
                        m.endTime = adv_buy_right_tv5.text.toString() + ":00"
                        m.name = adv_buy_et6.text.toString()
                        m.payPrice = model.adSpacePrice * adv_buy_all_time_tv.text.toString().toLong()
                        m.startTime = adv_buy_right_tv4.text.toString() + ":00"
                        m.taskId = taskId
                        m.location = adv_buy_title.text.toString()
                        startActivity<AdvPayActivity>("m" to m)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                1 -> {
                    taskId = data.getStringExtra("id")
                    val name = data.getStringExtra("name")
                    adv_buy_choice.text = name
                }
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
