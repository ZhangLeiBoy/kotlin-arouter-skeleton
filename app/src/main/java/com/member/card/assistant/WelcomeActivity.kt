package com.member.card.assistant

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.member.card.assistant.util.Properties
import com.member.card.assistant.util.RouterMain
import pub.devrel.easypermissions.EasyPermissions

class WelcomeActivity : AppCompatActivity() {

    private val perms = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        immersionBar {
            removeSupportAllView()
            statusBarDarkFont(true)
            hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
        }

        if (EasyPermissions.hasPermissions(this, *perms)) {
            next()
        } else {
            EasyPermissions.requestPermissions(this, "请授予程序运行所需的基础权限", 11, *perms)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, object :
            EasyPermissions.PermissionCallbacks {
            override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
                ToastUtils.showShort("授权失败!")
            }

            override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
                next()
            }

            override fun onRequestPermissionsResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
            ) {

            }
        })
    }

    private fun next() {
        Properties.initProperties()
        ARouter.getInstance()
            .build(RouterMain.main)
            .withTransition(0, 0)
            .navigation(this, object : NavCallback() {
                override fun onArrival(postcard: Postcard?) {
                    finish()
                }
            })

    }
}
