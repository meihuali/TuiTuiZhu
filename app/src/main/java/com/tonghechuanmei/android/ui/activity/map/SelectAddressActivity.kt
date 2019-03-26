package com.tonghechuanmei.android.ui.activity.map

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.app.ActivityOptionsCompat
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.designer.library.base.BaseActivity
import com.designer.library.component.net.observer.net
import com.designer.library.component.recycler.baseAdapter
import com.designer.library.component.recycler.divider
import com.designer.library.component.recycler.linear
import com.designer.library.component.recycler.setup
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivitySelectAddressBinding
import com.tonghechuanmei.android.model.LocationModel
import com.tonghechuanmei.android.model.SearchAddressModel
import com.tonghechuanmei.android.utils.getLocation
import kotlinx.android.synthetic.main.activity_select_address.*
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * Author     : shandirong
 * Date       : 2018/11/24 18:17
 */
class SelectAddressActivity : BaseActivity<ActivitySelectAddressBinding>() {

    private var poiQuery: PoiSearch.Query? = null
    private var marker: Marker? = null
    private var aMap: AMap? = null
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()

        initMap()
        rv_address.divider(R.drawable.divider_horizontal_padding_15dp).linear().setup {
            addType<SearchAddressModel>(R.layout.item_address_map)
            addClickable(R.id.item, R.id.cb_checked)
            singleMode = true

            onClick {
                val model = getModel<SearchAddressModel>()
                setChecked(adapterPosition, !model.checked)
            }

            onCheckedChange { itemType, position, isChekced, isAllChecked ->

                val model = getModel<SearchAddressModel>(position)
                model?.checked = isChekced
            }
        }

        binding.v = this
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_address)
        map.onCreate(savedInstanceState)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_confirm -> {
                val intent = Intent()
                val checkedModels = rv_address.baseAdapter.getCheckedModels<SearchAddressModel>()
                val model = checkedModels[0]

                model.let {
                    intent.putExtra("model", model)
                    setResult(Activity.RESULT_OK, intent)
                }

                finish()
            }
            R.id.tv_search -> {
                val intent = Intent(this, SearchAddressActivity::class.java)
                startActivityForResult(
                    intent,
                    100,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this, tv_search, "search_edit").toBundle()
                )
            }
            R.id.iv_location -> {
                aMap!!.moveCamera(
                    CameraUpdateFactory.changeLatLng(
                        LatLng(LocationModel.latitude, LocationModel.longitude)
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

    private fun initMap() {
        if (aMap == null) {
            aMap = map.map
        }

        aMap!!.uiSettings.isZoomControlsEnabled = false

        getLocation().net(this) {
            aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 17f))
        }

        addMark()

        aMap!!.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
            override fun onCameraChange(cameraPosition: CameraPosition) {
            }

            override fun onCameraChangeFinish(cameraPosition: CameraPosition) {
                searchList(cameraPosition.target.latitude, cameraPosition.target.longitude)
            }
        })
    }

    private fun searchList(lat: Double, lng: Double) {
        poiQuery = PoiSearch.Query("", "", "")
        poiQuery!!.pageSize = 30
        poiQuery!!.pageNum = 0
        val poiSearch = PoiSearch(this, poiQuery)
        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(lat, lng), 500)
        poiSearch.setOnPoiSearchListener(onPoiSearchListener)
        poiSearch.searchPOIAsyn()
    }

    private fun dimQueryAddress(str: String) {
        poiQuery = PoiSearch.Query(str, "", "")
        poiQuery!!.pageSize = 30
        poiQuery!!.pageNum = 0
        val search = PoiSearch(this, poiQuery)
        search.setOnPoiSearchListener(onPoiSearchListener)
        search.searchPOIAsyn()
    }

    private fun addMark() {
        marker = aMap!!.addMarker(
            MarkerOptions().icon(
                BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.home_location
                    )
                )
            ).draggable(true)
        )
        val vto = map.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                map.viewTreeObserver.removeGlobalOnLayoutListener(this)
                marker!!.setPositionByPixels(map.width shr 1, map.height shr 1)
            }
        })
    }

    //索引搜索
    private var onPoiSearchListener: PoiSearch.OnPoiSearchListener = object : PoiSearch.OnPoiSearchListener {
        override fun onPoiSearched(result: PoiResult?, rCode: Int) {
            if (rCode == 1000) {
                if (result != null && result.query != null) {// 搜索poi的结果
                    if (result.query == poiQuery) {// 是否是同一条
                        //     poiResult = result;

                        // 取得搜索到的poiitems有多少页
                        val poiItems = result.pois// 取得第一页的poiitem数据，页数从数字0开始

                        val models = mutableListOf<SearchAddressModel>()

                        poiItems.forEachWithIndex { i, poiItem ->
                            if (i == 0) {
                                models.add(SearchAddressModel(poiItem).apply { checked = true })
                            } else models.add(SearchAddressModel(poiItem))
                        }

                        rv_address.baseAdapter.models = models
                        rv_address.baseAdapter.setChecked(0, true)
                        rv_address.scrollToPosition(0)
                    }
                }
            }
        }

        override fun onPoiItemSearched(poiItem: PoiItem, i: Int) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val poiItem = data.getParcelableExtra<PoiItem>("data")
            rv_address.baseAdapter.setChecked(0, true)
            rv_address.scrollToPosition(0)

            aMap!!.moveCamera(
                CameraUpdateFactory.changeLatLng(
                    LatLng(
                        poiItem!!.latLonPoint.latitude,
                        poiItem.latLonPoint.longitude
                    )
                )
            )

            dimQueryAddress(poiItem.snippet)
        }
    }
}
