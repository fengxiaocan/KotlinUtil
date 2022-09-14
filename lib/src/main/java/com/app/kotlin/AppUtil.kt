package com.app.kotlin

import android.app.Activity
import android.app.ActivityManager
import android.app.Service
import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Process
import android.text.TextUtils
import android.widget.Toast

/**
 * 获取版本号
 */
inline fun Context.versionCode(packageName:String?):Int {
    val packageInfo = getPackageInfo(packageName) ?: return -1
    return packageInfo.versionCode
}

/**
 * 获取版本号
 */
inline fun Context.versionCode():Int {
    return versionCode(this.packageName)
}

/**
 * 获取版本名称
 */
inline fun Context.versionName(packageName:String?):String? {
    val packageInfo = getPackageInfo(packageName)
    return packageInfo?.versionName
}

/**
 * 获取版本名称
 */
inline fun Context.versionName():String? {
    return versionName(packageName)
}

/**
 * 判断sdk的版本号是否大于等于当前的数字
 */
inline fun Int.compareSdkVersion():Boolean {
    return sdkVersion() >= this
}

/**
 * 获取手机系统SDK版本
 *
 * @return 如API 17 则返回 17
 */
inline fun sdkVersion() = Build.VERSION.SDK_INT

/**
 * 获取包的信息
 * @param packageName
 * @return
 */
inline fun Context.getPackageInfo(packageName:String?):PackageInfo? {
    return getPackageInfo(packageName, 0)
}

/**
 * 获取包的信息
 * @param packageName
 * @return
 */
inline fun Context.getPackageInfo(packageName:String?, flags:Int):PackageInfo? {
    return try {
        packageName?.let {
            packageManager.getPackageInfo(it, flags)
        }
    } catch(e:Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * 获取包的信息
 * @return
 */
inline fun Context.getPackageInfo():PackageInfo? {
    return getPackageInfo(packageName)
}

/**
 * 获取包的信息
 * @return
 */
inline fun Context.getPackageInfo(flags:Int):PackageInfo? {
    return getPackageInfo(packageName, flags)
}

/**
 * 获取已安装Apk文件的源Apk文件
 * @param packageName
 * @return
 */
inline fun Context.getSourceApkPath(packageName:String?):String? {
    val info = getApplicationInfo(packageName)
    return info?.sourceDir
}

/**
 * 获取已安装Apk文件的源Apk文件
 * @return
 */
inline fun Context.getSourceApkPath():String? {
    return getSourceApkPath(packageName)
}

/**
 * 获取ApplicationInfo
 * @return
 */
inline fun Context.getApplicationInfo(packageName:String?, flags:Int):ApplicationInfo? {
    packageName ?: return null
    return packageManager.getApplicationInfo(packageName, flags)
}

/**
 * 获取ApplicationInfo
 * @return
 */
inline fun Context.getApplicationInfo(packageName:String?):ApplicationInfo? {
    return getApplicationInfo(packageName, 0)
}

/**
 * 获取ApplicationInfo
 * @return
 */
inline fun Context.getApplicationInfo():ApplicationInfo? {
    return getApplicationInfo(packageName)
}

/**
 * 获取ApplicationInfo
 * @return
 */
inline fun Context.getApplicationInfo(flags:Int):ApplicationInfo? {
    return getApplicationInfo(packageName, flags)
}

/**
 * 判断App是否是系统应用
 *
 * @param packageName 包名
 * @return `true`: 是<br></br>`false`: 否
 */
inline fun Context.isSystemApp(packageName:String):Boolean {
    val ai:ApplicationInfo? = getApplicationInfo(packageName)
    ai ?: return false
    return ai.flags and ApplicationInfo.FLAG_SYSTEM != 0
}

/**
 * 判断App是否是系统应用
 *
 * @param packageName 包名
 * @return `true`: 是<br></br>`false`: 否
 */
inline fun Context.getAppIcon(packageName:String?):Drawable? {
    val info = getApplicationInfo(packageName)
    return info?.loadIcon(packageManager)
}

/**
 * 获取清单文件信息
 *
 * @param packageName
 * @param key
 * @param defaultValue
 * @return
 */
inline fun Context.getManifestMetaData(packageName:String, key:String, defaultValue:String):String? {
    val appInfo:ApplicationInfo = getApplicationInfo(packageName, PackageManager.GET_META_DATA) ?: return defaultValue
    return appInfo.metaData.getString(key)
}

/**
 * 获取清单文件信息
 *
 * @param key
 * @param defaultValue
 * @return
 */
inline fun Context.getManifestMetaData(key:String, defaultValue:String):String? {
    return getManifestMetaData(packageName, key, defaultValue)
}

/**
 * 卸载apk
 *
 * @param packageName 包名
 */
inline fun Context.uninstallApk(packageName:String) {
    val intent = Intent(Intent.ACTION_DELETE)
    val packageURI = Uri.parse("package:$packageName")
    intent.data = packageURI
    this.startActivity(intent)
}

/**
 * 安装apk
 * Android 7.0 或更高版本的应用私有目录被限制访问
 * @param file    APK文件
 * @paras authority provider->authority属性
 */
inline fun Context.installApk(file:Uri) {
    val intent = Intent().apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        action = Intent.ACTION_VIEW
    }.setDataAndType(file, "application/vnd.android.package-archive")
    startActivity(intent)
}

/**
 * 获取本应用签名
 * @return 返回应用的签名
 */
inline fun Context.getAppSignatures():String? {
    return getAppSignatures(packageName)
}

/**
 * 获取指定包名应用的签名
 * @return 返回应用的签名
 */
inline fun Context.getAppSignatures(packageName:String?):String? {
    val info:PackageInfo? = getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
    return info?.let {
        it.signatures[0].toByteArray().toHex()
    }
}

/**
 * 停止运行服务
 * @param className 类名
 * @return 是否执行成功
 */
inline fun Context.stopRunningService(className:String):Boolean {
    return try {
        this.stopRunningService(Class.forName(className))
    } catch(e:Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * 停止运行服务
 * @param clazz 类
 * @return 是否执行成功
 */
inline fun Context.stopRunningService(clazz:Class<*>?):Boolean {
    var service = Intent(this, clazz)
    return stopService(service)
}

/**
 * 判断是否是相同的进程
 *
 * @param processName 进程名
 * @return 是否含有当前的进程
 */
inline fun Context.isSameProcess(processName:String):Boolean {
    if(TextUtils.isEmpty(processName)) {
        return false
    }
    val pid = Process.myPid()
    val manager:ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = manager.runningAppProcesses ?: return true
    for(processInfo in runningAppProcesses) {
        if(processInfo.pid == pid && processName.equals(processInfo.processName, ignoreCase = true)) {
            return true
        }
    }
    return false
}

/**
 * APK版本比较
 *
 * @param info 下载的apk信息
 * @return 下载的apk版本号大于当前版本号时返回true，反之返回false
 */
inline fun Context.compareApkInfo(info:PackageInfo?):Boolean {
    info ?: return false
    return info.packageName == packageName && info.versionCode > this.versionCode()
}

/**
 * 获取activity的MetaData信息
 */
inline fun Context.getActivityMetaData(activity:Activity, key:String):String? {
    return getActivityMetaData(activity.javaClass, key)
}

/**
 * 获取activity的MetaData信息
 */
inline fun Context.getActivityMetaData(clz:Class<out Activity?>, key:String):String? {
    return try {
        val ai = packageManager.getActivityInfo(ComponentName(this, clz), PackageManager.GET_META_DATA)
        ai.metaData[key].toString()
    } catch(e:PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

/**
 * 获取Service的MetaData信息
 */
inline fun Context.getServiceMetaData(service:Service, key:String):String? {
    return getServiceMetaData(service.javaClass, key)
}

/**
 * 获取Service的MetaData信息
 */
inline fun Context.getServiceMetaData(clz:Class<out Service?>, key:String):String? {
    return try {
        val ai = packageManager.getServiceInfo(ComponentName(this, clz), PackageManager.GET_META_DATA)
        ai.metaData[key].toString()
    } catch(e:PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

/**
 * 获取Receiver的MetaData信息
 */
inline fun Context.getReceiverMetaData(receiver:BroadcastReceiver, key:String):String? {
    return getReceiverMetaData(receiver.javaClass, key)
}

/**
 * 获取Receiver的MetaData信息
 */
inline fun Context.getReceiverMetaData(clz:Class<out BroadcastReceiver?>, key:String):String? {
    return try {
        val ai = packageManager.getReceiverInfo(ComponentName(this, clz), PackageManager.GET_META_DATA)
        ai.metaData[key].toString()
    } catch(e:PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

/**
 * 判断App是否是Debug版本
 *
 * @return `true`: 是<br></br>`false`: 否
 */
inline fun Context.isAppDebug():Boolean {
    return applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}

/**
 * 判断App是否是Debug版本
 *
 * @param packageName 包名
 * @return `true`: 是<br></br>`false`: 否
 */
inline fun Context.isAppDebug(packageName:String?):Boolean {
    val ai:ApplicationInfo = getApplicationInfo(packageName, 0) ?: return false
    return ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}

/**
 * 删除缓存文件夹
 */
inline fun Context.deleteCache() {
    cacheDir.deleteDir()
    externalCacheDir.deleteDir()
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        codeCacheDir.deleteDir()
    }
}

/**
 * 获取状态栏高度
 */
inline fun Context.getStatusBarHeight():Int {
    return try {
        val resourceId:Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        resources.getDimensionPixelSize(resourceId)
    } catch(e:Exception) {
        e.printStackTrace()
        0
    }
}

/**
 * 弹个Toast出来
 */
inline fun Context.toast(content:CharSequence?) {
    content?.let {Toast.makeText(this, content, Toast.LENGTH_SHORT).show()}
}

/**
 * 复制文本到粘贴板
 */
inline fun Context.copy(content:CharSequence?) {
    content?.let {
        val manager:ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.primaryClip = ClipData.newPlainText("text", it)
    }
}
