package com.app.kotlin

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

/**
 * 获取Activity根节点View
 */
inline fun Activity.rootView():View = (findViewById<ViewGroup>(R.id.content)).getChildAt(0)

/**
 * 获取Activity根节点Group
 */
inline fun Activity.rootViewGroup():ViewGroup = findViewById<ViewGroup>(R.id.content)

/**
 * 设置Activity全屏
 */
inline fun Activity.fullScreen() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

/**
 * 屏幕常亮
 * 需要权限SCREEN_BRIGHT_WAKE_LOCK
 */
inline fun Activity.keepScreenOn() {
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

/**
 * 打开一个界面
 */
inline fun Context.startActivity(clazz:Class<out Activity?>) {
    startActivity(clazz, null)
}

/**
 * 打开一个界面
 */
inline fun Context.startActivity(clazz:Class<out Activity?>, extras:Bundle?) {
    startActivity(Intent(this, clazz).apply {extras?.let {putExtras(extras)}})
}

