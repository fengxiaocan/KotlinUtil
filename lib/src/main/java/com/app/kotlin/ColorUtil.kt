package com.app.kotlin

import androidx.annotation.FloatRange
import androidx.annotation.IntRange

/**
 * 给颜色设置透明度
 * @param alpha 颜色的透明度
 * @return
 */
inline fun Int.setColorAlpha(@IntRange(from = 0, to = 255) alpha:Int):Int {
    return this and 0xFFFFFF or (alpha shl 24)
}

/**
 * 给颜色设置red 色值
 *
 * @param red 红色值
 * @return
 */
inline fun Int.setColorRed(@IntRange(from = 0, to = 255) red:Int):Int {
    return this and -0xff0001 or (red shl 16)
}

/**
 * 给颜色设置green 色值
 *
 * @param green
 * @return
 */
inline fun Int.setColorGreen(@IntRange(from = 0, to = 255) green:Int):Int {
    return this and -0xff01 or (green shl 8)
}

/**
 * 给颜色设置blue 色值
 * @param blue
 * @return
 */
inline fun Int.setColorBlue(@IntRange(from = 0, to = 255) blue:Int):Int {
    return this and -0x100 or blue
}

/**
 * 给颜色设置Alpha
 * @param alpha
 * @return
 */
inline fun Int.setColorAlpha(@FloatRange(from = 0.0, to = 1.0) alpha:Float):Int {
    return this and 0xFFFFFF or ((alpha * 255.0f + 0.5f).toInt() shl 24)
}

/**
 * 给颜色设置red 色值
 *
 * @param red
 * @return
 */
inline fun Int.setColorRed(@FloatRange(from = 0.0, to = 1.0) red:Float):Int {
    return this and -0xff0001 or ((red * 255.0f + 0.5f).toInt() shl 16)
}

/**
 * 给颜色设置green 色值
 *
 * @param green
 * @return
 */
inline fun Int.setColorGreen(@FloatRange(from = 0.0, to = 1.0) green:Float):Int {
    return this and -0xff01 or ((green * 255.0f + 0.5f).toInt() shl 8)
}

/**
 * 给颜色设置blue 色值
 *
 * @param blue
 * @return
 */
inline fun Int.setColorBlue(@FloatRange(from = 0.0, to = 1.0) blue:Float):Int {
    return this and -0x100 or (blue * 255.0f + 0.5f).toInt()
}

/**
 * 颜色值的红色
 */
inline fun Int.colorRed():Float {
    return (this shr 16 and 0xff) / 255.0f
}

/**
 * 颜色值的绿色
 */
inline fun Int.colorGreen():Float {
    return (this shr 8 and 0xff) / 255.0f
}

/**
 * 颜色值的蓝色
 */
inline fun Int.colorBlue():Float {
    return (this and 0xff) / 255.0f
}

/**
 * 颜色值的透明度
 */
inline fun Int.colorAlpha():Float {
    return (this shr 24 and 0xff) / 255.0f
}

/**
 * 颜色的红色值
 */
inline fun Int.red():Int {
    return this shr 16 and 0xFF
}

/**
 * 颜色的绿色值
 */
inline fun Int.green():Int {
    return this shr 8 and 0xFF
}

/**
 * 颜色的蓝色值
 */
inline fun Int.blue():Int {
    return this and 0xFF
}

/**
 * 颜色的透明值
 */
inline fun Int.alpha():Int {
    return this ushr 24
}

/**
 * 颜色的透明值
 */
inline fun Int.toColorHex():String {
    return "0x${Integer.toHexString(this).uppercase()}"
}

/**
 * 给颜色设置透明度
 * @param alpha 颜色的透明度
 * @return
 */
inline fun Long.setColorAlpha(@IntRange(from = 0, to = 255) alpha:Int):Long {
    return this and 0xFFFFFF or (alpha.toLong() shl 24)
}

/**
 * 给颜色设置red 色值
 *
 * @param red 红色值
 * @return
 */
inline fun Long.setColorRed(@IntRange(from = 0, to = 255) red:Int):Long {
    return this and -0xff0001 or (red shl 16).toLong()
}

/**
 * 给颜色设置green 色值
 *
 * @param green
 * @return
 */
inline fun Long.setColorGreen(@IntRange(from = 0, to = 255) green:Int):Long {
    return this and -0xff01 or (green shl 8).toLong()
}

/**
 * 给颜色设置blue 色值
 * @param blue
 * @return
 */
inline fun Long.setColorBlue(@IntRange(from = 0, to = 255) blue:Int):Long {
    return this and -0x100 or blue.toLong()
}

/**
 * 给颜色设置red 色值
 *
 * @param red
 * @return
 */
inline fun Long.setColorRed(@FloatRange(from = 0.0, to = 1.0) red:Float):Long {
    return this and -0xff0001 or ((red * 255.0f + 0.5f).toLong() shl 16)
}

/**
 * 给颜色设置green 色值
 *
 * @param green
 * @return
 */
inline fun Long.setColorGreen(@FloatRange(from = 0.0, to = 1.0) green:Float):Long {
    return this and -0xff01 or ((green * 255.0f + 0.5f).toLong() shl 8)
}

/**
 * 给颜色设置blue 色值
 *
 * @param blue
 * @return
 */
inline fun Long.setColorBlue(@FloatRange(from = 0.0, to = 1.0) blue:Float):Long {
    return this and -0x100 or (blue * 255.0f + 0.5f).toLong()
}

/**
 * 给颜色设置Alpha
 * @param alpha
 * @return
 */
inline fun Long.setColorAlpha(@FloatRange(from = 0.0, to = 1.0) alpha:Float):Long {
    return this and 0xFFFFFF or ((alpha * 255.0f + 0.5f).toLong() shl 24)
}

/**
 * 颜色值的红色
 */
inline fun Long.colorRed():Float {
    return (this shr 16 and 0xff) / 255.0f
}

/**
 * 颜色值的绿色
 */
inline fun Long.colorGreen():Float {
    return (this shr 8 and 0xff) / 255.0f
}

/**
 * 颜色值的蓝色
 */
inline fun Long.colorBlue():Float {
    return (this and 0xff) / 255.0f
}

/**
 * 颜色值的透明度
 */
inline fun Long.colorAlpha():Float {
    return (this shr 24 and 0xff) / 255.0f
}

/**
 * 颜色的红色值
 */
inline fun Long.red():Long {
    return this shr 16 and 0xFF
}

/**
 * 颜色的绿色值
 */
inline fun Long.green():Long {
    return this shr 8 and 0xFF
}

/**
 * 颜色的蓝色值
 */
inline fun Long.blue():Long {
    return this and 0xFF
}

/**
 * 颜色的透明值
 */
inline fun Long.alpha():Long {
    return this ushr 24
}

/**
 * 颜色的透明值
 */
inline fun Long.toColorHex():String {
    return "0x${java.lang.Long.toHexString(this).uppercase()}"
}