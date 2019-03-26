package com.tonghechuanmei.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.cancelTask
import com.tonghechuanmei.android.api.deleteTaskSign
import com.tonghechuanmei.android.api.getMyApplyList
import com.tonghechuanmei.android.databinding.FragmentMyApplyBinding
import com.tonghechuanmei.android.model.ApplyStateListModel
import com.tonghechuanmei.android.ui.activity.task.TaskDetailActivity
import kotlinx.android.synthetic.main.fragment_my_apply.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.startActivity

/**
 * Author     : shandirong
 * Date       : 2018/11/20 10:57
 */
class MyApplyFragment : BaseFragment<FragmentMyApplyBinding>() {

    private lateinit var mAdapter: BaseRecyclerAdapter
    private var mState = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_apply, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = my_apply_rv.linear().setup {
            addType<ApplyStateListModel.Data.Result>(R.layout.item_my_apply)
            addClickable(R.id.item_my_apply_cancel_btn, R.id.item_my_apply_sure_btn)
            onClick {
                val bean = models!![adapterPosition] as ApplyStateListModel.Data.Result
                when (it) {
                    R.id.item_my_apply_cancel_btn -> {
                        //'-2审核失败，-1放弃活动，0已报名，1已提交，未审核，2已审核',
                        when (bean.state) {
                            "2" -> {
                                activity!!.alert {
                                    message = "是否删除活动"
                                    cancelButton {
                                        it.dismiss()
                                    }
                                    positiveButton("确认") {
                                        deleteTaskSign(bean.id).net(this@MyApplyFragment) {
                                            initData()
                                        }
                                    }
                                }.show()
                            }
                            else -> {
                                // TODO::放弃活动
                                activity!!.alert {
                                    message = "是否放弃活动？"
                                    cancelButton {
                                        it.dismiss()
                                    }
                                    positiveButton("确认") {
                                        cancelTask(bean.id).net(this@MyApplyFragment) {
                                            initData()
                                        }
                                    }
                                }.show()
                            }
                        }
                    }
                    R.id.item_my_apply_sure_btn -> {
                        when (bean.state) {
                            "0" -> {
                                // TODO::提交活动
                                activity!!.startActivity<TaskDetailActivity>("taskId" to bean.taskId)
                            }
                            "-2" -> {
                                // TODO::重新审核 如果需要提交重新提交，如果不需要提交变为待审核状态
                                activity!!.startActivity<TaskDetailActivity>("taskId" to bean.taskId)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initData() {
        my_apply_refresh.autoRefresh()
        my_apply_refresh.onRefresh {
            getMyApplyList(mState, my_apply_refresh.page).page(my_apply_refresh) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    my_apply_refresh.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= my_apply_refresh.page }
                }
            }
        }
    }

    fun setState(state: String) {
        mState = state
    }
}
