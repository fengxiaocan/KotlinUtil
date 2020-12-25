package com.app.kotlin

import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import kotlin.experimental.and

/**
 * 拼接字符串
 */
inline fun String?.append(vararg array: Any?): String {
    return StringBuilder(this.orEmpty()).run {
        array.forEach {
            it?.let { append(it) }
        }
        toString()
    }
}


/**
 * 限制字符串的长度，超过长度的会截取一部分，末尾显示为自定义 end 字符串
 *
 * @param end     待替换字符串b
 * @param length  限制长度
 */
inline fun String?.limit(end: String?, length: Int): String {
    if (this == null) return ""
    return (this.length > length).condition({ "${this.substring(0, length - 1)}${end.orEmpty()}" }, { this })

}

/**
 * 限制字符串的长度，超过长度的会截取一部分，末尾显示为...
 * @param length 限制长度
 */
inline fun String?.limit(length: Int): String {
    return limit("", length)
}

/**
 * 判断字符串中是否包含多个字符串
 * @return
 */
inline fun String?.contains(vararg agr: String): Boolean {
    if (this == null) return false
    agr.forEach {
        if (!this.contains(it, false)) {
            return@contains false
        }
    }
    return true
}

/**
 * 拼接字符串
 */
inline fun join(vararg array: Any?): String {
    return StringBuilder().apply {
        array.forEach { any ->
            append(any)
        }
    }.toString()
}

/**
 * 拼接字符串
 */
inline fun builder(vararg array: Any?): StringBuilder {
    return StringBuilder().apply {
        array.forEach { any ->
            append(any)
        }
    }
}


/**
 * 计算中英文字符串的字节长度
 * @return int 中文占两个字节,英文1个字节
 */
inline fun String?.charLength(): Int {
    if (this == null) {
        return 0
    }
    var len = 0
    val chars = this.toCharArray()
    chars.forEach {
        len += if (it.toInt() > 255) 2 else 1
    }
    return len
}

/**
 * 字符串转整数
 * @return
 */
inline fun String?.toInt(): Int {
    this?.let {
        try {
            return@toInt Integer.parseInt(this)
        } catch (e: Exception) {
            return@toInt 0
        }
    } ?: let { return@toInt 0 }
}

/**
 * 字符串转整数
 * @return
 */
inline fun String?.toDouble(): Double {
    this?.let {
        try {
            return@toDouble java.lang.Double.parseDouble(this)
        } catch (e: Exception) {
            return@toDouble 0.toDouble()
        }
    } ?: let { return@toDouble 0.toDouble() }
}

/**
 * 字符串转整数
 * @return
 */
inline fun String?.toLong(): Long {
    this?.let {
        try {
            return@toLong java.lang.Long.parseLong(this)
        } catch (e: Exception) {
            return@toLong 0.toLong()
        }
    } ?: let { return@toLong 0.toLong() }
}

/**
 * 获取字符串的MD5
 */
inline fun String?.md5Hex(): String {
    return this.md5Hex(Charsets.UTF_8)
}


/**
 * 获取字符串的MD5
 */
inline fun String?.md5Hex(charset: Charset): String {
    return codeHex(charset, "MD5")
}

/**
 * 获取字符串的SHA-1
 */
inline fun String?.sha1Hex(charset: Charset): String {
    return codeHex(charset, "SHA-1")
}

/**
 * 获取字符串的SHA-1
 */
inline fun String?.sha1Hex(): String {
    return sha1Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的SHA-256
 */
inline fun String?.sha256Hex(charset: Charset): String {
    return codeHex(charset, "SHA-256")
}


/**
 * 获取字符串的SHA-256
 */
inline fun String?.sha256Hex(): String {
    return sha256Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的SHA-512
 */
inline fun String?.sha512Hex(charset: Charset): String {
    return codeHex(charset, "SHA-512")
}

/**
 * 获取字符串的SHA-512
 */
inline fun String?.sha512Hex(): String {
    return sha512Hex(Charsets.UTF_8)
}

/**
 * 获取字符串的Base64
 */
inline fun String?.encodeBase64(): String {
    if (this == null) return ""
    return Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)
}

/**
 * 获取字符串的Base64
 */
inline fun String?.decodeBase64(): String {
    if (this == null) return ""
    return toByteArray().decodeBase64()
}

/**
 * 获取字符串的Base64
 */
inline fun ByteArray?.decodeBase64(): String {
    if (this == null) return ""
    return Base64.decode(this, Base64.DEFAULT).toString(Charsets.UTF_8)
}

/**
 * 把字符串加密
 */
inline fun String?.codeHex(charset: Charset, algorithm: String): String {
    if (this == null) {
        return ""
    }
    val btInput = this.toByteArray(charset)
    return try {
        //获得MD5摘要算法的 MessageDigest 对象
        val mdInst = MessageDigest.getInstance(algorithm)
        //使用指定的字节更新摘要
        mdInst.update(btInput)
        //获得密文
        val md = mdInst.digest()
        //把密文转换成十六进制的字符串形式
        md.toHex()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

/**
 * 把Byte数组转换成16进制字符串
 */
inline fun ByteArray?.toHex(): String {
    if (this == null) {
        return ""
    }
    val bytes = this
    val len = bytes.size
    if (len <= 0) {
        return ""
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
inline fun String?.toUnicode(): String {
    if (this == null) {
        return ""
    }
    try {
        val unicode = StringBuffer()
        forEach {
            // 取出每一个字符转换为unicode
            val hexString = Integer.toHexString(it.toInt())
            when (hexString.length) {
                1 -> unicode.append("\\u000$hexString")
                2 -> unicode.append("\\u00$hexString")
                3 -> unicode.append("\\u0$hexString")
                else -> unicode.append("\\u$hexString")
            }
        }
        return unicode.toString()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        return ""
    }
}

/**
 * unicode 转字符串
 */
inline fun String?.fromUnicode(): String {
    if (this == null) {
        return ""
    }
    return let {
        val hex = split("\\\\u".toRegex()).toTypedArray()
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

/**
 * URL解码
 */
inline fun String?.decodeURL(charset: String?): String {
    if (this == null) {
        return ""
    }
    return try {
        URLDecoder.decode(this, charset)
    } catch (e: Exception) {
        this
    }
}

/**
 * URL解码
 */
inline fun String?.decodeURL(): String {
    return this.decodeURL("UTF-8")
}

/**
 * URL加密
 */
inline fun String?.encodeURL(charset: String?): String {
    if (this == null) {
        return ""
    }
    return try {
        URLEncoder.encode(this, charset)
    } catch (e: Exception) {
        this
    }
}

/**
 * URL加密
 */
inline fun String?.encodeURL(): String {
    return this.encodeURL("UTF-8")
}
