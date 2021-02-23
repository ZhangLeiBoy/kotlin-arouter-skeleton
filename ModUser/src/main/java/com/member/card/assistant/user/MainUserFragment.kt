package com.member.card.assistant.user

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ColorUtils
import com.member.card.assistant.actionbar.ActionBarConstants
import com.member.card.assistant.base.BaseFragment
import com.member.card.assistant.util.RouterUser
import com.member.card.assistant.view.roundediv.RoundedImageView
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

@Route(path = RouterUser.userHome)
class MainUserFragment : BaseFragment() {
    private lateinit var riv: RoundedImageView
    private lateinit var scrollView: NestedScrollView

    override fun getContentView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.user_fragment_home, null)
        initLayout(view)
        initListener()
        return view
    }

    override fun initActionBar() {
        super.initActionBar()
        belowActionBar = false
        topBar.showTransparentBackground()
        topBar.removeLeftMenu()
        topBar.getTitleView().apply {
            text = "个人中心"
            textColor = ColorUtils.getColor(R.color.white)
        }
        topBar.addMenu(ActionBarConstants.setting, R.drawable.nav_setting_white)
    }

    override fun menuClick(menuId: Int, clickView: View) {
        when (menuId) {
            ActionBarConstants.setting -> ARouter.getInstance().build(RouterUser.userSetting)
                .navigation()
            else -> super.menuClick(menuId, clickView)
        }
    }

    private fun initLayout(view: View) {
        riv = view.find(R.id.riv_head)
        (riv.layoutParams as RelativeLayout.LayoutParams).topMargin = topBar.contentHeight()
        scrollView = view.find(R.id.info_scroll_view)
    }

    private fun initListener() {
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            val height = topBar.barHeight
            when {
                scrollY <= 0 -> {
                    topBar.changeBackgroundAlpha(0f)
                }
                scrollY in 1 until height -> {
                    val alpha: Float = scrollY * 1.0f / height
                    topBar.changeBackgroundAlpha(alpha)
                }
                else -> {
                    topBar.changeBackgroundAlpha(1f)
                }
            }
        })
    }
}
