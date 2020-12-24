package com.member.card.assistant.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import java.util.*

open class App : Application() {
    private val mActivityRecords = ArrayList<Activity>()

    companion object {
        lateinit var instance: App

        fun inDebug(): Boolean {
            return BuildConfig.DEBUG
        }

        fun apiDebug(): Boolean {
            return false
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (inDebug()) {          // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        Utils.init(instance)
        ARouter.init(instance)
        registerActivityCallback()
    }

    private fun registerActivityCallback() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                mActivityRecords.add(p0)
            }

            override fun onActivityResumed(p0: Activity) {
            }
        })
    }

}
