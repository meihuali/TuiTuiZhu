package com.tonghechuanmei.android.ui.activity.task

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.designer.library.base.SwipeBackActivity
import com.designer.library.component.BannerImageLoader
import com.designer.library.component.net.get
import com.designer.library.component.net.observer.page
import com.designer.library.component.net.observer.state
import com.designer.library.component.net.post
import com.designer.library.component.recycler.NestedLinearLayoutManager
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityTaskLobbyBinding
import com.tonghechuanmei.android.model.*
import com.tonghechuanmei.android.ui.dialog.TaskRegionSelectorDialog
import com.tonghechuanmei.android.utils.getLocation
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_task_lobby.*
import org.jetbrains.anko.startActivity


class TaskLobbyActivity : SwipeBackActivity<ActivityTaskLobbyBinding>() {

    private val taskFilterModel = TaskFilterModel()
    private lateinit var taskTypeModel: TaskTypeModel

    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()

        rv_task_category.linear(RecyclerView.HORIZONTAL).setup {
            addType<TaskTypeModel.Data>(R.layout.item_task_type)
            onClick(R.id.item) {
                val model = getModel<TaskTypeModel.Data>()
                startActivity<TaskTypeListActivity>(
                    "taskTypeModel" to model
                )
            }
        }

        rv_task_lobby.layoutManager = NestedLinearLayoutManager(this)
        rv_task_lobby.setup {
            addClickable(R.id.item)
            addType<TaskListModel.Data.Result>(R.layout.item_task_lobby)

            onClick {
                val model = getModel<TaskListModel.Data.Result>()
                startActivity<TaskDetailActivity>("contentId" to model.contentId, "taskId" to model.id)
            }
        }

        refresh.adapter = rv_task_lobby.baseAdapter
    }


    override fun initData() {
        refresh.onRefresh {
            AndPermission.with(this@TaskLobbyActivity)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted { data: MutableList<String> -> getTaskList() }
                .onDenied { data -> getTaskListForDefaultLocation() }
                .rationale { context, data, executor -> executor.execute() }
                .start()
        }

        refresh.onLoadMore {

            // 请求网络
            post<TaskListModel>("/task/getTaskModelList.json") {
                params(taskFilterModel.get())
                param("page", refresh.page)
                param("isNovice", "0")
            }.page(refresh) {

                // 判断是否没有更多数据
                if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                    state_layout.showEmpty()
                } else {
                    refresh.refreshData(it.data.resultList) {
                        it.data.pageInfo.allpage >= refresh.page
                    }
                }
            }
        }
        refresh.autoRefresh()
    }

    @Subscribe(tags = [Tag("refresh_task_lobby_event")])
    fun subscribe(event: String) {
        refresh.autoRefresh()
    }

    /**
     * 过滤器
     */
    fun filter() {
        refresh.page = 1
        post<TaskListModel>("/task/getTaskModelList.json") {
            params(taskFilterModel.get())
            param("isNovice", "0")
            param("page", refresh.page)
        }.state(state_layout) {
            if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                showEmpty()
            } else {
                refresh.silentRefresh(it.data.resultList) {
                    it.data.pageInfo.allpage >= refresh.page
                }
            }
        }
    }

    /*
        通过定位获取活动大厅列表
    */
    fun getTaskList() {
        post<TaskTypeModel>("taskCate/getPublishTaskCateList.json").flatMap {
            if (it.msg != "-4") {
                taskTypeModel = it
                rv_task_category.baseAdapter.models = it.data
            }
            get<TaskLobbyBannerModel>("adspace/getSpaceTasks.json") {
                param("spaceCode", "renwubanner")
            }
        }.flatMap { bannerData ->
            if (bannerData.msg != "-4") {
                val banners = arrayListOf<String>()
                bannerData.data.forEach {
                    banners.add(it.adImage)
                }
                banner.setImageLoader(BannerImageLoader()).setImages(banners).setOnBannerListener {
                    startActivity<TaskDetailActivity>("taskId" to bannerData.data[it].taskId)
                }.start()
            }
            getLocation()
        }.flatMap {
            tv_location.text = it.city
            post<TaskListModel>("/task/getTaskModelList.json") {
                params(taskFilterModel.get())
                param("isNovice", "0")
                param("page", "1")
                param("orderby", "1_0")
            }
        }.page(refresh, rv_task_lobby.baseAdapter) {
            binding.v = this@TaskLobbyActivity
            binding.m = taskFilterModel
            if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                state_layout.showEmpty()
            } else {
                refreshData(it.data.resultList) {
                    it.data.pageInfo.allpage >= pageRefreshLayout.page
                }
            }
        }
    }

    /*
        没有获取到定位信息, 使用默认的位置显示活动大厅(杭州)
    */
    fun getTaskListForDefaultLocation() {
        post<TaskTypeModel>("taskCate/getPublishTaskCateList.json").flatMap {
            rv_task_category.baseAdapter.models = it.data

            get<TaskLobbyBannerModel>("adspace/getSpaceTasks.json") {
                param("spaceCode", "renwubanner")
            }
        }.flatMap { bannerData ->
            if (bannerData.msg != "-4") {
                val banners = arrayListOf<String>()
                bannerData.data.forEach {
                    banners.add(it.adImage)
                }
                banner.setImageLoader(BannerImageLoader()).setImages(banners).setOnBannerListener {
                    startActivity<TaskDetailActivity>("taskId" to bannerData.data[it].taskId)
                }.start()
            }
            post<TaskListModel>("/task/getTaskModelList.json") {
                params(taskFilterModel.get())
                param("isNovice", "0")
                param("page", "1")
                param("orderby", "1_0")
            }
        }.page(refresh) {
            binding.v = this@TaskLobbyActivity
            binding.m = taskFilterModel
            if (it.msg == "-4" || it.data.resultList.isNullOrEmpty()) {
                state_layout.showEmpty()
            } else {
                refreshData(it.data.resultList) {
                    it.data.pageInfo.allpage >= pageRefreshLayout.page
                }
            }
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.constraint_comprehensive_order -> comprehensiveFilter()
            R.id.constraint_type_order -> typeFilter()
            R.id.constraint_area -> {
                TaskRegionSelectorDialog().apply {
                    arguments = Bundle().apply { putString("flag", "task_address_selector_lobby_event") }
                }.show(supportFragmentManager, null)
            }
            R.id.tv_search -> {
                startActivity(
                    Intent(this, SearchTaskActivity::class.java),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, tv_search, "search_edit").toBundle()
                )
            }
        }
    }

    private fun typeFilter() {
        val categoryTypes = arrayListOf<String>()
        taskTypeModel.data.forEach {
            categoryTypes.add(it.name)
        }
        ListPopupWindow(this).apply {
            val adapter =
                ArrayAdapter(this@TaskLobbyActivity, android.R.layout.simple_list_item_1, categoryTypes)
            setAdapter(adapter)
            anchorView = constraint_type_order
            setOnItemClickListener { parent, view, position, id ->
                taskFilterModel.clear()
                taskFilterModel.setTypeFilter(taskTypeModel.data[position])
                filter()
                dismiss()
            }
        }.show()
    }

    private fun comprehensiveFilter() {
        val list = arrayListOf("综合排序", "时间排序", "奖励排序", "距离排序")
        ListPopupWindow(this).apply {
            val adapter =
                ArrayAdapter(this@TaskLobbyActivity, android.R.layout.simple_list_item_1, list)
            setAdapter(adapter)

            anchorView = constraint_comprehensive_order
            setOnItemClickListener { parent, view, position, id ->
                taskFilterModel.clear()
                taskFilterModel.setComprehensiveFilter(list[position])
                filter()
                dismiss()
            }
        }.show()
    }


    @Subscribe(tags = [Tag("task_address_selector_lobby_event")])
    fun regionFilter(event: RegionSelectorModel) {
        refresh.page = 1
        taskFilterModel.clear()
        taskFilterModel.setRegionFilter(event)
        filter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_lobby)
    }
}
