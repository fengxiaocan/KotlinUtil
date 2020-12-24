package com.app.kotlin

import java.nio.charset.Charset
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * 拼接字符串
 */
public inline fun String.append(vararg array: Any?): String {
    val builder = StringBuilder(this)
    for (str in array) {
        str?.let { builder.append(str) }
    }
    return builder.toString()
}

/**
 * 拼接字符串
 */
public inline fun String.md5Hex(): String? {
    return this.md5Hex(Charsets.UTF_8)
}

/**
 * 拼接字符串
 */
public inline fun String.md5Hex(charset: Charset): String? {
    val btInput = this.toByteArray(charset)
    return try {
        //获得MD5摘要算法的 MessageDigest 对象
        val mdInst = MessageDigest.getInstance("MD5")
        //使用指定的字节更新摘要
        mdInst.update(btInput)
        //获得密文
        val md = mdInst.digest()
        //把密文转换成十六进制的字符串形式
        md.toHex()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

public inline fun ByteArray.toHex(): String? {
    val bytes = this
    val len = bytes.size
    if (len <= 0) {
        return null
    }
    val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    val ret = CharArray(len shl 1)
    var i = 0
    var j = 0
    while (i < len) {
        ret[j++] = hexDigits[(bytes[i].toInt().ushr(4)) and 0xF]
        ret[j++] = hexDigits[(bytes[i] and 0xF).toInt()]
        i++
    }
    return String(ret)
}

/**
 * 字符串转换unicode
 */
public inline fun String.toUnicode(): String? {
    return try {
        val unicode = StringBuffer()
        for (element in this) {
            // 取出每一个字符转换为unicode
            val hexString = Integer.toHexString(element.toInt())
            unicode.append("\\u$hexString")
        }
        return unicode.toString()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * unicode 转字符串
 */
public inline fun String.fromUnicode(): String {
    return run {
        val hex = this.split("\\\\u".toRegex()).toTypedArray()
        val string = StringBuffer()
        for (i in hex.indices) {
            try {
                // 转换出每一个代码点
                val data = hex[i].toInt(16)
                // 追加成string
                string.append(data.toChar())
            } catch (e: java.lang.Exception) {
                string.append(hex[i])
            }
        }
        string.toString()
    }
}
