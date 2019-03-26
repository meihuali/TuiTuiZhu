/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/3/18 5:18 PM
 */

package com.tonghechuanmei.android.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.designer.library.component.databinding.loadCircular
import com.designer.library.component.net.Net
import com.designer.library.component.net.observer.state
import com.designer.library.utils.ScreenUtils
import com.designer.library.utils.permission
import com.king.zxing.util.CodeUtils
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityInviteFriendBinding
import com.tonghechuanmei.android.model.UserModel
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_invite_friend.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream


class InviteFriendActivity : BaseToolbarActivity<ActivityInviteFriendBinding>() {
    override fun initView() {
        title = "邀请好友"
        binding.v = this
    }

    override fun initData() {
        getUserDetail().state(container) {
            iv_user_portrait.loadCircular(it.data.headImg)
            val qrCode = CodeUtils.createQRCode("${Net.HOST}share?referrerUserId=${UserModel.userId}&taskId= ", 400)
            iv_qr_code.setImageBitmap(qrCode)
        }
    }

    override fun onClick(v: View) {
        permission(Permission.WRITE_EXTERNAL_STORAGE) {
            val screenShot = ScreenUtils.screenShot(this)
            try {
                val file =
                    File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + "/邀请好友二维码_推推猪" + ".jpg")
                val out = FileOutputStream(file)
                screenShot.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                val contentUri = Uri.fromFile(file)
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri)
                sendBroadcast(mediaScanIntent)

                toast("已保存二维码到图库")
            } catch (e: Exception) {
                toast("保存失败")
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friend)
    }
}
