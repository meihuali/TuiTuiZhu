/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：ZMT
 * Author：马靖乘
 * Date：18-11-18 下午4:22
 */

package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.utils.RegexUtils
import com.hwangjr.rxbus.RxBus
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.checkSms
import com.tonghechuanmei.android.api.sendSms
import com.tonghechuanmei.android.api.userMsg
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityUserAuthBinding
import com.tonghechuanmei.android.model.AliCerModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.model.UserMsgModel
import com.tonghechuanmei.android.utils.getZMCertification
import com.zmxy.ZMCertification
import com.zmxy.ZMCertificationListener
import kotlinx.android.synthetic.main.activity_user_auth.*
import org.jetbrains.anko.toast

/**
 * 是否回显，true、是，false
 */
//
class UserAuthActivity : BaseToolbarActivity<ActivityUserAuthBinding>(), ZMCertificationListener {
    var tag = false
    var isCheck = false
    var isFail = false
    private var countRegDownTimer: CountDownTimer? = null
    private var zmCertification: ZMCertification? = null
    override fun initView() {
        title = "用户认证"
        tag = intent.getBooleanExtra("tag", false)
        isCheck = intent.getBooleanExtra("isCheck", false)
        isFail = intent.getBooleanExtra("isFail", false)
        if (tag) {
            tv_confirm.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
        } else {
            tv_confirm.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
        tv_confirm.text = intent.getStringExtra("confirm")
        binding.m = tag
        zmCertification = ZMCertification.getInstance()
    }

    override fun initData() {
        if (tag) {     //回显隐藏输入框
            post<UserMsgModel>("usermsg/getUserMsgList.json") {
                param("userId", UserModel.userId)
            }.state(rootView) {
                if (!it.data.resultList.isNullOrEmpty()) {
                    val m = it.data.resultList[0]
                    binding.model = m
                }
            }
        } else {
        }
        if (isCheck) {
            binding.v = this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_auth)

        binding.v = this
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tv_get_code -> {
                when {
                    et_phone.text.toString().isEmpty() -> toast(resources.getString(R.string.phone_no_empty))
                    !RegexUtils.isMobileExact(et_phone.text.toString()) -> toast(resources.getString(R.string.phone_error))
                    else -> sendSms(et_phone.text.toString()).net(this) {
                        toast(resources.getString(R.string.send_success))
                        startTime()
                    }
                }
            }
            R.id.tv_confirm -> {
                if (isCheck) {
                    when {
                        user_input_et.text.toString().isEmpty() -> toast(resources.getString(R.string.name_no_empty))
                        et_identity.text.toString().isEmpty() -> toast(resources.getString(R.string.identity_no_empty))
                        et_phone.text.toString().isEmpty() -> toast(resources.getString(R.string.phone_no_empty))
                        et_code.text.toString().isEmpty() -> toast(resources.getString(R.string.code_no_empty))
                        et_identity.text.toString().length != 15 && et_identity.text.toString().length != 18 -> toast("非法身份证号")
                        else -> {
                            checkSms(et_code.text.toString(), et_phone.text.toString()).flatMap {
                                post<AliCerModel>("usermsg/aliCertIdentification.json") {
                                    param("certName", user_input_et.text.toString().trim())
                                    param("certNo", et_identity.text.toString().trim())
                                    param("phone", et_phone.text.toString().trim())
                                    param("userId", UserModel.userId)
                                }
                            }.dialog(this) {
                                zmCertification!!.setZMCertificationListener(this@UserAuthActivity)
                                zmCertification!!.startCertification(
                                    this@UserAuthActivity,
                                    it.data!!.bizNo,
                                    "2088231804736983",
                                    null
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startTime() {
        val count = 60
        if (countRegDownTimer != null) {
            countRegDownTimer!!.cancel()
        }

        countRegDownTimer = object : CountDownTimer((count * 1000).toLong(), 1000) {
            override fun onTick(timeCount: Long) {
                tv_get_code.text = String.format(getString(R.string.time_count_down_tip), timeCount / 1000)
                tv_get_code.isClickable = false
                tv_get_code.setTextColor(ContextCompat.getColor(this@UserAuthActivity, R.color.gray))
            }

            override fun onFinish() {
                tv_get_code.setTextColor(ContextCompat.getColor(this@UserAuthActivity, R.color.white))
                tv_get_code.isClickable = true
                tv_get_code.text = "重新获取验证码"
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countRegDownTimer != null) {
            countRegDownTimer!!.cancel()
        }
    }

    override fun onFinish(isCanceled: Boolean, isPassed: Boolean, errorCode: Int) {
        zmCertification?.setZMCertificationListener(null)
        if (isCanceled)
        //Toast.makeText(this, "cancel : 芝麻验证失败，原因是：用户取消认证了", Toast.LENGTH_SHORT).show()
        else {
            if (isPassed) {
                userMsg(
                    et_identity.text.toString(),
                    et_phone.text.toString(),
                    user_input_et.text.toString()
                ).dialog(this) {
                    RxBus.get().post("certification_finish_event", "认证完成")
                    toast("认证成功")
                    finish()
                }
            } else
                Toast.makeText(
                    this,
                    "芝麻验证失败，原因是：${getZMCertification(errorCode)}",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}
