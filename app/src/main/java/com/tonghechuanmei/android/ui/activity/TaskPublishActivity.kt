package com.tonghechuanmei.android.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.post
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.cancelPublish
import com.tonghechuanmei.android.api.deleteTask
import com.tonghechuanmei.android.api.getTaskAuditList
import com.tonghechuanmei.android.api.taskPayAgain
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityTaskPublishBinding
import com.tonghechuanmei.android.databinding.ItemPeopleBinding
import com.tonghechuanmei.android.databinding.ItemTaskPublishBinding
import com.tonghechuanmei.android.model.TaskPublishModel
import com.tonghechuanmei.android.ui.activity.publish.PublishTaskOptionActivity
import com.tonghechuanmei.android.ui.activity.task.TaskDetailActivity
import kotlinx.android.synthetic.main.activity_task_publish.*
import org.jetbrains.anko.*

/**
 * Author     : shandirong
 * Date       : 2018/11/17 16:51
 * 我的发布
 */
class TaskPublishActivity : BaseToolbarActivity<ActivityTaskPublishBinding>() {

    private lateinit var mAdapter: BaseRecyclerAdapter
    var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_publish)
    }

    override fun initView() {
        title = "我的发布"
        mAdapter = task_publish_rv.linear().setup {
            addType<TaskPublishModel.Data.Result>(R.layout.item_task_publish)
            onBind {
                val bean = models!![adapterPosition] as TaskPublishModel.Data.Result
                val dataBinding = getViewDataBinding<ItemTaskPublishBinding>()
                dataBinding.itemTaskPublishRecyclerView.linear(HORIZONTAL).setup {
                    addType<TaskPublishModel.Data.Result.TaskSign>(R.layout.item_people)
                    models = bean.taskSignList
                    onBind {
                        val binding = getViewDataBinding<ItemPeopleBinding>()
                        val layoutParams = binding.layout.layoutParams as RecyclerView.LayoutParams
                        if (adapterPosition == 0) {
                            layoutParams.marginStart = 0
                        } else {
                            layoutParams.marginStart = dip(-12)
                        }
                        binding.layout.layoutParams = layoutParams
                        false
                    }
                }
                false
            }
            addClickable(
                R.id.item_task_publish_preview_btn,
                R.id.item_task_publish_cancel_btn,
                R.id.item_task_publish_sure_btn
            )
            onClick {
                val bean = models!![adapterPosition] as TaskPublishModel.Data.Result
                when (it) {
                    R.id.item_task_publish_sure_btn -> {
                        //state：-1新建，未支付 1已支付带审核 2已经审核 3已经完成 -2 审核不通过 -3退款 -4退款成功 ,-5已下架,
                        when (bean.state) {
                            "-1" -> {
                                taskId = bean.id
                                taskPayAgain(bean.id).dialog(this@TaskPublishActivity) { b ->
                                    Pingpp.createPayment(this@TaskPublishActivity, b.data.data)
                                }
                            }
                            "-2" -> {
                                startActivity<PublishTaskOptionActivity>(
                                        "taskId" to bean.id,
                                        "modify" to true
                                )
                            }
                            "1", "2" -> {
                                startActivity<TaskAuditActivity>("id" to bean.id)
                            }
                            "-5" -> {
                                if (bean.getSureBtnStr() == "删除活动") {
                                    alert {
                                        message = "是否删除活动？"
                                        cancelButton {
                                            it.dismiss()
                                        }
                                        positiveButton("确认") {
                                            deleteTask(bean.id).net(this@TaskPublishActivity) {
                                                initData()
                                            }
                                        }
                                    }.show()
                                } else {   //复制
                                    startActivity<PublishTaskOptionActivity>("taskId" to bean.id)
                                }
                            }
                        }
                    }
                    R.id.item_task_publish_cancel_btn -> {
                        when (bean.state) {
                            "-1" -> {
                                alert {
                                    message = "是否取消发布？"
                                    cancelButton {
                                        it.dismiss()
                                    }
                                    positiveButton("确认") {
                                        deleteTask(bean.id).net(this@TaskPublishActivity) {
                                            initData()
                                        }
                                    }
                                }.show()
                            }
                            "1", "2" -> {
                                startActivity<PublishTaskOptionActivity>("taskId" to bean.id)
                            }
                            "-5" -> {
                                startActivity<PublishTaskOptionActivity>("taskId" to bean.id)
                            }
                            else -> {
                                alert {
                                    message = "是否取消发布？"
                                    cancelButton {
                                        it.dismiss()
                                    }
                                    positiveButton("确认") {
                                        cancelPublish(bean.id).net(this@TaskPublishActivity) {
                                            initData()
                                        }
                                    }
                                }.show()
                            }
                        }
                    }
                    R.id.item_task_publish_preview_btn -> {
                        // 预览
                        startActivity<TaskDetailActivity>("taskId" to bean.id, "preview" to true)
                    }
                }
            }
        }
    }

    override fun initData() {
        task_publish_refresh.autoRefresh()
        task_publish_refresh.onRefresh {
            getTaskAuditList(task_publish_refresh.page).page(task_publish_refresh) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= task_publish_refresh.page }
                    task_publish_rv.smoothScrollToPosition(0)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val result = data.extras!!.getString("pay_result")
                    /* 处理返回值
             * "success" - 支付
             * 成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
                    Log.e("-------", result)
                    if (result == "success") {
                        toast("支付成功")
                        initData()
                    } else {
                        taskId?.let {
                            post<String>("/task/payFailByTask.json") {
                                param("taskId", taskId)
                            }.dialog(this) {
                                startActivity<PaySuccessActivity>()
                            }
                        }
                    }
                }
            }
        }
    }
}
