package com.tonghechuanmei.android.ui.activity.task

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.designer.library.component.databinding.inflate
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.grid
import com.designer.library.component.recycler.setup
import com.designer.library.utils.permission
import com.hwangjr.rxbus.RxBus
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.submitTask
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivitySubmitTaskBinding
import com.tonghechuanmei.android.databinding.ItemAddPictureBinding
import com.tonghechuanmei.android.model.TaskDetailModel
import com.tonghechuanmei.android.model.UpdateFileModel
import com.yanzhenjie.permission.Permission
import io.reactivex.Observable
import io.reactivex.rxkotlin.combineLatest
import kotlinx.android.synthetic.main.activity_submit_task.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.toast
import java.io.File

class SubmitTaskActivity : BaseToolbarActivity<ActivitySubmitTaskBinding>() {

    private lateinit var itemAddPictureBinding: ItemAddPictureBinding
    private var mediaList = mutableListOf<String>()
    private lateinit var data: TaskDetailModel.Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_task)
    }

    override fun initView() {
        title = "提交活动"

        recycler.grid(3).setup {
            itemAddPictureBinding = recycler.inflate(R.layout.item_add_picture)
            itemAddPictureBinding.v = this@SubmitTaskActivity
            addFooter(itemAddPictureBinding.root)
            addType<String>(R.layout.item_add_picture)
            addClickable(R.id.iv_preview_picture, R.id.iv_delete)
            onClick {
                when (it) {
                    R.id.iv_delete -> {
                        mediaList.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                        if (mediaList.size < 8) {
                            recycler.baseAdapter.addFooter(itemAddPictureBinding.root)
                        }
                    }
                    R.id.iv_preview_picture -> {
                        previewPicture(adapterPosition)
                    }
                }
            }
        }
    }

    private fun previewPicture(position: Int) {
        val temp = mutableListOf<LocalMedia>()
        mediaList.forEach {
            temp.add(LocalMedia().apply { path = it })
        }
        PictureSelector.create(this).themeStyle(R.style.picture_default_style)
            .openExternalPreview(position, temp)
    }

    override fun initData() {
        data = intent.getSerializableExtra("data") as TaskDetailModel.Data
        binding.v = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_add_picture -> {
                permission(Permission.WRITE_EXTERNAL_STORAGE) {
                    openPhoto()
                }
            }
            R.id.btn -> {
                submit()
            }
        }
    }

    private fun submit() {

        when {
            data.haveObj == "1" && et_description.text.toString().isEmpty() -> {
                toast("请填写信息")
            }
            data.haveVoucher == "1" && mediaList.isEmpty() -> {
                toast("请上传审核凭证图片")
            }
            else -> {
                if (mediaList.size > 0) {
                    val updateObservable = mutableListOf<Observable<UpdateFileModel>>()

                    mediaList.forEach {
                        val temp = post<UpdateFileModel>("upload/uploadindex.html") {
                            file("file", File(it))
                        }
                        updateObservable.add(temp)
                    }

                    lateinit var files: String
                    updateObservable.combineLatest {
                        val stringBuilder = StringBuilder()
                        it.forEachWithIndex { i, fileModel ->
                            if (i < it.size - 1) {
                                stringBuilder.append("${it[i].data},")
                            } else {
                                stringBuilder.append(it[i].data)
                            }
                        }
                        files = stringBuilder.toString()
                    }.flatMap {
                        submitTask(data.taskSignId ?: "", et_description.text.toString(), files)
                    }.dialog(this) {
                        RxBus.get().post("refresh_task_lobby_event", "刷新任务大厅")
                        toast("提交成功")
                        finish()
                    }
                } else {
                    submitTask(data.taskSignId ?: "", et_description.text.toString()).dialog(this) {
                        RxBus.get().post("refresh_task_lobby_event", "刷新任务大厅")
                        toast("提交成功")
                        finish()
                    }
                }

            }
        }
    }

    private fun openPhoto() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(8 - mediaList.size)
            .isCamera(true)
            .compress(true)
            .minimumCompressSize(200)
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                val temp = PictureSelector.obtainMultipleResult(data)
                temp.forEach {
                    mediaList.add(it.compressPath ?: it.path)
                }
                if (mediaList.size == 8) {
                    recycler.baseAdapter.removeFooter(itemAddPictureBinding.root)
                }
                recycler.baseAdapter.models = mediaList
            }
        }
    }
}
