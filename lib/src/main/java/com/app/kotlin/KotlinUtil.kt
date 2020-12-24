package com.app.kotlin

import android.text.TextUtils
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.collection.SimpleArrayMap

/**
 * 判断是否为null,不为null则返回自身,为null则调用block返回新的对象
 * 用于懒创建对象
 * var str:String?=null
 * str = orNull(str,{"创建对象"})
 */
public inline fun <T> orNull(t: T?, block: () -> T): T {
    return t ?: block()
}

/**
 * 判断是否为null,不为null则调用block方法返回另一个对象,为null则调用default返回另一个对象
 * 用于获取对象的值等
 * val size = orNull(array,{array.size},0)
 */
public inline fun <T, R> nonNull(t: R?, block: R.(R) -> T, default: T): T {
    return t?.let { it.block(t) } ?: default
}

/**
 * 判断是否为null,不为null则调用block方法返回另一个对象,为null则调用default返回另一个对象
 * 用于获取对象的值等
 * val size = orNull(array,{array.size},{19})
 */
public inline fun <T, R> nonNull(t: R?, block: R.(R) -> T, default: () -> T): T {
    return t?.let { it.block(t) } ?: default()
}

/**
 * 三元表达式
 * val size = condition(array?.isEmpty,0,0)
 */
public inline fun <T> condition(b: Boolean?, truePart: T, falsePart: T): T {
    return if (b == true) truePart else falsePart
}

/**
 * 三元表达式
 * val size = condition(array?.isEmpty,{0},{0})
 */
public inline fun <T> condition(b: Boolean?, truePart: () -> T, falsePart: () -> T): T {
    return if (b == true) truePart() else falsePart()
}


/**
 * 拼接字符串
 */
public inline fun join(vararg array: Any?): String {
    val builder = StringBuilder()
    for (str in array) {
        str?.let { builder.append(str) }
    }
    return builder.toString()
}


/**
 * 判断对象是否为空
 */
public inline fun <T> isEmpty(t: T?): Boolean {
    if (t == null) {
        return true
    } else {
        if (t is Array<*>) {
            return t.size == 0
        }
        if (t is Collection<*>) {
            return t.isEmpty()
        }
        if (t is Map<*, *>) {
            return t.isEmpty()
        }
        if (t is CharSequence) {
            return t.isEmpty()
        }
        if (t is SimpleArrayMap<*, *>) {
            return t.isEmpty
        }
        if (t is SparseArray<*>) {
            return t.size() == 0
        }
        if (t is SparseBooleanArray) {
            return t.size() == 0
        }
        if (t is SparseIntArray) {
            return t.size() == 0
        }
        return false
    }
}

/**
 * 判断不为空
 */
public inline fun <T> isNotEmpty(t: T?): Boolean {
    return !isEmpty(t)
}

/**
 * a != b && a!= c && a!= d
 * @return
 */
public inline fun <T> T.unequals(vararg values: T): Boolean {
    for (value in values) {
        if (value?.equals(this) == true) {
            return false
        }
    }
    return true
}


/**
 * a != b || a != c || a != d
 * @return
 */
public inline fun <T> T.orUnequals(vararg values: T): Boolean {
    for (value in values) {
        if (value?.equals(this) != true) {
            return true
        }
    }
    return false
}


/**
 * a == b || a == c || a == d
 *
 * @param compare
 * @param values
 * @return
 */
public inline fun <T> T.orEquals(vararg values: T): Boolean {
    for (value in values) {
        if (value?.equals(this) == true) {
            return true
        }
    }
    return false
}

/**
 * a == b && a == c && a == d
 * @return
 */
public inline fun <T> T.equals(vararg values: T): Boolean {
    for (value in values) {
        if (value?.equals(this) != true) {
            return false
        }
    }
    return true
}

/**
 * 判断是否为true
 */
public inline fun judge(boolean: Boolean?): Boolean = boolean == true
/**
 * 判断是否为空
 */
public inline fun judge(content: CharSequence?): Boolean = !TextUtils.isEmpty(content)
/**
 * 判断是否为空
 */
public inline fun <T> judge(collection: Collection<T>?): Boolean = collection?.size ?: 0 > 0
/**
 * 判断是否为空
 */
public inline fun <T, K> judge(map: Map<T, K>?): Boolean = map?.size ?: 0 > 0