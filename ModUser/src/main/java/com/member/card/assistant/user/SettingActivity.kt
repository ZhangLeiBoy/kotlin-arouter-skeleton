package com.member.card.assistant.user

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.member.card.assistant.base.BaseActivity
import com.member.card.assistant.util.RouterMain
import com.member.card.assistant.util.RouterUser

@Route(path = RouterUser.userSetting)
class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_activity_setting)
        topBar.setTitle("设置")

    }
}
