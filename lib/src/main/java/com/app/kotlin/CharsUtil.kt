package com.app.kotlin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Base64
import android.widget.Toast
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.regex.Pattern
import kotlin.experimental.and

/**
 * 如果字符串为空，则返回默认的不为空的字符串
 * @defaultChar 默认字符串
 */
inline fun CharSequence?.noEmpty(defaultChar:CharSequence):CharSequence {
    return (this.isNotEmpty()).condition(this!!, defaultChar)
}

/**
 * 如果字符串为空，则返回默认的不为空的字符串
 * @defaultChar 默认字符串
 */
inline fun String?.noEmpty(defaultChar:String):String {
    return (this.isNotEmpty()).condition(this!!, defaultChar)
}

/**
 * 拼接字符串
 */
inline fun String?.append(vararg array:Any?):String {
    return StringBuilder(this.orEmpty()).apply {
        array.forEach {it?.let {append(it)}}
    }.toString()
}

/**
 * 限制字符串的长度，超过长度的会截取一部分，末尾显示为自定义 end 字符串
 *
 * @param end     待替换字符串b
 * @param length  限制长度
 */
inline fun String?.limit(end:String?, length:Int):String {
    if(this == null) return ""
    return (this.length > length).condition({"${this.substring(0, length - 1)}${end.orEmpty()}"}, {this})

}

/**
 * 限制字符串的长度，超过长度的会截取一部分，末尾显示为...
 * @param length 限制长度
 */
inline fun String?.limit(length:Int):String {
    return limit("", length)
}

/**
 * 判断字符串是否为null或全为空格
 * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
 */
inline fun String?.isTrimEmpty():Boolean {
    return this == null || this.trim {it <= ' '}.isEmpty()
}

/**
 * 判断字符串中是否包含多个字符串
 * @return
 */
inline fun String?.contains(vararg agr:String):Boolean {
    if(this == null) return false
    agr.forEach {
        if(!this.contains(it, false)) {
            return@contains false
        }
    }
    return true
}

/**
 * 获取url的后缀
 */
inline fun String?.urlSuffix():String {
    val of = this?.lastIndexOf('.') ?: -1
    return (of > 0).condition(this!!.substring(of), "")
}

/**
 * 获取url的中的文件名
 */
inline fun String?.urlFileName():String? {
    val of = this?.lastIndexOf('/') ?: -1
    return (of > 0).condition(this!!.substring(of + 1), "")
}

/**
 * 拼接字符串
 */
inline fun join(vararg array:Any?):String {
    return StringBuilder().apply {
        array.forEach {it?.let {append(it)}}
    }.toString()
}

/**
 * 拼接字符串
 */
inline fun stringBuilder(vararg array:Any?):StringBuilder {
    return StringBuilder().apply {
        array.forEach {it?.let {append(it)}}
    }
}

/**
 * 计算中英文字符串的字节长度
 * @return int 中文占两个字节,英文1个字节
 */
inline fun String?.charLength():Int {
    var len = 0
    this?.toCharArray()?.forEach {
        len += if(it.code > 255) 2 else 1
    }
    return len
}

/**
 * 字符串转整数
 * @return
 */
inline fun String?.toInt():Int {
    return kotlin.runCatching {
        this?.let {Integer.parseInt(it)} ?: 0
    }.getResult(0)
}

/**
 * 字符串转整数
 * @return
 */
inline fun String?.toDouble():Double {
    return kotlin.runCatching {
        this?.let {java.lang.Double.parseDouble(it)} ?: 0.0
    }.getResult(0.0)
}

/**
 * 字符串转整数
 * @return
 */
inline fun String?.toLong():Long {
    return kotlin.runCatching {
        this?.let {java.lang.Long.parseLong(it)} ?: 0L
    }.getResult(0L)
}

/**
 * 字符串转布尔
 * @return 转换异常返回 false
 */
inline fun String?.toBoolean():Boolean {
    return kotlin.runCatching {
        this?.let {java.lang.Boolean.parseBoolean(it)} ?: false
    }.getResult(false)
}

/**
 * 返回字符串长度
 * @return null返回0，其他返回自身长度
 */
inline fun CharSequence?.length():Int {
    return this?.length ?: 0
}

/**
 * 判断一个字符串是不是数字
 */
inline fun CharSequence?.isNumber():Boolean {
    if(this == null) return false
    val pattern = Pattern.compile("-?[0-9]+.?[0-9]+")
    val isNum = pattern.matcher(this)
    return isNum.matches()
}

/**
 * 获取字符串的MD5
 */
inline fun String?.md5Hex():String {
    return this.md5Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的MD5
 */
inline fun String?.md5Hex(charset:Charset):String {
    return codeHex(charset, "MD5")
}

/**
 * 获取字符串的SHA-1
 */
inline fun String?.sha1Hex(charset:Charset):String {
    return codeHex(charset, "SHA-1")
}

/**
 * 获取字符串的SHA-1
 */
inline fun String?.sha1Hex():String {
    return sha1Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的SHA-256
 */
inline fun String?.sha256Hex(charset:Charset):String {
    return codeHex(charset, "SHA-256")
}

/**
 * 获取字符串的SHA-256
 */
inline fun String?.sha256Hex():String {
    return sha256Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的SHA-512
 */
inline fun String?.sha512Hex(charset:Charset):String {
    return codeHex(charset, "SHA-512")
}

/**
 * 获取字符串的SHA-512
 */
inline fun String?.sha512Hex():String {
    return sha512Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的Base64
 */
inline fun String?.encodeBase64():String {
    return this?.let {Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)} ?: ""
}

/**
 * 获取字符串的Base64
 */
inline fun String?.decodeBase64():String {
    return this?.let {toByteArray().decodeBase64()} ?: ""
}

/**
 * 获取字符串的Base64
 */
inline fun ByteArray?.decodeBase64():String {
    return this?.let {Base64.decode(this, Base64.DEFAULT).toString(Charsets.UTF_8)} ?: ""
}

/**
 * 把字符串加密
 */
inline fun String?.codeHex(charset:Charset, algorithm:String):String {
    if(this == null) return ""
    return kotlin.runCatching {
        val btInput = this.toByteArray(charset)
        val mdInst = MessageDigest.getInstance(algorithm) //使用指定的字节更新摘要
        mdInst.update(btInput) //获得密文
        val md = mdInst.digest() //把密文转换成十六进制的字符串形式
        md.toHex()
    }.getResult("")
}

/**
 * 把Byte数组转换成16进制字符串
 */
inline fun ByteArray?.toHex():String {
    if(this == null) return ""
    val len = this.size
    if(len <= 0) {
        return ""
    }
    val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    val ret = CharArray(len shl 1)
    var i = 0
    var j = 0
    while(i < len) {
        ret[j++] = hexDigits[(this[i].toInt().ushr(4)) and 0xF]
        ret[j++] = hexDigits[(this[i] and 0xF).toInt()]
        i++
    }
    return String(ret)
}

/**
 * 字符串转换unicode
 */
inline fun String?.toUnicode():String {
    if(this == null) return ""
    return kotlin.runCatching {
        StringBuffer().onEach { // 取出每一个字符转换为unicode
            val hexString = Integer.toHexString(it.code)
            when(hexString.length) {
                1 -> append("\\u000$hexString")
                2 -> append("\\u00$hexString")
                3 -> append("\\u0$hexString")
                else -> append("\\u$hexString")
            }
        }.toString()
    }.getResult("")
}

/**
 * unicode 转字符串
 */
inline fun String?.fromUnicode():String {
    if(this == null) return ""
    return StringBuffer().apply {
        val hex = split("\\\\u".toRegex()).toTypedArray()
        for(i in hex.indices) {
            append(kotlin.runCatching {
                val data = hex[i].toInt(16) // 追加成string
                data.toChar()
            }.getResult(hex[i]))
        }
    }.toString()
}

/**
 * URL解码
 */
inline fun String?.decodeURL(charset:String?):String {
    if(this == null) return ""
    return kotlin.runCatching {
        URLDecoder.decode(this, charset)
    }.getResult("")
}

/**
 * URL解码
 */
inline fun String?.decodeURL():String {
    return this.decodeURL("UTF-8")
}

/**
 * URL加密
 */
inline fun String?.encodeURL(charset:String?):String {
    if(this == null) return ""
    return kotlin.runCatching {
        URLEncoder.encode(this, charset)
    }.getResult("")
}

/**
 * URL加密
 */
inline fun String?.encodeURL():String {
    return this.encodeURL("UTF-8")
}
