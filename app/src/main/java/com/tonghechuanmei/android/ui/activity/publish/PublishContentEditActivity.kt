/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/20/18 8:59 PM
 */

package com.tonghechuanmei.android.ui.activity.publish

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.designer.library.component.databinding.inflate
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.grid
import com.designer.library.component.recycler.setup
import com.designer.library.utils.permission
import com.hwangjr.rxbus.annotation.Subscribe
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPublishContentEditBinding
import com.tonghechuanmei.android.databinding.ItemAddPictureBinding
import com.tonghechuanmei.android.model.PublishTaskModel
import com.tonghechuanmei.android.model.SearchAddressModel
import com.tonghechuanmei.android.ui.activity.map.SelectAddressActivity
import com.tonghechuanmei.android.utils.previewMedia
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_publish_content_edit.*
import org.jetbrains.anko.*

class PublishContentEditActivity : BaseToolbarActivity<ActivityPublishContentEditBinding>() {

    private lateinit var itemAddPictureBinding: ItemAddPictureBinding
    private lateinit var model: PublishTaskModel

    override fun initView() {
        title = "活动内容"
        binding.v = this
        swipeEnable = false


        et_task_content.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            scroll.requestDisallowInterceptTouchEvent(true)
            false
        }

        rv_image.grid(3).setup {
            itemAddPictureBinding = rv_image.inflate(R.layout.item_add_picture)
            itemAddPictureBinding.v = this@PublishContentEditActivity
            addFooter(itemAddPictureBinding.root)
            addType<String>(R.layout.item_add_picture)
            addClickable(R.id.iv_preview_picture, R.id.iv_delete)
            onClick {
                when (it) {
                    R.id.iv_delete -> {
                        model.mediaList.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        if (model.mediaList.size < 8) {
                            rv_image.baseAdapter.addFooter(itemAddPictureBinding.root)
                        }
                    }
                    R.id.iv_preview_picture -> {
                        previewMedia(adapterPosition, model.mediaList)
                    }
                }
            }
        }
    }

    override fun initData() {
        model = intent.getSerializableExtra("model") as PublishTaskModel

        // 复写多媒体
        if (model.mediaList.isNotEmpty()) {
            rv_image.baseAdapter.models = model.mediaList
        }

        binding.m = model
    }

    private fun addMedia() {
        if (this::model.isInitialized) {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(8 - model.mediaList.size)
                    .minSelectNum(1)
                    .isCamera(true)
                    .compress(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_address -> {
                startActivityForResult<SelectAddressActivity>(200)
            }
            R.id.fl_advance_option -> {
                startActivity<PublishTaskAdvanceOptionActivity>("model" to model)
            }
            R.id.btn_submit -> {
                when {
                    model.content.isEmpty() -> toast("请描述发布活动的文字内容")
                    else -> startActivity<PublishTaskPayActivity>("model" to model)
                }
            }
            R.id.ll_add_picture -> {
                permission(Permission.WRITE_EXTERNAL_STORAGE) {
                    addMedia()
                }
            }
        }
    }


    override fun close() {
        discard()
    }

    override fun onBackPressed() {
        discard()
    }

    private fun discard() {
        alert {
            message = "是否丢弃当前编辑内容"
            cancelButton {
                it.dismiss()
            }
            okButton {
                finishTransition()
            }
        }.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        data?.let { _ ->
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                val mediaList = PictureSelector.obtainMultipleResult(data) as ArrayList<LocalMedia>

                mediaList.forEach { it ->
                    model.mediaList.add(it.compressPath ?: it.path)
                }

                if (model.mediaList.size == 8) {
                    rv_image.baseAdapter.removeFooter(itemAddPictureBinding.root)
                }

                rv_image.baseAdapter.models = model.mediaList

            } else if (resultCode == Activity.RESULT_OK) {
                val poiItem = data.getParcelableExtra("model") as SearchAddressModel
                model.setPoi(poiItem)
            }
        }
    }

    @Subscribe
    fun subscribe(event: PublishTaskModel) {
        model = event
        binding.m = model
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_content_edit)
    }
}
