package com.app.kotlin

import android.app.Application
import android.content.Context
import java.lang.reflect.Method

object App {
    private var application:Application? = null

    /**
     * 初始化application
     */
    fun initApplication(baseApp:Application?) {
        application = baseApp
    }

    /**
     * 获取ApplicationContext
     */
    val context:Context? get() = getApplication()

    /**
     * Get application application.
     */
    @Synchronized
    fun getApplication():Application? {
        return application.orNull {loaderApplication()?.apply {application = this}}
    }

    private fun loaderApplication():Application? {
        var method:Method?
        try {
            method = Class.forName("android.app.AppGlobals").getJavaMethod("getInitialApplication")
            return method?.invoke(null) as Application
        } catch(e:Exception) {
            try {
                method = Class.forName("android.app.ActivityThread")
                    .getJavaMethod("currentApplication")
                return method?.invoke(null) as Application
            } catch(ex:Exception) {
                ex.printStackTrace()
            }
        }
        return null
    }
}