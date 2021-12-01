package com.app.kotlin

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