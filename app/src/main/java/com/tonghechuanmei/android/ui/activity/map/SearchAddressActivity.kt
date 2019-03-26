package com.tonghechuanmei.android.ui.activity.map

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.designer.library.base.SwipeBackActivity
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityAddressSearchBinding
import com.tonghechuanmei.android.ui.adapter.SearchAddressAdapter
import kotlinx.android.synthetic.main.activity_address_search.*
import java.util.*

/**
 * Created by shandirong on 2017/12/8.
 */

open class SearchAddressActivity : SwipeBackActivity<ActivityAddressSearchBinding>(), PoiSearch.OnPoiSearchListener {

    var mLocationClient: AMapLocationClient? = null
    var mLocationOption = AMapLocationClientOption()
    var mLocationListener: AMapLocationListener = AMapLocationListener { aMapLocation ->
        if (aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {
                address_search_et.isEnabled = true
                lat = aMapLocation.latitude
                lng = aMapLocation.longitude
            }
            println("onLocationChanged------->" + aMapLocation.toStr())
        }
    }
    private var poiSearch: PoiSearch? = null
    private var query: PoiSearch.Query? = null
    private var lat: Double = 0.toDouble()
    private var lng: Double = 0.toDouble()
    private var mAdapter: SearchAddressAdapter? = null
    private var poiItems: ArrayList<PoiItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_search)
        initMap()
        mAdapter = SearchAddressAdapter(this)
        address_search_list_view.adapter = mAdapter

        address_search_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (!address_search_et.text.toString().isEmpty()) {
                    doSearchQuery(address_search_et.text.toString())
                } else {
                    mAdapter!!.setList(null)
                }
            }
        })
    }

    private fun initMap() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient!!.setLocationListener(mLocationListener)
        mLocationOption.isOnceLocation = true
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationClient!!.setLocationOption(mLocationOption)
        mLocationClient!!.startLocation()
    }

    protected fun doSearchQuery(content: String) {
        val mType =
            "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施"
        query = PoiSearch.Query(content, "", "")// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query!!.pageSize = 20// 设置每页最多返回多少条poiitem
        query!!.pageNum = 1// 设置查第一页
        query!!.isDistanceSort = true
        poiSearch = PoiSearch(this, query)
        poiSearch!!.setOnPoiSearchListener(this)
        // AMapInputTipsSearchRequest
        //以当前定位的经纬度为准搜索周围5000米范围
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch!!.bound = PoiSearch.SearchBound(LatLonPoint(lat, lng), 99999999, true)//

        poiSearch!!.searchPOIAsyn()// 异步搜索
    }

    override fun onPoiSearched(poiResult: PoiResult?, i: Int) {
        if (i == 1000) {
            if (poiResult != null && poiResult.query != null) {// 搜索poi的结果
                poiItems = poiResult.pois// 取得第一页的poiitem数据，页数从数字0开始
                mAdapter!!.setList(poiItems)
            }
        }
    }

    override fun onPoiItemSearched(poiItem: PoiItem, i: Int) {

    }

    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
        address_search_list_view.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent()
            intent.putExtra("data", poiItems!![position])
            setResult(100, intent)
            finish()
        }
    }

    override fun initData() {

    }
}
