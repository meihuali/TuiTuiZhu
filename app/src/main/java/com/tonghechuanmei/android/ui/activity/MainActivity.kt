/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/21/18 12:54 PM
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.view.View
import com.designer.library.base.BaseActivity
import com.designer.library.component.BannerImageLoader
import com.designer.library.component.databinding.loadCircular
import com.designer.library.component.net.get
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.grid
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.taobao.sophix.SophixManager
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getAuthDynamic
import com.tonghechuanmei.android.api.getUserDetail
import com.tonghechuanmei.android.databinding.ActivityMainBinding
import com.tonghechuanmei.android.model.*
import com.tonghechuanmei.android.pushreceiver.ExampleUtil
import com.tonghechuanmei.android.ui.activity.guide.NewbieGuideActivity
import com.tonghechuanmei.android.ui.activity.otherserver.ExChangeActivity
import com.tonghechuanmei.android.ui.activity.publish.PublishTaskTypeActivity
import com.tonghechuanmei.android.ui.activity.recharge.RechargeMobileActivity
import com.tonghechuanmei.android.ui.activity.task.TaskDetailActivity
import com.tonghechuanmei.android.ui.activity.task.TaskLobbyActivity
import com.tonghechuanmei.android.ui.dialog.AdvertisingDialog
import com.tonghechuanmei.android.ui.dialog.NewbieGiftDialog
import com.tonghechuanmei.android.utils.DeviceInfoUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import util.UpdateAppUtils
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var homeIconList = ArrayList<HomeIconEntity>()

    override fun initData() {
        post<DeviceModel>("device/device.json") {
            param("deviceId", ExampleUtil.getDeviceId(this@MainActivity))
        }.net(this) {
            if (it.msg != "-4") {
                SophixManager.getInstance().queryAndLoadNewPatch()
            }
        }

        UserModel.isFirst = false

        getUserDetail().net(this) {
            if (it.msg != "-4") {
                UserModel.saveUser(it.data)
            }
        }

        post<VersionModel>("getVersionInfo.json") {
            param("type", "1")
            param("versionNo", DeviceInfoUtil.getAppVersionCode(this@MainActivity))
        }.net(this) {
            if (it.msg != "-4") {
                UpdateAppUtils.from(this@MainActivity)
                    // .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
                    .serverVersionCode(it.data!!.versionNo!!.toInt())
                    .apkPath(it.data.downloadUrl)
                    // .showNotification(false) //是否显示下载进度到通知栏，默认为true
                    .updateInfo(it.data.remark)  //更新日志信息 String
                    // .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
                    .isForce(true) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                    .update()
            }
        }

        head_iv.loadCircular(UserModel.headImg)

        checkDialog()

        get<TaskLobbyBannerModel>("adspace/getSpaceTasks.json") {
            param("spaceCode", "shouyebanner")
        }.net(this) { bannerData ->
            if (bannerData.data.isNullOrEmpty()) {
                main_foot_view.visibility = View.GONE
                return@net
            }
            main_foot_view.visibility = View.VISIBLE
            val banners = arrayListOf<String>()
            bannerData.data.forEach {
                banners.add(it.adImage)
            }

            main_foot_view.setImageLoader(BannerImageLoader()).setImages(banners).setOnBannerListener {
                startActivity<TaskDetailActivity>("taskId" to bannerData.data[it].taskId)
            }.start()
        }
    }

    /**
     * 检查对话框弹出
     */
    private fun checkDialog() {
        post<MainDialogModel>("/welfare/userwelfarelist.json") {
            param("userId", UserModel.userId)
        }.net(this) {
            if (it.data.welfare != null) {
                NewbieGiftDialog().apply {
                    arguments = Bundle().apply { putParcelable("model", it.data.welfare) }
                }.show(supportFragmentManager, null)
            } else if (it.data.ad != null) {
                AdvertisingDialog().apply {
                    arguments = Bundle().apply { putParcelable("model", it.data.ad) }
                }.show(supportFragmentManager, null)
            }
        }
    }

    override fun initView() {

        darkMode()
        top_iv.setStatusBarPadding()
        home_recycler_view.grid(2).setup {
            addType<HomeIconEntity>(R.layout.item_main_icon)
            addClickable(R.id.item_main_layout)
            onClick {
                when (adapterPosition) {
                    0 -> {
                        getAuthDynamic {
                            startActivity<PublishTaskTypeActivity>()
                        }
                    }
                    1 -> startActivity<EmptyActivity>()
                    2 -> startActivity<NewbieGuideActivity>()//新手指南
                    3 -> startActivity<TaskLobbyActivity>()//任务大厅
                    4 -> startActivity<MyTaskActivity>()//我的任务
                    5 -> startActivity<VipCenterActivity>() //会员中心
                    6 -> startActivity<RankingActivity>() //排行榜
                    7 -> startActivity<EmptyActivity>()
                    8 -> startActivity<RechargeMobileActivity>()
                    9 -> {
                        startActivity<ExChangeActivity>()  // startActivity<ExchangeCenterActivity>() //兑换中心
                    }
                }
            }
        }

        homeIconList.apply {
            add(HomeIconEntity(getString(R.string.home_icon_fb), "100", R.drawable.icon_fb_home))
            add(HomeIconEntity(getString(R.string.home_icon_fl), "暂未开放", R.drawable.icon_fl_home))
            add(HomeIconEntity(getString(R.string.home_icon_xszn), "100", R.drawable.icon_xszn_home))
            add(HomeIconEntity(getString(R.string.home_icon_rwdt), "100", R.drawable.icon_rwdt_home))
            add(HomeIconEntity(getString(R.string.home_icon_wdrw), "100", R.drawable.icon_wdrw_home))
            add(HomeIconEntity(getString(R.string.home_icon_hyzx), "100", R.drawable.icon_hyzx_home))
            add(HomeIconEntity(getString(R.string.home_icon_phb), "100", R.drawable.icon_phb_home))
            add(HomeIconEntity(getString(R.string.home_icon_gxsg), "暂未开放", R.drawable.icon_gxsg_home))
            add(HomeIconEntity(getString(R.string.home_icon_hfcz), "100", R.drawable.icon_hfcz_home))
            add(HomeIconEntity(getString(R.string.home_icon_dhzx), "100", R.drawable.icon_dsf_home))
        }

        home_recycler_view.baseAdapter.models = homeIconList
        binding.v = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.head_iv -> {
                startActivity<PersonCenterActivity>()
            }
            R.id.dismiss_banner -> {
                binding.mainFootView.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

