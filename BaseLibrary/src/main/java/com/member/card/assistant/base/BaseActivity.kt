package com.member.card.assistant.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.member.card.assistant.actionbar.ActionBarClick
import com.member.card.assistant.actionbar.ActionBarConstants
import com.member.card.assistant.actionbar.ActionBarEx
import org.jetbrains.anko.backgroundResource

open class BaseActivity : AppCompatActivity(), ActionBarClick {
    lateinit var topBar: ActionBarEx
    lateinit var contentView: RelativeLayout
    var belowActionBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentView = RelativeLayout(this)
        topBar = ActionBarEx(this)
        topBar.id = ActionBarConstants.actionBarId
        topBar.setMenuClickCallBack(this)

        contentView.addView(topBar)
        actionBarInit()

        super.setContentView(contentView)
        //属性设置
        immersionBar {
            removeSupportAllView()
            statusBarDarkFont(false)
            //titleBar(topBar)
        }
    }

    open fun actionBarInit() {
        //topBar.background = resources.getDrawable(R.drawable.actionbar_shape, null)
        topBar.setBackgroundResource(R.drawable.nav_bg)
    }

    override fun setContentView(layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null))
    }

    override fun setContentView(view: View?) {
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        if (belowActionBar)
            lp.addRule(RelativeLayout.BELOW, ActionBarConstants.actionBarId)
        contentView.addView(view, 0, lp)
    }

    override fun menuClick(menuId: Int, clickView: View) {
        when (menuId) {
            ActionBarConstants.back, ActionBarConstants.cancel, ActionBarConstants.close -> finish()
        }
    }
}
