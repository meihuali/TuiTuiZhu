/*
 * Copyright (C) 2018,巨神科技有限公司 All rights reserved.
 * Project：TuiTuiZhu
 * Author：两津勘吉
 * Date：12/12/18 10:01 AM
 */

package com.tonghechuanmei.android.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.net.observer.dialog
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.divider
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getNewbieGuideQuestion
import com.tonghechuanmei.android.databinding.FragmentNewbieGuideBinding
import com.tonghechuanmei.android.model.NewbieGuideQuestionModel
import com.tonghechuanmei.android.model.NewbieGuideTaskModel
import com.tonghechuanmei.android.model.UserModel
import com.tonghechuanmei.android.ui.activity.UserAuthActivity
import com.tonghechuanmei.android.ui.activity.VipCenterActivity
import com.tonghechuanmei.android.ui.activity.guide.NewbieGuideQuestionActivity
import com.tonghechuanmei.android.ui.activity.publish.PublishTaskTypeActivity
import com.tonghechuanmei.android.ui.activity.task.TaskLobbyActivity
import kotlinx.android.synthetic.main.fragment_newbie_guide.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class NewbieGuideFragment : BaseFragment<FragmentNewbieGuideBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        rv_newbie_guide.divider(R.drawable.divider_horizontal_padding_15dp).linear().setup {
            addType<NewbieGuideTaskModel.Data.Result>(R.layout.item_newbie_guide_task)
            addType<NewbieGuideQuestionModel.Data.Result>(R.layout.item_newbie_guide_question)
            onClick(R.id.item, R.id.btn_complete) {
                when (it) {
                    R.id.item -> {
                        val model = getModel<NewbieGuideQuestionModel.Data.Result>()
                        activity?.startActivity<NewbieGuideQuestionActivity>("model" to model)
                    }
                    R.id.btn_complete -> {
                        val model = getModel<NewbieGuideTaskModel.Data.Result>()
                        if (model.isComplete == "0") {
                            performTask(model)
                        } else if (model.isState == "1") {
                            post<String>("/noobTask/gainRewards.json") {
                                param("id", model.id)
                                param("userId", UserModel.userId)
                            }.dialog(activity!!) {
                                activity.toast("恭喜您已领取 ${model.getAwardName()}")
                                initData()
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 去完成任务
     * @param model NewbieGuideTaskModel.Data.Result
     */
    private fun performTask(model: NewbieGuideTaskModel.Data.Result) {
        when (model.code) {
            "10001" -> {
                activity?.startActivity<UserAuthActivity>(
                    "tag" to false,
                    "confirm" to "确认提交",
                    "isCheck" to true,
                    "isFail" to false
                )
            }
            "10002" -> {
                activity?.startActivity<VipCenterActivity>()
            }
            "10003" -> {
                activity?.startActivity<TaskLobbyActivity>()
            }
            "10004" -> {
                activity?.startActivity<PublishTaskTypeActivity>()
            }
        }
    }

    @Subscribe(tags = [Tag("refresh_newbie_guide_event")])
    fun subscribe(event: String) {
        if (isInitView && flag == "新手任务") {
            initData()
        }
    }

    private val flag: String?
        get() {
            return arguments?.getString("flag")
        }

    override fun initData() {
        when (flag) {
            "新手任务" -> {
                post<NewbieGuideTaskModel>("/noobTask/getList.json") {
                    param("userId", UserModel.userId)
                    param("rows", 9999)
                }.state(container) {
                    if (it.msg == "-4") {
                        showEmpty()
                        return@state
                    }
                    rv_newbie_guide.baseAdapter.models = it.data.resultList
                }
            }
            "新手问答" -> {
                getNewbieGuideQuestion().state(container) {
                    if (it.msg == "-4") {
                        showEmpty()
                        return@state
                    }
                    rv_newbie_guide.baseAdapter.models = it.data.resultList
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newbie_guide, container, false)
    }
}
