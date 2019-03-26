/*
 * Copyr()ight (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/21/18 12:45 PM
*/

package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.designer.library.base.BaseActivity
import com.designer.library.component.net.observer.NetObserver
import com.designer.library.utils.immersive
import com.tonghechuanmei.android.BuildConfig
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.databinding.ActivitySplashBinding
import com.tonghechuanmei.android.model.UserDetailModel
import com.tonghechuanmei.android.model.UserModel
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @SuppressLint("CheckResult")
    override fun initData() {
        if (BuildConfig.DEBUG && UserModel.userId.isEmpty()) {
            //法拉利尔晴 2c9fd49f676b406b01676b79f9f70000        2c9fd49f6763662d016763d4e38a0031        2c9fd49f67541e030167544286640010
            // 402880f6672fa40501672fa405d60000  402880f6672fa40501672fa611360001 2c9fd4a065f5c92c0165f5c98a3e0006  402880f7672a53cd01672a53cdc10000
            UserModel.userId =
                    "402880f7672a53cd01672a53cdc10000"//"402880f7672a53cd01672a53cdc10000"
            getUserInfo()
        } else {
            Observable.intervalRange(0, 1, 1, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
                if (UserModel.userId.isEmpty()) {
                    if (UserModel.isFirst) {
                        startActivity<GuideActivity>()
                        finish()
                    } else {
                        startActivity<LoginActivity>()
                        finish()
                    }
                    return@subscribe
                } else {
                    getUserInfo()
                }
            }
        }
    }

    private fun getUserInfo() {
        getUserDetail().subscribe(object : NetObserver<UserDetailModel>(this@SplashActivity) {
            override fun onNext(it: UserDetailModel) {
                if (it.msg == "-4") {
                    logout()
                    return
                }
                UserModel.saveUser(it.data)
                startActivity<MainActivity>()
                finish()
            }

            override fun onError(e: Throwable) {
                logout()
            }
        })
    }

    /**
     * 退出登录
     */
    private fun logout() {
        UserModel.clear()
        val shareApi = UMShareAPI.get(this@SplashActivity)
        shareApi.deleteOauth(this@SplashActivity, SHARE_MEDIA.WEIXIN, null)
        startActivity<LoginActivity>()
        finish()
    }

    override fun initView() {
        immersive()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}
