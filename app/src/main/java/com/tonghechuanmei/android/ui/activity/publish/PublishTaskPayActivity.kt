package com.tonghechuanmei.android.ui.activity.publish

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.designer.library.utils.DateUtils
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.pingplusplus.android.Pingpp
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.pay
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityPublishTaskPayBinding
import com.tonghechuanmei.android.model.PingPlusPayWayModel
import com.tonghechuanmei.android.model.PublishTaskModel
import com.tonghechuanmei.android.model.UpdateFileModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.PaySuccessActivity
import com.tonghechuanmei.android.utils.handlePinPlusResult
import io.reactivex.Observable
import io.reactivex.rxkotlin.combineLatest
import kotlinx.android.synthetic.main.activity_publish_task_pay.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.File

class PublishTaskPayActivity : BaseToolbarActivity<ActivityPublishTaskPayBinding>() {

    lateinit var model: PublishTaskModel

    override fun initView() {
        title = "发布活动"
    }

    override fun initData() {
        model = intent.getSerializableExtra("model") as PublishTaskModel
        binding.m = model
        binding.v = this

        if (model.thirdPayWayVisible) {
            rg_pay_way.checkedChanges().subscribe {
                when (it) {
                    R.id.rb_pay_way_ali -> {
                        model.payType = "1"
                    }
                    R.id.rb_pay_way_wx -> {
                        model.payType = "2"
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_pay_way_alipay -> {
                rb_pay_way_ali.isChecked = true
            }
            R.id.iv_pay_way_wx -> {
                rb_pay_way_wx.isChecked = true
            }
            R.id.tv_confirm -> {
                if (model.thirdPayWayVisible && model.payType == "4") {
                    toast("请选择支付方式")
                    return
                }
                val publishTaskPayObservable = post<PingPlusPayWayModel>("task/pulishTask.json") {
                    param("content", model.content)
                    param("endTime", DateUtils.formatDate(model.endTime, "yyyy-MM-dd HH:mm:ss"))
                    param("filesStr", model.filesStr)
                    param("haveAudit", model.haveAudit)
                    param("haveObj", model.haveObj)
                    param("haveVoucher", model.haveVoucher)
                    param("explicitLink", model.explicitLink)
                    param("lat", model.lat)
                    param("lng", model.lng)
                    param("memberId", model.memberId)
                    param("name", model.name)
                    param("adress", model.address)
                    param("objName", model.objName)
                    param("objPrice", model.objPrice)
                    param("payType", model.payType)
                    param("requirements", model.requirements)
                    param("serverAreaCityId", model.serverAreaCityId)
                    param("serverAreaCountyId", model.serverAreaCountyId)
                    param("serverAreaProvinceId", model.serverAreaProvinceId)
                    param("signUpNum", model.signUpNum)
                    param("startTime", DateUtils.formatDate(model.startTime, "yyyy-MM-dd HH:mm:ss"))
                    param("taskCategoryId", model.taskCategoryId)
                    param("taskPrice", model.taskPrice)
                    param("userId", UserModel.userId)
                }

                val updateThreads = arrayListOf<Observable<UpdateFileModel>>()

                model.mediaList.forEach {
                    val temp = post<UpdateFileModel>("upload/uploadindex.html") {
                        file("file", File(it))
                    }
                    updateThreads.add(temp)
                }

                val updateObservableList = updateThreads.combineLatest {
                    val stringBuilder = StringBuilder()
                    it.forEachWithIndex { i, _ ->
                        if (i < it.size - 1) {
                            stringBuilder.append("1,${it[i].data};")
                        } else {
                            stringBuilder.append("1,${it[i].data}")
                        }
                    }
                    model.filesStr = stringBuilder.toString()
                }

                if (model.thirdPayWayVisible) {

                    if (model.mediaList.isEmpty()) {
                        publishTaskPayObservable
                                .flatMap {
                                    cancelTaskId = it.data.id
                                    pay(it)
                                }.dialog(this) {
                                    Pingpp.createPayment(this@PublishTaskPayActivity, it.data.data)
                                }
                    } else {
                        updateObservableList.flatMap {
                            publishTaskPayObservable
                        }.flatMap {
                            cancelTaskId = it.data.id
                            pay(it)
                        }.dialog(this) {
                            Pingpp.createPayment(this@PublishTaskPayActivity, it.data.data)
                        }
                    }
                } else {
                    if (model.mediaList.isEmpty()) {
                        publishTaskPayObservable.dialog(this) {
                            startActivity<PaySuccessActivity>("payState" to true)
                        }
                    } else {
                        updateObservableList.flatMap {
                            publishTaskPayObservable
                        }.dialog(this) {
                            startActivity<PaySuccessActivity>("payState" to true)
                        }
                    }
                }
            }
        }
    }

    var cancelTaskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_task_pay)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            handlePinPlusResult(data) {
                cancelTaskId.let {
                    post<String>("/task/payFailByTask.json") {
                        param("taskId", cancelTaskId)
                    }.dialog(this, false) {
                        startActivity<PaySuccessActivity>()
                    }
                }
            }
        }
    }
}
