package com.member.card.assistant.actionbar

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.immersionbar.ImmersionBar
import com.member.card.assistant.base.R
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.textColor
import kotlin.apply

class ActionBarEx(context: Context?) : FrameLayout(context!!) {

    var titleTextSize = 18f
    var menuTextSize = 17f
    var statusBarHeight = SizeUtils.dp2px(15f)
    var barHeight = 0
    private var content: RelativeLayout
    private var barBgImage: ImageView
    private var left: LinearLayout
    private var center: LinearLayout
    private var right: LinearLayout
    private lateinit var titleView: TextView

    //菜单点击回调
    private var clickCallback: ActionBarClick? = null
    fun setMenuClickCallBack(clickCallback: ActionBarClick?) {
        this.clickCallback = clickCallback
    }

    init {
        if (context is Activity) {
            statusBarHeight = ImmersionBar.getStatusBarHeight(context)
        } else if (context is Fragment) {
            statusBarHeight = ImmersionBar.getStatusBarHeight(context)
        }
        addView(LayoutInflater.from(context).inflate(R.layout.actionbar_ex, null))
        barHeight = SizeUtils.dp2px(45f)
        layoutParams =
            ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, barHeight + statusBarHeight)
        content = this.find(R.id.ex_content)
        (content.layoutParams as FrameLayout.LayoutParams).topMargin = statusBarHeight
        barBgImage = this.find(R.id.ex_bar_iv)
        left = this.find(R.id.ex_left)
        left.layoutParams.height = barHeight
        center = this.find(R.id.ex_center)
        center.layoutParams.height = barHeight
        right = this.find(R.id.ex_right)
        right.layoutParams.height = barHeight
        addLeftBackMenu()
        addCenterTitleText()
    }


    override fun setBackground(background: Drawable?) {
        //super.setBackground(background)
        barBgImage.background = background
    }

    override fun setBackgroundColor(color: Int) {
        //super.setBackgroundColor(color)
        barBgImage.imageResource = color
    }

    override fun setBackgroundResource(resid: Int) {
        //super.setBackgroundResource(resid)
        barBgImage.imageResource = resid
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        //super.setBackgroundDrawable(background)
    }

    fun showTransparentBackground() {
        barBgImage.alpha = 0f
    }

    fun changeBackgroundAlpha(_alpha: Float) {
        when {
            _alpha > 1 -> {
                barBgImage.alpha = 1f
            }
            _alpha < 0 -> {
                barBgImage.alpha = 0f
            }
            else -> {
                barBgImage.alpha = _alpha
            }
        }
    }

    fun contentHeight(): Int {
        if (context is Activity) {
            return barHeight + ImmersionBar.getStatusBarHeight(context as Activity)
        } else if (context is Fragment) {
            return barHeight + ImmersionBar.getStatusBarHeight(context as Fragment)
        }
        return barHeight
    }

    private fun addLeftBackMenu() {
        left.addView(ImageView(context).apply {
            imageResource = R.drawable.nav_back_white
            tag = ActionBarConstants.back
            onClick {
                clickCallback?.menuClick(ActionBarConstants.back, this@apply)
            }
            padding = SizeUtils.dp2px(12f)
        }, LinearLayout.LayoutParams(barHeight, barHeight))
    }

    private fun addCenterTitleText() {
        titleView = TextView(context).apply {
            gravity = Gravity.CENTER
            textColor = ColorUtils.getColor(R.color.app_nav_title)
            textSize = titleTextSize
        }
        center.addView(
            titleView,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    fun getTitleView(): TextView {
        return titleView
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun removeLeftMenu() {
        left.removeAllViews()
    }

    fun removeAllRightMenu() {
        right.removeAllViews()
    }

    fun addMenu(mid: Int, @DrawableRes drawableId: Int) {
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(barHeight, barHeight)
        params.setMargins(0, 0, SizeUtils.dp2px(10f), 0)
        right.addView(ImageView(context).apply {
            imageResource = drawableId
            tag = mid
            onClick {
                clickCallback?.menuClick(mid, this@apply)
            }
            padding = SizeUtils.dp2px(12f)
        }, params)
    }

    fun addMenu(mid: Int, view: View) {

    }

    fun hideMenu(mid: Int) {
        val view: View? = right.findViewWithTag(mid)
        view?.let {
            view.visibility = View.GONE
        }
    }

    fun showMenu(mid: Int) {
        val view: View? = right.findViewWithTag(mid)
        view?.let {
            view.visibility = View.VISIBLE
        }
    }
}