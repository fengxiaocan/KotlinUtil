package com.app.kotlin

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.collection.SimpleArrayMap

inline fun <T> T?.notNull(): Boolean {
    return this != null
}

/**
 * 判断对象是否为空
 */
inline fun <T> T?.isEmpty(): Boolean {
    if (this == null) {
        return true
    } else {
        if (this is Array<*>) {
            return this.size == 0
        }
        if (this is Collection<*>) {
            return this.isEmpty()
        }
        if (this is Map<*, *>) {
            return this.isEmpty()
        }
        if (this is SimpleArrayMap<*, *>) {
            return this.isEmpty
        }
        if (this is SparseArray<*>) {
            return this.size() == 0
        }
        if (this is SparseBooleanArray) {
            return this.size() == 0
        }
        if (this is SparseIntArray) {
            return this.size() == 0
        }
        return false
    }
}

/**
 * 判断不为空
 */
inline fun <T> T?.isNotEmpty(): Boolean {
    return this?.let { !isEmpty() } ?: false
}

/**
 * a != b && a!= c && a!= d
 * 如果所有的都不相等,返回true;有一个相等则为false
 * @return
 */
inline fun <T> T?.unequals(vararg values: T?): Boolean {
    if (this == null) {
        values.forEach {
            if (it == null) return@unequals false
        }
        return true
    }
    values.forEach {
        if (it?.equals(this) == true) return@unequals false
    }
    return true
}


/**
 * a != b || a != c || a != d
 * 如果有一个不相等,返回true
 * @return
 */
inline fun <T> T?.orUnequals(vararg values: T?): Boolean {
    if (this == null) {
        values.forEach {
            if (it != null) return@orUnequals true
        }
        return false
    }
    values.forEach {
        if (it?.equals(this) != true) return@orUnequals true
    }
    return false
}


/**
 * a == b || a == c || a == d
 * 如果有一个相等,则返回true;否则false
 * @return
 */
inline fun <T> T?.orEquals(vararg values: T?): Boolean {
    if (this == null) {
        values.forEach {
            if (it == null) return@orEquals true
        }
        return false
    }
    values.forEach {
        if (it?.equals(this) == true) return@orEquals true
    }
    return false
}

/**
 * a == b && a == c && a == d
 * 只有所有的都相等,才会返回true;有一个不相等,则为false
 * @return
 */
inline fun <T> T?.equals(vararg values: T?): Boolean {
    if (this == null) {
        values.forEach {
            if (it != null) return@equals false
        }
        return true
    }
    values.forEach {
        if (it?.equals(this) != true) return@equals false
    }
    return true
}

/**
 * 判断是否为true
 */
inline fun Boolean?.judge(): Boolean = this == true