package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.designer.library.base.BaseActivity
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityVideoBinding
import kotlinx.android.synthetic.main.activity_video.*

/**
 * Author     : shandirong
 * Date       : 2018/11/23 11:16
 */
class VideoActivity : BaseActivity<ActivityVideoBinding>() {
    override fun initView() {

    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        web_view.settings.domStorageEnabled = true
        web_view.settings.javaScriptEnabled = true
        web_view.settings.blockNetworkImage = false

        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view!!.loadUrl(url)
                return false
            }
        }
        web_view.loadUrl("https://www.baidu.com")
    }
}
