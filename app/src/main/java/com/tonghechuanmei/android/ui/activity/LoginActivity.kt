package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.designer.library.base.BaseActivity
import com.designer.library.component.net.observer.dialog
import com.designer.library.utils.darkMode
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.login
import com.tonghechuanmei.android.databinding.ActivityLoginBinding
import com.tonghechuanmei.android.model.UserModel
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Author     : shandirong
 * Date       : 2018/11/23 16:22
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private var wechatId = ""
    private var unionid = ""
    private var headImage = ""
    private var nickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        darkMode()
        binding.v = this
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.next_btn -> {
                val shareApi = UMShareAPI.get(this)
                shareApi.doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener)
            }
            R.id.content -> {
                startActivity<AgreementActivity>()
            }
        }
    }

    override fun initView() {

    }

    override fun initData() {

    }

    private var authListener: UMAuthListener = object : UMAuthListener {

        override fun onStart(platform: SHARE_MEDIA) {

        }

        override fun onComplete(platform: SHARE_MEDIA, action: Int, data: Map<String, String>) {
            UMShareAPI.get(this@LoginActivity)
                .getPlatformInfo(this@LoginActivity, SHARE_MEDIA.WEIXIN, object : UMAuthListener {
                    override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>) {
                        wechatId = p2["openid"].toString()
                        headImage = p2["iconurl"].toString()
                        nickname = p2["name"].toString()
                        unionid = p2["unionid"].toString()

                        startActivity<MainActivity>()
                        finish()

 /*                       login(unionid, wechatId, headImage, nickname).dialog(this@LoginActivity) {
                            val user = it.data[0]
                            UserModel.userId = user.id
                            UserModel.headImg = user.headImg
                            UserModel.nickName = user.nickName
                            UserModel.allContributeNum = user.allContributeNum ?: "0.0"
                            UserModel.memberLevelId = user.memberId ?: ""
                            JPushInterface.setAlias(this@LoginActivity, 1, user.id)
                            startActivity<MainActivity>()
                            finish()
                        }*/
                    }

                    override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {

                    }

                    override fun onError(p0: SHARE_MEDIA?, p1: Int, t: Throwable) {

                    }

                    override fun onStart(p0: SHARE_MEDIA?) {

                    }
                })
        }

        override fun onError(platform: SHARE_MEDIA, action: Int, t: Throwable) {
            t.printStackTrace()
            toast("授权失败")
        }

        override fun onCancel(platform: SHARE_MEDIA, action: Int) {
            toast("授权取消")
        }
    }
}
