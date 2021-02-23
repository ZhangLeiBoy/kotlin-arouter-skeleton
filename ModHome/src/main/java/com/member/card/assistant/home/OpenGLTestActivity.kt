package com.member.card.assistant.home

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.member.card.assistant.base.BaseActivity
import com.member.card.assistant.util.RouterHome

@Route(path = RouterHome.openGLTest)
class OpenGLTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity_opengl)
        topBar.setTitle("OpenGL")


    }
}
