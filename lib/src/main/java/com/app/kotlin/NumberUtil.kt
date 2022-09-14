package com.app.kotlin

import kotlin.math.max
import kotlin.math.min

/**
 * 使一个数在限定的两个数字之间，最小不能小于最小值，最大不能大于最大值
 */
inline fun Long?.inBetween(from:Long, to:Long):Long {
    return min(max(from, to), max(min(from, to), this ?: 0))
}

/**
 * 使一个数在限定的两个数字之间，最小不能小于最小值，最大不能大于最大值
 */
inline fun Int?.inBetween(from:Int, to:Int):Int {
    return min(max(from, to), max(min(from, to), this ?: 0))
}

/***
 * 使一个数在限定的两个数字之间，最小不能小于最小值，最大不能大于最大值
 */
inline fun Double?.inBetween(from:Double, to:Double):Double {
    return min(max(from, to), max(min(from, to), this ?: 0.0))
}

/**
 * 使一个数在限定的两个数字之间，最小不能小于最小值，最大不能大于最大值
 */
inline fun Float?.inBetween(from:Float, to:Float):Float {
    return min(max(from, to), max(min(from, to), this ?: 0f))
}

/**
 * 判断是否在两个数值之间
 */
inline fun Long?.between(num1:Long, num2:Long):Boolean {
    return this?.let {it >= num1.coerceAtMost(num2) && it <= num1.coerceAtLeast(num2)} ?: false
}

/**
 * 判断是否在两个数值之间
 */
inline fun Int?.between(num1:Int, num2:Int):Boolean {
    return this?.let {it >= num1.coerceAtMost(num2) && it <= num1.coerceAtLeast(num2)} ?: false
}

/**
 * 判断是否在两个数值之间
 */
inline fun Double?.between(num1:Double, num2:Double):Boolean {
    return this?.let {it >= num1.coerceAtMost(num2) && it <= num1.coerceAtLeast(num2)} ?: false
}

/**
 * 判断是否在两个数值之间
 */
inline fun Float?.between(num1:Float, num2:Float):Boolean {
    return this?.let {it >= num1.coerceAtMost(num2) && it <= num1.coerceAtLeast(num2)} ?: false
}

/**
 * 判断是否包含某个数值
 */
inline fun <T> T?.include(vararg args:T):Boolean {
    return this?.let {t ->
        args.forEach {a ->
            if(a == t) {
                return true
            }
        }
        return false
    } ?: false
}

/**
 * 把数字转换为16进制
 */
inline fun Long.toHex():String {
    return java.lang.Long.toHexString(this)
}

/**
 * 把数字转换为16进制
 */
inline fun Int.toHex():String {
    return Integer.toHexString(this)
}

/**
 * 把数字转换为二进制
 */
inline fun Long.toBinary():String {
    return java.lang.Long.toBinaryString(this)
}

/**
 * 把数字转换为二进制
 */
inline fun Int.toBinary():String {
    return Integer.toBinaryString(this)
}

/**
 * 把数字转换为8进制
 */
inline fun Long.toOctal():String {
    return java.lang.Long.toOctalString(this)
}

/**
 * 把数字转换为8进制
 */
inline fun Int.toOctal():String {
    return Integer.toOctalString(this)
}

/**
 * 把8进制为10进制
 */
inline fun String.octalToInt():Int {
    return Integer.valueOf(this, 8)
}

/**
 * 把2进制为10进制
 */
inline fun String.binaryToInt():Int {
    return Integer.valueOf(this, 2)
}

/**
 * 把16进制为10进制
 */
inline fun String.hexToInt():Int {
    return Integer.valueOf(this, 16)
}

/**
 * 把8进制为10进制
 */
inline fun String.octalToLong():Long {
    return java.lang.Long.valueOf(this, 8)
}

/**
 * 把2进制为10进制
 */
inline fun String.binaryToLong():Long {
    return java.lang.Long.valueOf(this, 2)
}

/**
 * 把16进制为10进制
 */
inline fun String.hexToLong():Long {
    return java.lang.Long.valueOf(this, 16)
}
