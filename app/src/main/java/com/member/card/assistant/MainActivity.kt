package com.member.card.assistant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.flyco.tablayout.listener.OnTabSelectListener
import com.member.card.assistant.base.BaseActivity
import com.member.card.assistant.util.RouterHome
import com.member.card.assistant.util.RouterMain
import com.member.card.assistant.util.RouterUser
import com.member.card.assistant.view.SimplePagerAdapter
import com.member.card.assistant.widget.TabEntity
import kotlinx.android.synthetic.main.activity_main.*


//ARouter.getInstance().build(RouterMain.second).navigation()
@Route(path = RouterMain.main)
class MainActivity : BaseActivity() {

    private val fragments = ArrayList<Fragment>()
    private val tabEntities: ArrayList<TabEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topBar.visibility = View.GONE
        fragments.add(ARouter.getInstance().build(RouterHome.homeMain).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RouterUser.userHome).navigation() as Fragment)
        tabEntities.add(
            TabEntity(
                "首页",
                R.drawable.main_home_focused,
                R.drawable.main_home_normal
            )
        )
        tabEntities.add(
            TabEntity(
                "个人中心",
                R.drawable.main_usercenter_focused,
                R.drawable.main_usercenter_normal
            )
        )
        main_viewpager.adapter = SimplePagerAdapter(supportFragmentManager, fragments)
        main_tab.setTabData(tabEntities)
        main_viewpager.currentItem = 0
        main_tab.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                main_viewpager.currentItem = position
                main_tab.setCurrentTab(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }
}
