package com.tonghechuanmei.android.base

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.designer.library.base.Library
import com.designer.library.component.net.Net
import com.designer.library.component.net.Net.HOST
import com.designer.library.component.net.interceptor.LogInterceptor
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.state.StateConfig
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.tencent.bugly.Bugly
import com.tonghechuanmei.android.BR
import com.tonghechuanmei.android.BuildConfig
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.Const
import com.tonghechuanmei.android.api.StupidParseConverter
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.Config
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.common.QueuedWork


class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLibrary()
        initShare()
        initPush()

        if (!BuildConfig.DEBUG) {
            Bugly.init(this, "374a09e584", false)
        }
    }

    private fun initPush() {
        JPushInterface.setDebugMode(false)
        JPushInterface.init(this)
    }

    private fun initLibrary() {
        Library.app = this
        BaseRecyclerAdapter.setConfig(BR.m)

        StateConfig.apply {
            loadingLayout = R.layout.layout_loading
            errorLayout = R.layout.layout_error
            emptyLayout = R.layout.layout_empty
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            MaterialHeader(context).setColorSchemeColors(
                resources.getColor(R.color.colorAccent)
            )
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context).setDrawableSize(20f).setTextSizeTitle(14F)
        }
        Net.setConfig {
            converter(StupidParseConverter())
            addInterceptor(LogInterceptor())
            HOST = Const.HOST
        }
    }


    private fun initShare() {
        Config.isUmengWx = true
        QueuedWork.isUseThreadPool = false
        UMShareAPI.get(this)
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "")
        PlatformConfig.setWeixin("wx909e2214136c8bac", "2a5b1a401d385121bed3a5db08fbdc2f")

/*      PlatformConfig.setQQZone("1106829425", "LtXkVwDbV20It5nw")

        PlatformConfig.setSinaWeibo(
            "751638881", "d0c80873e0e3738680eb5ed6255a2049", "https://sns.whalecloud.com/sina2/callback"
        )*/
    }
}