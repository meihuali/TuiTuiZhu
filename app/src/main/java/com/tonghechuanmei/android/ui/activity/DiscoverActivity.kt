package com.tonghechuanmei.android.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.designer.library.base.SwipeBackActivity
import com.designer.library.widget.slidecard.CardAdapter
import com.designer.library.widget.slidecard.CardSlidePanel
import com.tonghechuanmei.android.R
import com.tonghechuanmei.android.databinding.ActivityDiscoverBinding
import com.tonghechuanmei.android.model.CardDataItem
import kotlinx.android.synthetic.main.activity_discover.*
import java.util.*

/**
 * Author     : shandirong
 * Date       : 2018/11/20 17:00
 */
class DiscoverActivity : SwipeBackActivity<ActivityDiscoverBinding>() {

    private var cardSwitchListener: CardSlidePanel.CardSwitchListener? = null

    private val imagePaths = arrayOf(
        "file:///android_asset/wall01.jpg",
        "file:///android_asset/wall02.jpg",
        "file:///android_asset/wall03.jpg",
        "file:///android_asset/wall04.jpg",
        "file:///android_asset/wall05.jpg",
        "file:///android_asset/wall06.jpg",
        "file:///android_asset/wall07.jpg",
        "file:///android_asset/wall08.jpg",
        "file:///android_asset/wall09.jpg",
        "file:///android_asset/wall10.jpg",
        "file:///android_asset/wall11.jpg",
        "file:///android_asset/wall12.jpg"
    ) // 12个图片资源

    private val names =
        arrayOf("郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰", "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟") // 12个人名

    private val dataList = ArrayList<CardDataItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
    }

    override fun initView() {
        cardSwitchListener = object : CardSlidePanel.CardSwitchListener {
            override fun onShow(index: Int) {
                Log.d("Card", "正在显示-" + dataList[index].userName)
            }

            override fun onCardVanish(index: Int, type: Int) {
                Log.d("Card", "正在消失-" + dataList[index].userName + " 消失type=" + type)
            }
        }

        image_slide_panel.setCardSwitchListener(cardSwitchListener)

        image_slide_panel.adapter = object : CardAdapter() {
            override fun getLayoutId(): Int {
                return R.layout.item_discover_card
            }

            override fun getCount(): Int {
                return dataList.size
            }

            override fun bindView(view: View?, index: Int) {
                val tag = view!!.tag
                val viewHolder: ViewHolder
                if (null != tag) {
                    viewHolder = tag as ViewHolder
                } else {
                    viewHolder = ViewHolder(view)
                    view.tag = viewHolder
                }

                viewHolder.bindData(dataList[index])
            }

            override fun getItem(index: Int): Any {
                return dataList[index]
            }

            override fun obtainDraggableArea(view: View): Rect {
                // 可滑动区域定制，该函数只会调用一次
                val contentView = view.findViewById<View>(R.id.card_item_content)
                val topLayout = view.findViewById<View>(R.id.card_top_layout)
                val bottomLayout = view.findViewById<View>(R.id.card_bottom_layout)
                val left = view.left + contentView.paddingLeft + topLayout.paddingLeft
                val right = view.right - contentView.paddingRight - topLayout.paddingRight
                val top = view.top + contentView.paddingTop + topLayout.paddingTop
                val bottom = view.bottom - contentView.paddingBottom - bottomLayout.paddingBottom
                return Rect(left, top, right, bottom)
            }
        }
        val handler = Handler()
        handler.postDelayed({
            prepareDataList()
            image_slide_panel.adapter.notifyDataSetChanged()
        }, 500)
    }

    override fun initData() {

    }

    private fun prepareDataList() {
        for (i in 0..11) {
            val dataItem = CardDataItem()
            dataItem.userName = names[i]
            dataItem.imagePath = imagePaths[i]
            dataItem.likeNum = (Math.random() * 10).toInt()
            dataItem.imageNum = (Math.random() * 6).toInt()
            dataList.add(dataItem)
        }
    }

    internal inner class ViewHolder(view: View) {

        var imageView: ImageView = view.findViewById<View>(R.id.card_image_view) as ImageView
        var maskView: View = view.findViewById(R.id.maskView)
        var userNameTv: TextView = view.findViewById<View>(R.id.card_user_name) as TextView
        var imageNumTv: TextView = view.findViewById<View>(R.id.card_pic_num) as TextView
        var likeNumTv: TextView = view.findViewById<View>(R.id.card_like) as TextView

        fun bindData(itemData: CardDataItem) {
            Glide.with(this@DiscoverActivity).load(itemData.imagePath).into(imageView)
            userNameTv.text = itemData.userName
            imageNumTv.text = itemData.imageNum.toString() + ""
            likeNumTv.text = itemData.likeNum.toString() + ""
        }
    }
}
