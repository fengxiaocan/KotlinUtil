package com.app.kotlin

/**
 * 判断是否为null,不为null则返回自身,为null则调用block返回新的对象
 * 用于懒创建对象
 * var str:String?=null
 * str = orNull(str,{"创建对象"})
 */
inline fun <T> T?.orNull(block: () -> T): T {
    return this ?: block()
}

/**
 * 判断是否为null,不为null则调用block方法返回另一个对象,为null则调用default返回另一个对象
 * 用于获取对象的值等
 * val size = orNull(array,{array.size},0)
 */
inline fun <T, R> R?.nonNull(block: R.(R) -> T, default: T): T {
    return this?.let { it.block(this) } ?: default
}

/**
 * 判断是否为null,不为null则调用block方法返回另一个对象,为null则调用default返回另一个对象
 * 用于获取对象的值等
 * val size = orNull(array,{array.size},{19})
 */
inline fun <T, R> R?.nonNull(block: R.(R) -> T, default: () -> T): T {
    return this?.let { it.block(this) } ?: default()
}

/**
 * 三元表达式
 * val size = condition(array?.isEmpty,0,0)
 */
inline fun <T> Boolean?.condition(truePart: T, falsePart: T): T {
    return if (this == true) truePart else falsePart
}

/**
 * 三元表达式
 * val size = condition(array?.isEmpty,{0},{0})
 */
inline fun <T> Boolean?.condition(truePart: () -> T, falsePart: () -> T): T {
    return if (this == true) truePart() else falsePart()
}

