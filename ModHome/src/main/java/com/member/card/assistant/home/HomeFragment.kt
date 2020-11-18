package com.member.card.assistant.home

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.member.card.assistant.base.BaseFragment
import com.member.card.assistant.util.RouterHome
import com.member.card.assistant.view.ShadowLayout
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

@Route(path = RouterHome.homeMain)
class HomeFragment : BaseFragment() {
    private lateinit var sdLayout: ShadowLayout
    override fun getContentView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.home_fragment_main, null)
        initLayout(view)
        initListener()
        return view
    }

    private fun initListener() {

    }

    private fun initLayout(view: View) {
        sdLayout = view.find(R.id.sdl_top)
        (sdLayout.layoutParams as RelativeLayout.LayoutParams).topMargin = topBar.contentHeight()


    }

    override fun initActionBar() {
        super.initActionBar()
        belowActionBar = false
        topBar.showTransparentBackground()
        topBar.removeLeftMenu()
        topBar.getTitleView().apply {
            text = "首页"
            textColor = ColorUtils.getColor(R.color.white)
        }
    }
}
