package com.member.card.assistant.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.flyco.tablayout.listener.OnTabSelectListener
import com.flyco.tablayout.utils.UnreadMsgUtils
import com.flyco.tablayout.widget.MsgView
import com.member.card.assistant.R
import java.util.*

class TabLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private val mTabEntities = ArrayList<TabEntity>()
    private var mContext: Context? = null
    private var mTabsContainer: LinearLayout? = null
    private var mCurrentTab = 0
    private var mTabCount = 0

    private var mHeightCommon = 0
    private var mTextSelectColor = 0
    private var mTextUnSelectColor = 0
    private var mTextSize = 0f
    private var mIconSizeNormal = 0

    init {
        setWillNotDraw(false) //重写onDraw方法,需要调用这个方法来清除flag
        clipChildren = false
        clipToPadding = false
        mContext = context
        mTextSelectColor = resources.getColor(R.color.app_text_main)
        mTextUnSelectColor = resources.getColor(R.color.app_text_grey)
        mTextSize = 10f
        mHeightCommon = SizeUtils.dp2px(50f)
        mIconSizeNormal = SizeUtils.dp2px(20f)
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.view_tab_layout, null)
        mTabsContainer = view.findViewById(R.id.tab_container)
        addView(view)
    }

    /**
     * @param tabEntities 数据源
     */
    fun setTabData(tabEntities: ArrayList<TabEntity>?) {
        check(!(tabEntities == null || tabEntities.size == 0)) { "TabEntities can not be NULL or EMPTY !" }
        mTabEntities.clear()
        mTabEntities.addAll(tabEntities)
        notifyDataSetChanged()
    }

    fun removeAllTab() {
        mTabsContainer!!.removeAllViews()
    }

    /**
     * 更新数据
     */
    fun notifyDataSetChanged() {
        mTabsContainer!!.removeAllViews()
        mTabCount = mTabEntities.size
        var tabView: View
        for (i in 0 until mTabCount) {
            tabView = View.inflate(mContext, R.layout.layout_tab_top, null)
            tabView.tag = i
            addTab(i, tabView)
        }
        updateTabStyles()
    }

    /**
     * 创建并添加tab
     */
    private fun addTab(position: Int, tabView: View) {
        val tabEntity = mTabEntities[position]
        val tv_tab_title = tabView.findViewById<TextView>(R.id.tv_tab_title)
        tv_tab_title.text = tabEntity.tabTitle
        val iv_tab_icon =
            tabView.findViewById<ImageView>(R.id.iv_tab_icon)
        iv_tab_icon.setImageResource(tabEntity.tabUnselectedIcon)
        tabView.setOnClickListener { v: View ->
            val position1 = v.tag as Int
            if (mCurrentTab != position1) {
                setCurrentTab(position1)
                if (mListener != null) {
                    mListener!!.onTabSelect(position1)
                }
            } else {
                if (mListener != null) {
                    mListener!!.onTabReselect(position1)
                }
            }
        }
        val lp_tab =
            LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f)
        mTabsContainer!!.addView(tabView, position, lp_tab)
    }

    private fun updateTabStyles() {
        for (i in 0 until mTabCount) {
            val tabEntity = mTabEntities[i]
            var tabView: View
            var lp: LinearLayout.LayoutParams
            tabView = mTabsContainer!!.getChildAt(i)
            lp = LinearLayout.LayoutParams(mIconSizeNormal, mIconSizeNormal)
            lp.bottomMargin = SizeUtils.dp2px(2f)
            val tv_tab_title = tabView.findViewById<TextView>(R.id.tv_tab_title)
            tv_tab_title.setTextColor(if (i == mCurrentTab) mTextSelectColor else mTextUnSelectColor)
            tv_tab_title.textSize = mTextSize
            val iv_tab_icon =
                tabView.findViewById<ImageView>(R.id.iv_tab_icon)
            iv_tab_icon.visibility = View.VISIBLE
            iv_tab_icon.layoutParams = lp
            iv_tab_icon.setImageResource(if (i == mCurrentTab) tabEntity.tabSelectedIcon else tabEntity.tabUnselectedIcon)
        }
    }

    private fun updateTabSelection(position: Int) {
        for (i in 0 until mTabCount) {
            var tabView: View
            var lp: LinearLayout.LayoutParams
            tabView = mTabsContainer!!.getChildAt(i)
            lp = LinearLayout.LayoutParams(mIconSizeNormal, mIconSizeNormal)
            lp.bottomMargin = SizeUtils.dp2px(0f)
            val isSelect = i == position
            val tab_title = tabView.findViewById<TextView>(R.id.tv_tab_title)
            tab_title.setTextColor(if (isSelect) mTextSelectColor else mTextUnSelectColor)
            val iv_tab_icon =
                tabView.findViewById<ImageView>(R.id.iv_tab_icon)
            val tabEntity = mTabEntities[i]
            iv_tab_icon.setImageResource(if (isSelect) tabEntity.tabSelectedIcon else tabEntity.tabUnselectedIcon)
            if (!isSelect) {
                tab_title.visibility = View.VISIBLE
            } else {
                iv_tab_icon.visibility = View.VISIBLE
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isInEditMode || mTabCount <= 0) {
            return
        }
    }

    //setter and getter
    fun setCurrentTab(currentTab: Int) {
        val tabEntity = mTabEntities[currentTab]
        //        if (!tabEntity.isHideTab()) {
        mCurrentTab = currentTab
        updateTabSelection(currentTab)
        invalidate()
//        }
    }

    //setter and getter

    //setter and getter
    // show MsgTipView
    private val mTextPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)
    private val mInitSetMap = SparseArray<Boolean?>()

    /**
     * 显示未读消息
     *
     * @param position 显示tab位置
     * @param num      num小于等于0显示红点,num大于0显示数字
     */
    fun showMsg(position: Int, num: Int) {
        var position = position
        if (position >= mTabCount) {
            position = mTabCount - 1
        }
        val tabView = mTabsContainer!!.getChildAt(position)
        val tipView: MsgView = tabView.findViewById(R.id.rtv_msg_tip)
        if (tipView != null) {
            UnreadMsgUtils.show(tipView, num)
            if (mInitSetMap[position] != null && mInitSetMap[position]!!) {
                return
            }
            setMsgMargin(position, 0f, 0f)
            mInitSetMap.put(position, true)
        }
    }

    fun hideMsg(position: Int) {
        var position = position
        if (position >= mTabCount) {
            position = mTabCount - 1
        }
        val tabView = mTabsContainer!!.getChildAt(position)
        if (tabView != null) {
            val tipView: MsgView = tabView.findViewById(R.id.rtv_msg_tip)
            if (tipView != null) {
                tipView.visibility = View.GONE
            }
        }
    }

    /**
     * 设置提示红点偏移,注意
     * 1.控件为固定高度:参照点为tab内容的右上角
     * 2.控件高度不固定(WRAP_CONTENT):参照点为tab内容的右上角,此时高度已是红点的最高显示范围,所以这时bottomPadding其实就是topPadding
     */
    fun setMsgMargin(
        position: Int,
        leftPadding: Float,
        bottomPadding: Float
    ) {
        var position = position
        if (position >= mTabCount) {
            position = mTabCount - 1
        }
        val tabView = mTabsContainer!!.getChildAt(position)
        val tipView: MsgView = tabView.findViewById(R.id.rtv_msg_tip)
        if (tipView != null) {
            //TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
            mTextPaint.textSize = mTextSize
            val textHeight = mTextPaint.descent() - mTextPaint.ascent()
            val lp = tipView.layoutParams as MarginLayoutParams
            val iconH = mIconSizeNormal.toFloat() //= mIconHeight;
            val margin = 0f
            lp.leftMargin = SizeUtils.dp2px(leftPadding)
            lp.topMargin =
                (mHeightCommon - textHeight - iconH - margin).toInt() / 2 - SizeUtils.dp2px(bottomPadding)
            tipView.layoutParams = lp
        }
    }

    private var mListener: OnTabSelectListener? = null

    fun setOnTabSelectListener(listener: OnTabSelectListener?) {
        mListener = listener
    }


    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("instanceState", super.onSaveInstanceState())
        bundle.putInt("mCurrentTab", mCurrentTab)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var state = state
        if (state is Bundle) {
            val bundle = state
            mCurrentTab = bundle.getInt("mCurrentTab")
            state = bundle.getParcelable("instanceState")
            if (mCurrentTab != 0 && mTabsContainer!!.childCount > 0) {
                updateTabSelection(mCurrentTab)
            }
        }
        super.onRestoreInstanceState(state)
    }
}
