package com.member.card.assistant.util

import com.blankj.utilcode.util.LogUtils
import com.member.card.assistant.base.App
import java.io.File


class Properties {
    companion object {
        var LOG_CRASH = ""
        var PATH_CATCH_IMAGE = ""
        var PATH_DOWNLOAD_FILES = ""

        fun initProperties() {
            LOG_CRASH = App.instance.getExternalFilesDir(null).toString()
            PATH_DOWNLOAD_FILES =
                App.instance.externalCacheDir.toString() + "files" + File.separator
            PATH_CATCH_IMAGE =
                App.instance.externalCacheDir.toString() + "imageCatch" + File.separator
            LogUtils.d("PATH - LOG_CRASH : $LOG_CRASH")
            LogUtils.d("PATH - PATH_DOWNLOAD_FILES : $PATH_DOWNLOAD_FILES")
            LogUtils.d("PATH - PATH_CATCH_IMAGE : $PATH_CATCH_IMAGE")
        }
    }
}