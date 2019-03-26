package com.tonghechuanmei.android.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.designer.library.base.BaseActivity
import com.designer.library.utils.darkMode
import com.designer.library.utils.setStatusBarPadding
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityGuideBinding
import com.tonghechuanmei.android.model.UserModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity
import java.util.*
import java.util.concurrent.TimeUnit

class GuideActivity : BaseActivity<ActivityGuideBinding>() {
    private var mDistance: Int = 0

    private var currentTime = 1L
    override fun initView() {
        darkMode()
        toolbar.setStatusBarPadding()
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        //  countRegDownTimer?.cancel()
        //  countRegDownTimer = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        UserModel.isFirst = false
        iv_light_dots!!.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = in_ll!!.getChildAt(1).left - in_ll!!.getChildAt(0).left
                iv_light_dots!!.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
        val viewList = ArrayList<View>()
        val view1 = LayoutInflater.from(this).inflate(R.layout.we_indicator1, null)
        val view2 = LayoutInflater.from(this).inflate(R.layout.we_indicator2, null)
        val view3 = LayoutInflater.from(this).inflate(R.layout.we_indicator3, null)
        val view4 = LayoutInflater.from(this).inflate(R.layout.we_indicator4, null)
        viewList.add(view1)
        viewList.add(view2)
        viewList.add(view3)
        viewList.add(view4)

        next_btn.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }

        val adapter = ViewPagerAdapter(viewList)
        my_viewpager!!.adapter = adapter
        startCount()
        my_viewpager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                val leftMargin = mDistance * (position + positionOffset)
                val params = iv_light_dots!!.layoutParams as RelativeLayout.LayoutParams
                params.leftMargin = leftMargin.toInt()
                iv_light_dots!!.layoutParams = params
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun startCount() {
        val totalSeconds = 5L
        Observable.intervalRange(0, totalSeconds, 0, 1, TimeUnit.SECONDS)
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .map {
                val temp = totalSeconds - it
                next_btn.text = "跳过${temp}s"
                currentTime = temp
                temp
            }.filter {
                it == 1L
            }.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)))
            .subscribe {
                startActivity<LoginActivity>()
                finish()
            }
    }

    private inner class ViewPagerAdapter internal constructor(private val mViewList: List<View>) : PagerAdapter() {

        override fun getCount(): Int {
            return mViewList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(mViewList[position])
            return mViewList[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(mViewList[position])
        }
    }
}
