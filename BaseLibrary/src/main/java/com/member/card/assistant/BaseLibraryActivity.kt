package com.member.card.assistant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.member.card.assistant.actionbar.ActionBarEx
import com.member.card.assistant.base.R

open class BaseLibraryActivity : AppCompatActivity() {
    lateinit var topBar : ActionBarEx
    lateinit var contentView:RelativeLayout
    var belowActionBar = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentView = RelativeLayout(this)
        super.setContentView(contentView)
        topBar = ActionBarEx(this)

        immersionBar {
            removeSupportAllView()
            statusBarDarkFont(true)
            titleBar(topBar)
        }
        //contentView.addView(topBar)
        topBar.background = resources.getDrawable(R.drawable.actionbar_shape,null)
    }

    override fun setContentView(layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID,null))
    }

    override fun setContentView(view: View?) {
        contentView.addView(view)
        contentView.addView(topBar)
    }

}
