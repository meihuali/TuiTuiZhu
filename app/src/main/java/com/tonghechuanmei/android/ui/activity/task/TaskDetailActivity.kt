package com.tonghechuanmei.android.ui.activity.task

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Lifecycle
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.databinding.inflate
import com.designer.library.component.net.Net
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.hwangjr.rxbus.RxBus
import com.just.agentweb.AgentWeb
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.applyTask
import com.tonghechuanmei.android.api.cancelTask
import com.tonghechuanmei.android.api.getAuthDynamic
import com.tonghechuanmei.android.api.submitTask
import com.tonghechuanmei.android.databinding.ActivityTaskDetailBinding
import com.tonghechuanmei.android.databinding.LayoutTaskDetailFooterBinding
import com.tonghechuanmei.android.databinding.LayoutTaskDetailHeaderBinding
import com.tonghechuanmei.android.model.LocationModel
import com.tonghechuanmei.android.model.TaskDetailModel
import com.tonghechuanmei.android.model.UserModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_task_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit


class TaskDetailActivity : SwipeBackActivity<ActivityTaskDetailBinding>() {

    private lateinit var taskDetailHeaderBinding: LayoutTaskDetailHeaderBinding
    private lateinit var taskDetailFooterBinding: LayoutTaskDetailFooterBinding
    private lateinit var taskId: String
    private var currentTime = 1L
    private lateinit var web: AgentWeb.PreAgentWeb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)
    }


    override fun onRestart() {
        super.onRestart()

        // 如果未倒计时完退出界面后需要重新开始倒计时
        if (currentTime != 1L) {
            startCount()
        }
    }


    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        recycler_task_detail.linear().setup {

            addType<TaskDetailModel.Data.TaskFile>(R.layout.item_task_detail_image)

            taskDetailHeaderBinding =
                    recycler_task_detail.inflate(R.layout.layout_task_detail_header)

            taskDetailFooterBinding =
                    recycler_task_detail.inflate(R.layout.layout_task_detail_footer)

            taskDetailFooterBinding.v = this@TaskDetailActivity

            addHeader(taskDetailHeaderBinding.root)
            addFooter(taskDetailFooterBinding.root)

            val settings = taskDetailFooterBinding.webView.settings
            settings.javaScriptEnabled = true
            settings.blockNetworkImage = false
            taskDetailFooterBinding.webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }
        }
    }


    override fun initData() {
        taskId = intent.getStringExtra("taskId")

        post<TaskDetailModel>("/task/getTaskModeDetail.json") {
            param("id", taskId)
            param("userId", UserModel.userId)
            param("lat", LocationModel.latitude)
            param("lng", LocationModel.longitude)
        }.state(ll_container) {
            if (it.msg == "-4") {
                showEmpty()
            } else {
                taskDetailHeaderBinding.m = it.data
                binding.m = it.data
                binding.executePendingBindings()

                recycler_task_detail.baseAdapter.models = it.data.taskFileList

                val link = it.data.explicitLink
                if (!link.isNullOrEmpty()) {
                    taskDetailFooterBinding.m = it.data
                    taskDetailFooterBinding.webView.loadUrl(link)
                }

                // 报名任务状态
                if (it.data.signState.isNullOrEmpty() || it.data.signState == "-1") {
                    startCount()
                    // 提交任务状态
                } else if (it.data.signState == "0") {
                    startCount()
                } else {
                    apply_btn.isEnabled = true
                }

                // 如果不是预览界面，下方按钮需要动态改变
                if (intent.getBooleanExtra("preview", false)) {
                    btn_layout.visibility = View.GONE
                } else {
                    when {
                        it.data.signState == null -> btn_layout.visibility = View.VISIBLE
                        it.data.signState != "2" -> btn_layout.visibility = View.VISIBLE
                        else -> btn_layout.visibility = View.GONE
                    }
                }
                binding.v = this@TaskDetailActivity
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.copy -> {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 将文本内容放到系统剪贴板里
                cm.primaryClip = ClipData.newPlainText(null, binding.m!!.explicitLink ?: "")
                toast("复制成功")
            }
            R.id.iv_share -> {
                val model = binding.m!!
                val web =
                    UMWeb("${Net.HOST}share?referrerUserId=${model.userId}&taskId=${model.id}")
                web.title = model.name
                web.setThumb(UMImage(this, R.drawable.ic_logo))
                web.description = model.content ?: ""
                ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .withMedia(web).open()
            }
            R.id.cancel_btn -> {
                alert {
                    message = "是否放弃活动？"
                    cancelButton {
                        it.dismiss()
                    }
                    positiveButton("确认") {
                        cancelTask(binding.m!!.taskSignId!!).net(this@TaskDetailActivity) {
                            RxBus.get().post("refresh_task_lobby_event", "刷新任务大厅")
                            toast("已放弃")
                            finish()
                        }
                    }
                }.show()
            }
            R.id.apply_btn -> {
                positiveBtn()
            }
        }
    }

    private fun positiveBtn() {
        val data = binding.m!!

        if (data.signState.isNullOrEmpty() || data.signState == "-1") {
            getAuthDynamic {
                alert {
                    message = "确认报名活动？"
                    cancelButton {
                        it.dismiss()
                    }
                    positiveButton("确认") {
                        applyTask(taskId).dialog(this@TaskDetailActivity) {
                            initData()
                        }
                    }
                }.show()
            }
            // 提交任务( 无实物奖励/不需要审核用户)
        } else if (data.signState == "0" && data.haveObj == "0" && data.haveVoucher == "0") {
            submitTask(data.taskSignId ?: "").dialog(this) {
                RxBus.get().post("refresh_task_lobby_event", "刷新任务大厅")
                toast("提交成功")
                finish()
            }
            // 提交任务
        } else {
            startActivity<SubmitTaskActivity>("id" to data.id, "data" to data)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }

    private fun startCount() {
        val totalSeconds = 5L

        Observable.intervalRange(0, totalSeconds, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val temp = totalSeconds - it
                val btnStr = binding.m!!.getBtnStr()
                apply_btn.text = "$btnStr ${temp}s"
                currentTime = temp
                temp
            }.filter {
                it == 1L
            }.autoDisposable(scope(Lifecycle.Event.ON_DESTROY))
            .subscribe {
                apply_btn.isEnabled = true
                apply_btn.text = binding.m!!.getBtnStr()
            }
    }

}
