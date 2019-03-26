package com.tonghechuanmei.android.ui.activity.task

import android.annotation.SuppressLint
import android.os.Bundle
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.net.observer.page
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.jakewharton.rxbinding3.widget.textChanges
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivitySearchTaskBinding
import com.tonghechuanmei.android.model.LocationModel
import com.tonghechuanmei.android.model.TaskListModel
import com.tonghechuanmei.android.model.UserModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search_task.*
import org.jetbrains.anko.startActivity
import java.util.concurrent.TimeUnit

class SearchTaskActivity : SwipeBackActivity<ActivitySearchTaskBinding>() {


    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        rv_search_result.linear().setup {
            addClickable(R.id.item)
            addType<TaskListModel.Data.Result>(R.layout.item_task_lobby)
            onClick {
                val model = getModel<TaskListModel.Data.Result>()
                startActivity<TaskDetailActivity>("contentId" to model.contentId, "taskId" to model.id)
            }
        }
    }

    lateinit var searchText: String

    @SuppressLint("CheckResult")
    override fun initData() {
        refresh.onRefresh {
            com.designer.library.component.net.post<TaskListModel>("/task/getTaskModelList.json") {
                param("lng", LocationModel.longitude)
                param("lat", LocationModel.latitude)
                param("page", refresh.page)
                param("isNovice", "0")
                param("name", searchText)
                param("userId", UserModel.userId)
            }.page(refresh) { it ->
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    showEmpty()
                } else {
                    refreshData(it.data.resultList)
                    {
                        it.data.pageInfo.allpage >= pageRefreshLayout.page
                    }
                }
            }
        }
        et_search.textChanges().debounce(500, TimeUnit.MICROSECONDS).filter {
            it.isNotEmpty()
        }.observeOn(AndroidSchedulers.mainThread()).autoDisposable(scope()).subscribe {
            searchText = it.toString()
            refresh.autoRefresh()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_task)
    }
}
