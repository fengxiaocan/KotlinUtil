package com.app.kotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * 根据包名打开应用的设置界面
 */
inline fun Context.openAppSetting(packageName:String) {
    var intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

/**
 * 打开系统的设置界面
 */
inline fun Context.openSystemSetting() = startActivity(Intent().apply {action = Settings.ACTION_SETTINGS})

/**
 * 打开系统的无障碍界面
 */
inline fun Context.openAccessibleSetting() = startActivity(Intent().apply {action = Settings.ACTION_ACCESSIBILITY_SETTINGS})

/**
 * 打开系统的Wifi 设置界面
 */
inline fun Context.openWifiSetting() = startActivity(Intent().apply {action = Settings.ACTION_WIFI_SETTINGS})

/**
 * 打开系统的蓝牙界面
 */
inline fun Context.openBluetoothSetting() = startActivity(Intent().apply {action = Settings.ACTION_BLUETOOTH_SETTINGS})


/**
 * 打开系统的显示设置界面
 */
inline fun Context.openDisplaySetting() = startActivity(Intent().apply {action = Settings.ACTION_DISPLAY_SETTINGS})

/**
 * 打开系统的语言设置
 */
inline fun Context.openLocaleSetting() = startActivity(Intent().apply {action = Settings.ACTION_LOCALE_SETTINGS})

/**
 * 打开系统的语言和输入法设置界面
 */
inline fun Context.openInputMethodSetting() = startActivity(Intent().apply {action = Settings.ACTION_INPUT_METHOD_SETTINGS})

/**
 * 打开存储空间设置的界面
 */
inline fun Context.openInternalStorageSetting() = startActivity(Intent().apply {action = Settings.ACTION_INTERNAL_STORAGE_SETTINGS})

/**
 * 打开开发者选项的界面
 */
inline fun Context.openDevelopmentSetting() = startActivity(Intent().apply {action = Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS})

/**
 * 打开手机状态信息的界面
 */
inline fun Context.openDeviceInfoSetting() = startActivity(Intent().apply {action = Settings.ACTION_DEVICE_INFO_SETTINGS})

/**
 * 打开通知使用权设置的界面
 */
inline fun Context.openNotificationSetting() = startActivity(Intent().apply {action = Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS})

/**
 * 打开节电助手界面
 */
inline fun Context.openBatterySaverSetting() = startActivity(Intent().apply {action = Settings.ACTION_BATTERY_SAVER_SETTINGS})

/**
 * 根据包名打开应用的设置界面
 */
inline fun Context.openAppSetting() {
    openAppSetting(this.packageName)
}


