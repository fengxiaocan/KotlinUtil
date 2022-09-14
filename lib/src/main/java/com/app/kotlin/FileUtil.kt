package com.app.kotlin

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

object FileUtil {
    fun deleteDir(dir:File, deleteOriginal:Boolean) {
        if(dir.isDirectory) {
            val listFiles = dir.listFiles()
            listFiles?.forEach {it ->
                deleteDir(it, true)
            }
            if(deleteOriginal) {
                dir.delete()
            }
        } else {
            dir.delete()
        }
    }

    /**
     * 仅仅删除文件夹下的文件
     * @param dir File
     */
    fun deleteFiles(dir:File) {
        if(dir.isDirectory) {
            val listFiles = dir.listFiles()
            listFiles?.forEach {it ->
                deleteFiles(it)
            }
        } else {
            dir.delete()
        }
    }

    /**
     * 复制文件
     * @param srcDir 目标文件
     * @param destDir 目标文件
     * @return true: 复制成功 false:复制失败
     */
    fun copyDir(srcDir:File, destDir:File):Boolean {
        val listFiles = srcDir.listFiles()
        listFiles.forEach {
            if(it.isFile) {
                if(!it.copyFile(destDir.newFile(it.name))) return false
            } else {
                if(!copyDir(it, destDir.newFile(it.name, false))) return false
            }
        }
        return true
    }
}

/**
 * 删除文件夹以及文件夹的内容
 */
inline fun File?.deleteDir() {
    this?.let {FileUtil.deleteDir(it, false)}

}

/**
 * 删除文件夹以及文件夹的内容
 * @param deleteOriginal 是否删除原来的文件夹
 */
inline fun File?.deleteDir(deleteOriginal:Boolean) {
    this?.let {FileUtil.deleteDir(it, deleteOriginal)}
}

/**
 * 删除文件夹内的文件
 */
inline fun File?.deleteFiles() {
    this?.let {FileUtil.deleteFiles(it)}
}

/**
 * 根据文件名建立文件,不会直接创建
 * @receiver File 需要创建的上层文件夹
 * @param prefix String 文件名称.的前缀,不传为空
 * @param suffix String 文件名称.的后缀,不传为空
 * @param createDirectory Boolean 是否需要生成文件夹
 * @return File 创建的文件
 */
inline fun File.newFile(prefix:String?, suffix:String?, createDirectory:Boolean):File {
    val pre = prefix ?: ""
    val name = suffix?.let {"$pre.$it"} ?: pre
    return this.newFile(name, createDirectory)
}

/**
 * 根据文件名建立文件,不会直接创建
 * @receiver File 需要创建的上层文件夹
 * @param prefix String 文件名称.的前缀,不传为空
 * @param suffix String 文件名称.的后缀,不传为空
 * @return File 创建的文件
 */
inline fun File.newFile(prefix:String?, suffix:String?):File {
    return this.newFile(prefix, suffix, true)
}

/**
 * 根据文件名建立文件,不会直接创建
 * @receiver File 需要创建的上层文件夹
 * @param fileName String 文件名称
 * @param createDirectory Boolean 是否需要生成文件夹
 * @return File 创建的文件
 */
inline fun File.newFile(fileName:String?, createDirectory:Boolean):File {
    if(createDirectory) mkdirs()
    return fileName?.let {File(this, fileName)} ?: this
}

/**
 * 根据文件名建立文件,不会直接创建
 * @receiver File 需要创建的上层文件夹
 * @param fileName String 文件名称
 * @return File 创建的文件
 */
inline fun File.newFile(fileName:String?):File {
    return this.newFile(fileName, true)
}

/**
 * 根据路径直接创建文件
 * @receiver String 文件的路径
 * @return File 创建的文件
 */
inline fun String.createFile():File {
    return File(this).apply {if(!exists()) createNewFile()}
}

/**
 * 在文件夹中根据文件名直接创建文件
 * @receiver File 文件夹的路径
 * @param fileName 文件的名字
 * @return File 创建的文件
 */
inline fun File.createFile(fileName:String):File {
    return File(this, fileName).apply {if(!exists()) createNewFile()}
}

/**
 * 在文件夹中根据文件名直接创建文件
 * @receiver String 文件夹的路径
 * @return File 创建的文件
 */
inline fun String?.asFile():File? {
    return this?.let {File(it)}
}

/**
 * 先删除旧文件再创建新文件
 * @receiver File 文件夹的路径
 * @return File 创建的文件
 */
inline fun File.deleteAndCreateFile():Boolean {
    return this.also {deleteOnExit()}.createNewFile()
}

/**
 * 根据文件路径创建文件，先删除旧文件再创建新文件
 * @receiver File 文件夹的路径
 * @return File 创建的文件
 */
inline fun String.deleteAndCreateFile():File {
    return File(this).apply {
        deleteOnExit()
        createNewFile()
    }
}

/**
 * 在文件夹中根据文件名直接创建文件，先删除旧文件再创建新文件
 * @receiver File 文件夹的路径
 * @param fileName 文件名
 * @return File 创建的文件
 */
inline fun File.deleteAndCreateFile(fileName:String):File {
    return File(this, fileName).apply {
        deleteOnExit()
        createNewFile()
    }
}

/**
 * 根据文件名创建文件夹
 * @receiver File 需要创建的上层文件夹
 * @param fileName String 文件夹名称
 * @param createDirectory Boolean 是否需要生成文件夹
 * @return File 创建的文件
 */
inline fun File.createDir(fileName:String?, createDirectory:Boolean):File {
    return (fileName?.let {File(this, fileName)} ?: this).apply {if(createDirectory) mkdirs()}
}

/**
 * 根据文件名创建文件夹
 * @receiver File 需要创建的上层文件夹
 * @param fileName String 文件夹名称
 * @return File 创建的文件夹
 */
inline fun File.createDir(fileName:String?):File {
    return this.createDir(fileName, true)
}

/**
 * 根据文件名创建文件夹
 * @receiver File 需要创建的上层文件夹
 * @param fileNames String 文件夹名称
 * @return File 创建的文件夹
 */
inline fun File.createDirs(vararg fileNames:String?):File {
    return this.createDir(StringBuilder().apply {
        onEachIndexed {index, c ->
            if(index > 0) {
                append(File.separator)
            }
            append(c)
        }
    }.toString(), true)
}

/**
 * 根据时间戳来创建文件
 * @receiver File 需要创建的上层文件夹
 * @param prefix String 时间戳的前缀,不传为空
 * @param suffix String 文件名称.的后缀,不传为空
 * @return File 创建的文件
 */
inline fun File.timeNameFile(prefix:String?, suffix:String?):File {
    return this.newFile(prefix?.let {"$it${times()}"} ?: "${times()}", suffix)
}

/**
 * 判断是否是文件并且是否存在
 * @receiver File? 文件
 * @return Boolean 是否存在
 */
inline fun File?.isFileExists():Boolean {
    return this?.let {exists() && isFile} ?: false
}

/**
 * 判断是否是文件并且是否存在
 * @receiver String? 文件路径
 * @return Boolean 是否存在
 */
inline fun String?.isFileExists():Boolean {
    return this?.let {File(it).isFileExists()} ?: false
}

/**
 * 判断是否是文件夹并且是否存在
 * @receiver File? 文件
 * @return Boolean 是否存在
 */
inline fun File?.isDirExists():Boolean {
    return this?.let {exists() && isDirectory} ?: false
}

/**
 * 判断是否是文件夹并且是否存在
 * @receiver String? 文件路径
 * @return Boolean 是否存在
 */
inline fun String?.isDirExists():Boolean {
    return this?.let {File(it).isDirExists()} ?: false
}

/**
 * 文件重命名
 * @receiver File 需要重新命名的原文件
 * @param newFile File
 * @return Boolean
 */
inline fun File.renameTo(newFile:String?):Boolean {
    return newFile?.let {
        newFile != this.absolutePath && File(newFile).let {!it.exists() && this.renameTo(it)}
    } ?: false
}

/**
 * 修改文件后缀
 * @receiver File 需要修改的文件
 * @param suffix String 文件的名字.之后的后缀
 * @return Boolean 是否修改成功
 */
inline fun File.renameSuffix(suffix:String):Boolean {
    val nameSuffix = this.getNameSuffix()
    return this.renameTo(nameSuffix.isNotEmpty()
        .condition(absolutePath.replaceAfterLast(nameSuffix, suffix), "$absolutePath.$suffix"))
}

/**
 * 获取文件名的.后缀
 * @receiver File 文件
 * @return String 文件名的.后缀
 */
inline fun File.getNameSuffix():String {
    val fileName = name
    val index = fileName?.lastIndexOf('.', 0, false) ?: -1
    return (index >= 0).condition(fileName.substring(index + 1), "")
}

/**
 * 修改文件的名字，仅仅修改File.name
 * @receiver File 需要修改的文件
 * @param fileName String 文件的名字
 * @return Boolean 是否修改成功
 */
inline fun File.renameFile(fileName:String?):Boolean {
    if(fileName == null) return false
    return this.name != fileName && renameTo(File(this.absolutePath.replaceAfterLast(this.name, fileName)))
}

/**
 * 将输入流写入文件
 * @receiver File? 需要写入的文件
 * @param input InputStream? 文件流
 * @param append Boolean 是否需要追加
 * @return Boolean 是否写入成功
 */
inline fun File?.writeStream(input:InputStream?, append:Boolean):Boolean {
    return input?.readToWrite(this?.outputStream(append))?.getOrDefault(false) ?: false
}

/**
 * 将输入流写入文件
 * @receiver File? 需要写入的文件
 * @param input InputStream? 文件流
 * @return Boolean 是否写入成功
 */
inline fun File?.writeStream(input:InputStream?):Boolean {
    return writeStream(input, false)
}

/**
 * 将输入流写入文件
 * @receiver File? 需要写入的文件
 * @param input InputStream? 文件流
 * @return Boolean 是否写入成功
 */
inline fun File?.appendStream(input:InputStream?):Boolean {
    return writeStream(input, true)
}

/**
 * 将字节数组写入文件
 * @receiver File? 需要写入的文件
 * @param inputArray ByteArray? 需要写入的字节数组
 * @return Boolean 是否写入成功
 */
inline fun File?.writeBytesCatch(inputArray:ByteArray?, append:Boolean):Boolean {
    return inputArray.nonNull({
        this?.outputStream({writeBytes(it)}, append).getResult(false)
    }, false)
}

/**
 * 将字节数组写入文件
 * @receiver File? 需要写入的文件
 * @param inputArray ByteArray? 需要写入的字节数组
 * @return Boolean 是否写入成功
 */
inline fun File?.writeBytesCatch(inputArray:ByteArray?):Boolean {
    return writeBytesCatch(inputArray, false)
}

/**
 * 将字节数组写入文件
 * @receiver File? 需要写入的文件
 * @param inputArray ByteArray? 需要写入的字节数组
 * @return Boolean 是否写入成功
 */
inline fun File?.appendBytesCatch(inputArray:ByteArray?):Boolean {
    return writeBytesCatch(inputArray, true)
}

/**
 * 将字节数组写入文件
 * @receiver File? 需要写入的文件
 * @param text CharSequence? 需要写入的数据
 * @param append Boolean 是否追加在文件后面
 * @return Boolean 是否写入成功
 */
inline fun File?.writeTextCatch(text:String?, append:Boolean):Boolean {
    return writeBytesCatch(text?.toByteArray(), append)
}

/**
 * 将字节数组写入文件
 * @receiver File? 需要写入的文件
 * @param text CharSequence? 需要写入的数据
 * @return Boolean 是否写入成功
 */
inline fun File?.writeTextCatch(text:String?):Boolean {
    return writeTextCatch(text, false)
}

/**
 * 将字节数组写入文件
 * @receiver File? 需要写入的文件
 * @param text CharSequence? 需要写入的数据
 * @return Boolean 是否写入成功
 */
inline fun File?.appendTextCatch(text:String?):Boolean {
    return writeTextCatch(text, true)
}

/**
 * 把文件变成输入流
 * @receiver File? 需要读取的文件
 */
inline fun <R> File.inputStream(block:FileInputStream.() -> R):Result<R> {
    return this.runCatching {FileInputStream(this).use(block)}
}

/**
 * 把文件流出成流
 * @receiver File 需要读取的文件
 */
inline fun <R> File.outputStream(block:FileOutputStream.() -> R, append:Boolean):Result<R> {
    return this.runCatching {FileOutputStream(this, append).use(block)}
}

/**
 * 把文件流出成流
 * @receiver File 需要读取的文件
 */
inline fun <R> File.outputStream(block:FileOutputStream.() -> R):Result<R> {
    return outputStream(block, false)
}

/**
 * 把文件流出成流
 * @receiver File 需要读取的文件
 */
inline fun File?.outputStream(append:Boolean):FileOutputStream? {
    return this?.runCatching {FileOutputStream(this, append)}?.getOrNull()
}

/**
 * 复制文件
 * @param destFile 目标文件
 * @return true: 复制成功 false:复制失败
 */
inline fun File?.copyFile(destFile:File?):Boolean {
    if(this == null || destFile == null || !this.exists()) return false
    if(this.absolutePath == destFile.absolutePath) return false
    return kotlin.runCatching {destFile.apply {mkdirs()}.writeStream(inputStream())}
        .getResult(false)
}

/**
 * 复制文件
 * @param destFile 目标文件
 * @return true: 复制成功 false:复制失败
 */
inline fun File?.copyFile(destFile:String?):Boolean {
    if(this == null || destFile == null || !this.exists()) return false
    if(this.absolutePath == destFile) return false
    return this.copyFile(destFile.asFile())
}

/**
 * 复制文件夹
 * @param destDir 目标文件夹
 * @return true: 复制成功 false:复制失败
 */
inline fun File?.copyDir(destDir:File?):Boolean {
    if(this == null || destDir == null || !this.exists() || this.isFile) return false
    if(this.absolutePath == destDir.absolutePath) return false
    return FileUtil.copyDir(this, destDir)
}

/**
 * 获取文件编码格式
 * @return 文件编码
 */
inline fun File.getFileCharset():String? {
    return inputStream {
        when((read() shl 8) + read()) {
            0xefbb -> "UTF-8"
            0xfffe -> "Unicode"
            0xfeff -> "UTF-16BE"
            else -> "GBK"
        }
    }.getResult("GBK")
}


