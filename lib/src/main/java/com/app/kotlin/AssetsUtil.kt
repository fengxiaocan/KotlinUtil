package com.app.kotlin

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * 从assets获取文件
 *
 * @param fileName
 * @return
 * @throws IOException
 */
@Throws(IOException::class)
inline fun Context.streamFromAssets(fileName:String):InputStream? {
    return assets.open(fileName)
}

/**
 * 从assets获取文本文件
 *
 * @param fileName
 * @return
 */
@Throws(IOException::class)
inline fun Context.getTextFromAssets(fileName:String, charset:Charset?):String {
    val inputReader = InputStreamReader(streamFromAssets(fileName), charset ?: Charsets.UTF_8)
    val bufReader = BufferedReader(inputReader)

    var line:String
    val result = StringBuilder()
    while(bufReader.readLine().also {line = it} != null) {
        result.append(line)
    }
    return result.toString()
}

/**
 * 从assets获取文本文件
 *
 * @param fileName
 * @return
 */
inline fun Context.getTextFromAssets(fileName:String):String {
    return this.getTextFromAssets(fileName, Charsets.UTF_8)
}