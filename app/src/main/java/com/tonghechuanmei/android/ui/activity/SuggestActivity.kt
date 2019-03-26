/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午5:37
 */

package com.tonghechuanmei.android.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.designer.library.utils.permission
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivitySuggestBinding
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_suggest.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SuggestActivity : BaseToolbarActivity<ActivitySuggestBinding>() {
    override fun initView() {
        title = "建议反馈"
        binding.v = this
    }

    private fun showAlert(bitmap: Bitmap) {
        alert {
            message = "保存到手机"
            cancelButton {
                //   dismissDialog()
            }
            positiveButton("确定") {
                saveBitmap(bitmap)
            }
        }.show()
    }

    /**
     * 保存bitmap到本地
     */
    private fun saveBitmap(bitmap: Bitmap) {
        permission(Permission.WRITE_EXTERNAL_STORAGE) {
            val filePic: File
            try {
                filePic =
                        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + "/建议反馈_推推猪" + ".jpg")
                if (!filePic.exists()) {
                    filePic.parentFile.mkdirs()
                    filePic.createNewFile()
                }
                val fos = FileOutputStream(filePic)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                val contentUri = Uri.fromFile(filePic)
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri)
                sendBroadcast(mediaScanIntent)
                toast("保存成功")
            } catch (e: IOException) {
                e.printStackTrace()
                return@permission
            }
        }
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggest)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.suggest_iv -> {
                val obmp = (suggest_iv.drawable as BitmapDrawable).bitmap
                val width = obmp.width
                val height = obmp.height
                val data = IntArray(width * height)
                obmp.getPixels(data, 0, width, 0, 0, width, height)
                showAlert(obmp)
            }
            R.id.tv_suggest_phone -> {
                val intent = Intent(Intent.ACTION_DIAL)
                val data = Uri.parse("tel:" + tv_suggest_phone.text.toString().replace("联系电话：", ""))
                intent.data = data
                startActivity(intent)
            }
        }
    }
}
