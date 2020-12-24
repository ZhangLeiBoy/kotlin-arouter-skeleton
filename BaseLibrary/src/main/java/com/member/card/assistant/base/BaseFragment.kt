package com.member.card.assistant.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar
import com.member.card.assistant.actionbar.ActionBarClick
import com.member.card.assistant.actionbar.ActionBarConstants
import com.member.card.assistant.actionbar.ActionBarEx

abstract class BaseFragment : Fragment(), ActionBarClick {
    lateinit var topBar: ActionBarEx
    lateinit var contentView: RelativeLayout
    var belowActionBar = true

    abstract fun getContentView(): View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentView = RelativeLayout(context)
        topBar = ActionBarEx(context)
        topBar.id = ActionBarConstants.actionBarId
        topBar.setMenuClickCallBack(this)
        contentView.addView(topBar)

        initActionBar()

        val view = getContentView()
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        if (belowActionBar)
            lp.addRule(RelativeLayout.BELOW, ActionBarConstants.actionBarId)
        contentView.addView(view, 0, lp)

        //属性设置
        immersionBar {
            removeSupportAllView()
            statusBarDarkFont(false)
            //titleBar(topBar)
        }

        return contentView
    }

    open fun initActionBar() {
        //topBar.background = resources.getDrawable(R.drawable.actionbar_shape, null)
        topBar.setBackgroundResource(R.drawable.nav_bg)
    }

    override fun menuClick(menuId: Int, clickView: View) {
        when (menuId) {
            ActionBarConstants.back, ActionBarConstants.cancel, ActionBarConstants.close -> activity?.finish()
        }
    }

}
