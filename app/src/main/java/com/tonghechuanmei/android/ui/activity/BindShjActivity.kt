package com.tonghechuanmei.android.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.post
import com.designer.library.utils.RegexUtils
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.base.BaseToolbarActivity
import com.tonghechuanmei.android.databinding.ActivityBindShjBinding
import com.tonghechuanmei.android.model.BaseModel
import com.tonghechuanmei.android.model.CheckSmsModel
import com.tonghechuanmei.android.model.UserModel
import kotlinx.android.synthetic.main.activity_bind_shj.*
import org.jetbrains.anko.toast

class BindShjActivity : BaseToolbarActivity<ActivityBindShjBinding>() {

    private var countRegDownTimer: CountDownTimer? = null
    override fun initView() {
        title = "绑定上禾佳"
        binding.v = this
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_shj)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.send_sms -> {
                if (checkPhone()) {
                    post<BaseModel>("http://www.eshanghejia.com/shanghejia/user/sentsms.json", true) {
                        param("phone", account_et.text.toString())
                    }.dialog(this) {
                        startRegTimeCounter()
                    }
                }
            }
            R.id.bind_shj_confirm -> {
                if (checkPhone() && sms_code_et.text.toString().trim().isNotEmpty()) {
                    post<CheckSmsModel>("http://www.eshanghejia.com/shanghejia/user/checksms.json", true) {
                        param("code", sms_code_et.text.toString())
                    }.flatMap { model ->
                        post<BaseModel>("user/bindShjAccount.json") {
                            param("shjUserId", model.data!!.id)
                            param("userId", UserModel.userId)
                        }
                    }.dialog(this) {
                        toast("绑定成功")
                        finish()
                    }
                } else {
                    toast("验证码不能为空")
                }
            }
        }
    }

    private fun startRegTimeCounter() {
        val count = 60
        if (countRegDownTimer != null) {
            countRegDownTimer!!.cancel()
        }
        countRegDownTimer = object : CountDownTimer((count * 1000).toLong(), 1000) {
            override fun onTick(timeCount: Long) {
                send_sms.text = String.format(getString(R.string.time_count_down_tip), timeCount / 1000)
                send_sms.isClickable = false
                send_sms.setTextColor(ContextCompat.getColor(this@BindShjActivity, R.color.gray))
            }

            override fun onFinish() {
                send_sms.setTextColor(ContextCompat.getColor(this@BindShjActivity, R.color.colorAccent))
                send_sms.isClickable = true
                send_sms.text = "重新获取验证码"
            }
        }.start()
    }

    private fun checkPhone(): Boolean {
        if (account_et.text.isNullOrEmpty()) {
            toast(resources.getString(R.string.phone_no_empty))
            return false
        }
        if (!RegexUtils.isMobileExact(account_et.text.toString())) {
            toast(resources.getString(R.string.phone_error))
            return false
        }
        return true
    }
}
