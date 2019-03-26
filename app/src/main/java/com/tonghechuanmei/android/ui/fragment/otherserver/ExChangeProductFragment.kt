/*
 * Copyright (C) 2018,米珈科技有限公司 All rights reserved.
 * Project：TuiTuiPig1.1
 * Author：马靖乘
 * Date：18-11-28 下午2:03
 */

package com.tonghechuanmei.android.ui.fragment.otherserver


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.designer.library.base.BaseFragment
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.api.OtherServer
import com.tonghechuanmei.android.databinding.FragmentExChangeProductBinding
import com.tonghechuanmei.android.model.ProductSearchModel
import com.tonghechuanmei.android.ui.activity.otherserver.ExChangeDetailActivity
import kotlinx.android.synthetic.main.fragment_ex_change_product.*
import org.jetbrains.anko.startActivity

class ExChangeProductFragment : BaseFragment<FragmentExChangeProductBinding>() {
    private var mId = ""
    override fun initView(savedInstanceState: Bundle?) {
        refreshLayout.onRefresh {
            OtherServer.getProductSearch(mId, "2", refreshLayout.page).page(refreshLayout) {
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshLayout.refreshData(it.data.resultList)
                    { it.data.pageInfo.allpage >= refreshLayout.page }
                }
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
        rv_ex_change.linear().setup {
            addClickable(R.id.other_buy_shop, R.id.cv_ex_change)
            addType<ProductSearchModel.Data.Result>(R.layout.item_ex_change_layout)
            onClick {
                val m = models!![layoutPosition] as ProductSearchModel.Data.Result
                activity!!.startActivity<ExChangeDetailActivity>("id" to m.id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ex_change_product, container, false)
    }

    fun setId(id: String) {
        mId = id
    }

}
