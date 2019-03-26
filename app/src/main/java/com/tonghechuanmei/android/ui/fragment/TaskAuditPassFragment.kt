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
import com.tonghechuanmei.android.api.submitTaskNumber
import com.tonghechuanmei.android.databinding.FragmentTaskAuditPassBinding
import com.tonghechuanmei.android.databinding.ItemTaskAuditPassBinding
import com.tonghechuanmei.android.model.ApplyStateListModel
import com.tonghechuanmei.android.utils.AlertUtils
import com.tonghechuanmei.android.utils.previewMedia
import kotlinx.android.synthetic.main.fragment_task_audit_pass.*

/**
 * Author     : shandirong
 * Date       : 2018/11/19 10:57
 */
class TaskAuditPassFragment : BaseFragment<FragmentTaskAuditPassBinding>() {

    private lateinit var mAdapter: BaseRecyclerAdapter
    private lateinit var mImageAdapter: BaseRecyclerAdapter
    private var mState = ""
    private var mId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_audit_pass, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mAdapter = task_audit_pass_rv.linear().setup {
            addType<ApplyStateListModel.Data.Result>(R.layout.item_task_audit_pass)
            onBind {
                val dataBinding = getViewDataBinding<ItemTaskAuditPassBinding>()
                mImageAdapter =
                        dataBinding.itemTaskAuditRecyclerView
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
                val bean = models!![adapterPosition] as ApplyStateListModel.Data.Result
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
            addClickable(R.id.item_task_audit_btn)
            onClick {
                val bean = models!![adapterPosition] as ApplyStateListModel.Data.Result
                AlertUtils.editAlert(activity!!, "请填写运单号") { s ->
                    submitTaskNumber(bean.id, s).net(this@TaskAuditPassFragment) {
                        initData()
                    }
                    AlertUtils.dismiss()
                }
            }
        }
    }

    override fun initData() {
        task_audit_pass_refresh.autoRefresh()
        task_audit_pass_refresh.onRefresh {
            getApplyStateList(mId, mState, task_audit_pass_refresh.page).page(task_audit_pass_refresh) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    task_audit_pass_refresh.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= task_audit_pass_refresh.page }
                }
            }
        }
    }

    fun setState(state: String) {
        mState = state
    }

    fun setId(id: String) {
        mId = id
    }
}
