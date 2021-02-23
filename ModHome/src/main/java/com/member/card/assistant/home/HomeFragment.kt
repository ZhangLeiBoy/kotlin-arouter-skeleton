package com.member.card.assistant.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.member.card.assistant.base.BaseFragment
import com.member.card.assistant.home.adapter.HomeAdapter
import com.member.card.assistant.home.model.HomeItemModel
import com.member.card.assistant.util.RouterHome
import com.member.card.assistant.view.ShadowLayout
import kotlinx.android.synthetic.main.home_fragment_main.*
import org.jetbrains.anko.textColor

@Route(path = RouterHome.homeMain)
class HomeFragment : BaseFragment() {
    private var data = ArrayList<HomeItemModel>()


    override fun getContentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.home_fragment_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData(){
        data.add(HomeItemModel("Scan",R.drawable.icon_home_left_scan,"二维码扫描",""))
        data.add(HomeItemModel("OpenGL",R.drawable.icon_home_left_ticket,"openGLTest",RouterHome.openGLTest))
        data.add(HomeItemModel("OpenGL",R.drawable.icon_home_left_activity,"openGLTest",RouterHome.openGLTest))
        data.add(HomeItemModel("OpenGL",R.drawable.icon_home_left_scan,"openGLTest",RouterHome.openGLTest))
        data.add(HomeItemModel("OpenGL",R.drawable.icon_home_left_ticket,"openGLTest",RouterHome.openGLTest))
        data.add(HomeItemModel("OpenGL",R.drawable.icon_home_left_vip,"openGLTest",""))
    }

    private fun initListener() {
        (sdl_top.layoutParams as RelativeLayout.LayoutParams).topMargin = topBar.contentHeight()
        home_rcy.layoutManager = LinearLayoutManager(context)
        val adapter = HomeAdapter()
        home_rcy.adapter = adapter
        adapter.list = data

        info_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
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

    override fun initActionBar() {
        super.initActionBar()
        belowActionBar = false
        topBar.showTransparentBackground()
        topBar.removeLeftMenu()
        topBar.getTitleView().apply {
            text = "Demo测试"
            textColor = ColorUtils.getColor(R.color.white)
        }
    }
}
