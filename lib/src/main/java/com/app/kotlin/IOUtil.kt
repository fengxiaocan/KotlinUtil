package com.app.kotlin

import java.io.*
import java.lang.StringBuilder

/**
 * Result<R> 获取结果,如果为空,则返回默认值
 * @return R 默认值
 */
inline fun <R> Result<R>?.getResult(defaultValue:R):R {
    return this?.getOrDefault(defaultValue) ?: defaultValue
}

/**
 * 关闭流而不抛出异常
 * @receiver T?
 */
inline fun <T:Closeable?> T?.closeQuiet() {
    try {
        this?.close()
    } catch(e:Throwable) {

    }
}

/**
 * 关闭流而不抛出异常
 * @param t Array<out T>
 */
inline fun <T:Closeable?> closeQuiet(vararg t:T) {
    t.forEach {
        it.closeQuiet()
    }
}

/**
 * 使用流的时候最终会自动关闭流
 * @receiver T 流
 * @param block Function1<T, R> 传入的参数为流，返回自定义参数
 * @return Result<R> 结果封包
 */
inline fun <T:Closeable?, R> T.useCatch(block:(T) -> R):Result<R> {
    return try {
        Result.success(block(this))
    } catch(e:Throwable) {
        Result.failure(e)
    } finally {
        closeQuiet()
    }
}

/**
 * 使用InputStream来读取byte数组,注意最终会关闭掉输入流
 * @receiver T 输入流
 * @param block Function2<ByteArray, Int, Unit>
 */
inline fun <T:InputStream?> T?.readBytes(block:(ByteArray, Int) -> Unit) {
    this?.let {
        val data = ByteArray(DEFAULT_BUFFER_SIZE)
        var len:Int
        while(read(data, 0, DEFAULT_BUFFER_SIZE).also {len = it} != -1) {
            block(data, len)
        }
        closeQuiet()
    }
}

/**
 * 使用InputStream来读取byte数组,注意最终会关闭掉输入流
 * @receiver T 输入流
 * @param bytes
 */
inline fun <T:OutputStream?> T?.writeBytes(bytes:ByteArray):Boolean {
    return this?.let {
        write(bytes)
        flush()
        closeQuiet()
        true
    } ?: false
}

/**
 * 把InputStream的数据读写到OutputStream中,注意 InputStream 和 OutputStream 都不会关闭
 * @receiver T 输入流
 * @param output R 输出流
 */
inline fun <T:InputStream?> T?.copyTo(output:OutputStream?):Boolean {
    return if(this != null && output != null) {
        val data = ByteArray(DEFAULT_BUFFER_SIZE)
        var len:Int
        while(read(data, 0, DEFAULT_BUFFER_SIZE).also {len = it} != -1) {
            output.write(data, 0, len)
            output.flush()
        }
        true
    } else {
        false
    }
}

/**
 * 把InputStream的数据读写到OutputStream中,同时捕捉异常,InputStream 和 OutputStream 最终会自动关闭
 * @receiver T 输入流
 * @param output R 输出流
 * @return Result<Boolean> 是否读写成功
 */
inline fun <T:InputStream?, R:OutputStream?> T?.readToWrite(output:R?):Result<Boolean> {
    return try {
        Result.success(copyTo(output))
    } catch(e:Exception) {
        Result.failure(e)
    } finally {
        output.closeQuiet()
        closeQuiet()
    }
}


inline fun <T:InputStream?, R:Reader?> T?.readToReader(output:R?):Result<StringBuilder> {
    return try {
        Result.success(copyTo(output))
    } catch(e:Exception) {
        Result.failure(e)
    } finally {
        output.closeQuiet()
        closeQuiet()
    }
}

