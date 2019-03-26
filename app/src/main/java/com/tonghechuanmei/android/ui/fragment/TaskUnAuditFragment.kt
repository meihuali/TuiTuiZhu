package com.tonghechuanmei.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.net.observer.net
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.BaseRecyclerAdapter
import com.designer.library.component.recycler.grid
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.getApplyStateList
import com.tonghechuanmei.android.api.passTask
import com.tonghechuanmei.android.api.unPassTask
import com.tonghechuanmei.android.databinding.FragmentTaskUnAuditBinding
import com.tonghechuanmei.android.databinding.ItemTaskAuditUncheckedBinding
import com.tonghechuanmei.android.model.ApplyStateListModel
import com.tonghechuanmei.android.ui.activity.TaskAuditActivity
import com.tonghechuanmei.android.utils.AlertUtils
import com.tonghechuanmei.android.utils.previewMedia
import kotlinx.android.synthetic.main.fragment_task_un_audit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.toast

/**
 * Author     : shandirong
 * Date       : 2018/11/19 10:57
 */
class TaskUnAuditFragment : BaseFragment<FragmentTaskUnAuditBinding>() {

    private lateinit var mAdapter: BaseRecyclerAdapter
    private lateinit var mImageAdapter: BaseRecyclerAdapter

    private var mId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_un_audit, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = task_un_audit_rv.linear().setup {

            onBind {
                val bean = models!![adapterPosition] as ApplyStateListModel.Data.Result

                val dataBinding = getViewDataBinding<ItemTaskAuditUncheckedBinding>()
                mImageAdapter =
                        dataBinding.itemTaskAuditRv
                            .grid(4).setup {
                                addType<String>(R.layout.item_picture)
                                addClickable(R.id.item)
                                onClick {
                                    previewMedia(
                                        adapterPosition,
                                        models as List<String>
                                    )
                                }
                            }
                if (!bean.subFiles.isNullOrEmpty()) {
                    val temp = arrayListOf<String>()
                    val imageList = bean.subFiles!!.split(",")
                    imageList.forEach {
                        temp.add(it)
                    }
                    mImageAdapter.models = temp
                }
                false
            }

            val view = LayoutInflater.from(activity!!)
                .inflate(R.layout.foot_view_space, task_un_audit_rv, false)
            addFooter(view)

            addType<ApplyStateListModel.Data.Result>(R.layout.item_task_audit_unchecked)

            addFastClickable(R.id.item_task_audit_check_box, R.id.item)

            onClick {
                val model = getModel<ApplyStateListModel.Data.Result>()
                setChecked(adapterPosition, !model.checked)
            }
            onCheckedChange { itemType, position, checked, allChecked ->
                val model = getModel<ApplyStateListModel.Data.Result>(position)
                model!!.checked = checked
                task_un_audit_check_box.isChecked = allChecked
            }
        }
        task_un_audit_check_box.setOnClickListener {
            mAdapter.allChecked(task_un_audit_check_box.isChecked)
        }
        // 未通过
        task_un_audit_un_pass.setOnClickListener {
            if (mAdapter.checkedCount != 0) {
                val models = mAdapter.getCheckedModels<ApplyStateListModel.Data.Result>()
                var id = ""
                models.forEach { bean ->
                    id = id + bean.id + ","
                }
                id = id.substring(0, id.length - 1)
                AlertUtils.refuseAlert(activity!!) { s ->
                    unPassTask(id, s).net(this@TaskUnAuditFragment) {
                        (activity!! as TaskAuditActivity).notifyData()
                    }
                    AlertUtils.dismiss()
                }
            } else {
                activity!!.toast("请选择用户")
            }
        }
        // 通过
        task_un_audit_pass.setOnClickListener {
            if (mAdapter.checkedCount != 0) {
                val models = mAdapter.getCheckedModels<ApplyStateListModel.Data.Result>()
                var id = ""
                models.forEach { bean ->
                    id = id + bean.id + ","
                }
                id = id.substring(0, id.length - 1)
                activity!!.alert {
                    message = "确认通过？"
                    cancelButton {
                        it.dismiss()
                    }
                    positiveButton("确认") {
                        passTask(id).net(this@TaskUnAuditFragment) {
                            (activity!! as TaskAuditActivity).notifyData()
                        }
                    }
                }.show()
            } else {
                activity!!.toast("请选择用户")
            }
        }
    }

    override fun initData() {
        task_un_audit_refresh.autoRefresh()
        task_un_audit_refresh.onRefresh {
            getApplyStateList(mId, "1", task_un_audit_refresh.page).page(task_un_audit_refresh) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    task_un_audit_check_box.isChecked = false
                    mAdapter.clearChecked()
                    task_un_audit_refresh.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= task_un_audit_refresh.page }
                }
            }
        }
    }

    fun setId(id: String) {
        mId = id
    }
}
