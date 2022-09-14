package com.app.kotlin

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.core.net.toFile
import java.io.File
import java.io.InputStream
import java.io.OutputStream

const val ASSET_BASE = "file:///android_asset/"
const val RESOURCE_BASE = "file:///android_res/"
const val FILE_BASE = "file:"
const val FILE_BASE_URL = "file://"
const val CONTENT_BASE = "content:"

inline fun Uri.inputStream(context:Context):InputStream? {
    return context.contentResolver.openInputStream(this)
}

inline fun Uri.inputStream():InputStream? {
    return App.context?.let {inputStream(it)}
}

inline fun Uri.outputStream(context:Context):OutputStream? {
    return context.contentResolver.openOutputStream(this)
}

inline fun Uri.outputStream():OutputStream? {
    return App.context?.let {outputStream(it)}
}

inline fun Uri.fileDescriptor(context:Context, mode:String):ParcelFileDescriptor? {
    return context.contentResolver.openFileDescriptor(this, mode)
}

inline fun Uri.fileDescriptor(mode:String):ParcelFileDescriptor? {
    return App.context?.let {fileDescriptor(it, mode)}
}

inline fun Uri?.copy2File(destFile:File?):Boolean {
    return copy2File(App.context, destFile)
}

inline fun Uri?.copy2File(context:Context?, destFile:File?):Boolean {
    if(this == null || context == null || destFile == null) return false
    destFile.parentFile.mkdirs()
    return inputStream(context).useCatch {destFile.writeStream(it)}.getOrDefault(false)
}

inline fun Uri?.copy2File(destFile:String):Boolean {
    return copy2File(App.context, destFile.asFile())
}

inline fun Uri?.copy2File(context:Context?, destFile:String?):Boolean {
    return copy2File(context, destFile.asFile())
}

inline fun File?.toUri():Uri? {
    return toUri(App.context)
}

inline fun File?.toUri(context:Context?):Uri? {
    if(this == null || context == null) return null
    return if(Build.VERSION_CODES.N.compareSdkVersion()) {
        val authority = "${context.packageName}.provider"
        androidx.core.content.FileProvider.getUriForFile(context, authority, this)
    } else {
        Uri.fromFile(this)
    }
}

/**
 * 查询Uri的文件的列表类型值
 * @receiver Uri
 * @param columns String 需要查询的项
 * @return Cursor?
 */
inline fun Uri.queryColumn(context:Context?, columns:String):Cursor? {
    if(context == null) return null
    var cursor:Cursor? = null
    if("file" == scheme) {
        encodedPath?.let {
            val buff = StringBuffer().apply {
                append("(").append(MediaStore.MediaColumns.DATA).append("='${Uri.decode(it)}')")
            }
            val projection = arrayOf(MediaStore.Images.ImageColumns._ID, columns)
            cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, buff.toString(), null, null)
        }
    } else if("content" == scheme) {
        cursor = context.contentResolver.query(this, arrayOf(columns), null, null, null)
    }
    return cursor
}

/**
 * 查询Uri的文件的列表类型值
 * @receiver Uri
 * @param columns String 需要查询的项
 * @return Cursor?
 */
inline fun Uri.queryColumn(columns:String):Cursor? {
    return queryColumn(App.context, columns)
}

/**
 * 查询Uri的文件的列表类型值
 * @receiver Uri
 * @param columns String 需要查询的项
 * @return Cursor?
 */
inline fun <R> Uri.queryColumn(context:Context?, columns:String, block:(Cursor, Int) -> R):R? {
    val cursor = queryColumn(context, columns)
    if(cursor?.moveToNext().judge()) {
        val columnIndex = cursor!!.getColumnIndex(columns)
        return block(cursor!!, columnIndex)
    }
    return null
}

/**
 * 查询Uri的文件的列表类型值
 * @receiver Uri
 * @param columns String 需要查询的项
 * @return Cursor?
 */
inline fun <R> Uri.queryColumn(columns:String, block:(Cursor, Int) -> R):R? {
    return queryColumn(App.context, columns, block)
}

/**
 * Uri转文件
 * @receiver Uri
 * @return File
 */
inline fun Uri.asFile():File? {
    return try {
        toFile()
    } catch(e:Exception) {
        queryColumn(MediaStore.Images.Media.DATA) {cursor, i ->
            val data = cursor.getStringOrNull(i)
            return data?.let {File(it)}
        }
    }
}

/**
 * 获取Uri文件的长度
 * @receiver Uri
 * @return Long
 */
inline fun Uri.length(context:Context?):Long {
    if(context == null) return 0
    var column = fileDescriptor(context, "r")?.use {
        it.statSize
    } ?: -1
    return if(column <= 0) {
        queryColumn(MediaStore.Images.Media.SIZE) {cursor, i ->
            return cursor.getLongOrNull(i) ?: 0
        } ?: 0
    } else {
        column
    }
}

inline fun Uri.length():Long {
    return length(App.context)
}

/**
 * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
 */
@SuppressLint("NewApi")
inline fun Uri.getPath(context:Context):String? {
    if(Build.VERSION_CODES.KITKAT.compareSdkVersion() && DocumentsContract.isDocumentUri(context, this)) { // ExternalStorageProvider
        val documentId = DocumentsContract.getDocumentId(this)
        if(isExternalStorageDocument()) {
            val split = documentId.split(":").toTypedArray()
            val type = split[0]
            if("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if(isDownloadsDocument()) {
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), documentId.toLong())
            return contentUri.getDataColumn(context, null, null)
        } else if(isMediaDocument()) {
            val split = documentId.split(":").toTypedArray()
            var contentUri:Uri? = null
            when(split[0]) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            return contentUri?.getDataColumn(context, "_id=?", arrayOf(split[1]))
        }
    } else if("content".equals(scheme, ignoreCase = true)) {
        return getDataColumn(context, null, null)
    } else if("file".equals(scheme, ignoreCase = true)) {
        return path
    }
    return null
}

inline fun Uri.getDataColumn(context:Context, selection:String?, selectionArgs:Array<String?>?):String? {
    val query = context.contentResolver.query(this, arrayOf(MediaStore.Images.Media.DATA), selection, selectionArgs, null)
    if(query != null && query.moveToFirst()) {
        val column = query.getColumnIndex(MediaStore.Images.Media.DATA)
        return query.getString(column)
    }
    return null
}

inline fun Uri.isExternalStorageDocument():Boolean {
    return "com.android.externalstorage.documents" == authority
}

inline fun Uri.isDownloadsDocument():Boolean {
    return "com.android.providers.downloads.documents" == authority
}

inline fun Uri.isMediaDocument():Boolean {
    return "com.android.providers.media.documents" == authority
}

inline fun String?.isUriContent():Boolean {
    return this != null && (startsWith("/content:/") || startsWith("content:/"))
}

/**
 * 获取Uri的文件类型
 * @receiver Uri
 * @param context Context
 * @return String?
 */
inline fun Uri.mimeType(context:Context?):String? {
    if(context == null) return null
    val resolverType = context.contentResolver.getType(this)
    return resolverType.orNull {
        queryColumn(MediaStore.MediaColumns.MIME_TYPE) {cursor, i ->
            cursor.getString(i)
        }
    }
}

inline fun Uri.mimeType():String? {
    return mimeType(App.context)
}