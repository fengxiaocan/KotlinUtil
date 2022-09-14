package com.app.acc

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.kotlin.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Long.numberOfLeadingZeros
import kotlin.io.path.createTempDirectory
import kotlin.time.measureTime

class MainActivity:AppCompatActivity() {
    var content:String? = "MD5的典型应用是对一段信息（Message）产生信息摘要（Message-Digest）"

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            //Log.e("noah","sha1Hex:"+content.sha1Hex())
            //Log.e("noah","sha256Hex:"+content.sha256Hex())
            //Log.e("noah","sha512Hex:"+content.sha512Hex())
            //Log.e("noah","md5Hex:"+content.md5Hex())
            //Log.e("noah","encodeBase64:"+content.encodeBase64())
            //Log.e("noah","decodeBase64:"+content.encodeBase64().decodeBase64())
            //Log.e("noah","encodeURL:"+content.encodeURL())
            //Log.e("noah","decodeURL:"+content.encodeURL().decodeURL())

            //获取java的静态成员变量的值
//            Log.e("noah", "获取java的静态成员变量的值:" + TestA::class.getStaticField("value1"))
//            //修改java的静态成员变量的值
//            TestA::class.setStaticField("value1",content)
//            //检验修改后的java的静态成员变量的值
//            Log.e("noah", "检验修改后的java的静态成员变量的值:" + TestA::class.getStaticField("value1"))
//            //调用java的静态方法
//            val a1:Unit? = TestA::class.invokeJavaMethod("toast")
//
//            val testA = TestA()
//            //获取java的静态成员变量的值
//            Log.e("noah", "获取java的普通成员变量的值:" + testA.getField("value2"))
//            //修改java的静态成员变量的值
//            testA.setField("value2",content)
//            //检验修改后的java的静态成员变量的值
//            Log.e("noah", "检验修改后的java的普通成员变量的值:" + testA.getField("value2"))
//            //调用java的方法
//            val a2:Unit? = testA.invokeMethod("Log")
//
//
//            //获取Kotlin的静态成员变量的值
//            Log.e("noah", "获取Kotlin的静态成员变量的值:" + TestB::class.getStaticField("value"))
//            //修改java的静态成员变量的值
//            TestB::class.setStaticField("value",content)
//            //检验修改后的Kotlin的静态成员变量的值
//            Log.e("noah", "检验修改后的Kotlin的静态成员变量的值:" + TestB::class.getStaticField("value"))
//            //调用Kotlin的静态方法
//            val a3:Unit? = TestB::class.invokeKotlinMethod("toast")
//
//
//            val testB = TestB()
//            //获取Kotlin的普通成员变量的值
//            Log.e("noah", "获取Kotlin的普通成员变量的值:" + testB.getField("value\$1"))
//            //修改Kotlin的普通成员变量的值
//            testB.setField("value\$1",content)
//            //检验修改后的Kotlin的普通成员变量的值
//            Log.e("noah", "检验修改后的Kotlin的普通成员变量的值:" + testB.getField("value\$1"))
//            //调用Kotlin的方法
//            val a4:Unit? = testB.invokeMethod("Log")
//
//            val declaredFields = TestB::class.java.declaredFields
//            declaredFields.forEach {
//                it.isAccessible = true
//                Log.e("noah",it.name)
//            }

//            Log.e("noah","0x88F25A3F.red = "+ 0x88F25A3F.red().toColorHex())
//            Log.e("noah","0x88F25A3F.blue = "+ 0x88F25A3F.blue().toColorHex())
//            Log.e("noah","0x88F25A3F.green = "+ 0x88F25A3F.green().toColorHex())
//            Log.e("noah","0x88F25A3F.alpha = "+ 0x88F25A3F.alpha().toColorHex())
//            Log.e("noah","0x88F25A3F.setColorBlue = "+ 0x88F25A3F.setColorBlue(0x64).toColorHex())
//            Log.e("noah","0x88F25A3F.setColorGreen = "+ 0x88F25A3F.setColorGreen(0x91).toColorHex())
//            Log.e("noah","0x88F25A3F.setColorRed = "+ 0x88F25A3F.setColorRed(0xAB).toColorHex())
//            Log.e("noah","0x88F25A3F.setColorAlpha = "+ 0x88F25A3F.setColorAlpha(0x9A).toColorHex())
//            Log.e("noah","0x88F25A3F.toColorHex = "+ 0x88F25A3F.toColorHex())
//
//            Log.e("noah", "0x88F25A3F.setColorBlue = " + 0x88F25A3F.setColorBlue(0.5f).toColorHex())
//            Log.e("noah", "0x88F25A3F.setColorGreen = " + 0x88F25A3F.setColorGreen(0.5f).toColorHex())
//            Log.e("noah", "0x88F25A3F.setColorRed = " + 0x88F25A3F.setColorRed(0.5f).toColorHex())
//            Log.e("noah", "0x88F25A3F.setColorAlpha = " + 0x88F25A3F.setColorAlpha(0.5f).toColorHex())
//
//            Log.e("noah", "0x80808080.red = " + 0x80808080.colorRed())
//            Log.e("noah", "0x80808080.blue = " + 0x80808080.colorBlue())
//            Log.e("noah", "0x80808080.green = " + 0x80808080.colorGreen())
//            Log.e("noah", "0x80808080.alpha = " + 0x80808080.colorAlpha())
//
//            Log.e("noah","0xFFCCCCCC = "+ Color.LTGRAY)
//            Log.e("noah","0xFFCCCCCC.red = "+ Color.LTGRAY.red().toColorHex())
//            Log.e("noah","0xFFCCCCCC.blue = "+ Color.LTGRAY.blue().toColorHex())
//            Log.e("noah","0xFFCCCCCC.green = "+ Color.LTGRAY.green().toColorHex())
//            Log.e("noah","0xFFCCCCCC.alpha = "+ Color.LTGRAY.alpha().toColorHex())
//            Log.e("noah","0xFFCCCCCC.setColorBlue = "+ Color.LTGRAY.setColorBlue(0x64).toColorHex())
//            Log.e("noah","0xFFCCCCCC.setColorGreen = "+ Color.LTGRAY.setColorGreen(0x91).toColorHex())
//            Log.e("noah","0xFFCCCCCC.setColorRed = "+ Color.LTGRAY.setColorRed(0xAB).toColorHex())
//            Log.e("noah","0xFFCCCCCC.setColorAlpha = "+ Color.LTGRAY.setColorAlpha(0x9A).toColorHex())
//            Log.e("noah","0xFFCCCCCC.toColorHex = "+ Color.LTGRAY.toColorHex())
//
//            Log.e("noah", "0xFFCCCCCC.setColorBlue = " + Color.LTGRAY.setColorBlue(0.5f).toColorHex())
//            Log.e("noah", "0xFFCCCCCC.setColorGreen = " + Color.LTGRAY.setColorGreen(0.5f).toColorHex())
//            Log.e("noah", "0xFFCCCCCC.setColorRed = " + Color.LTGRAY.setColorRed(0.5f).toColorHex())
//            Log.e("noah", "0xFFCCCCCC.setColorAlpha = " + Color.LTGRAY.setColorAlpha(0.5f).toColorHex())
//
//            Log.e("noah", "0xFFCCCCCC.red = " + Color.LTGRAY.colorRed())
//            Log.e("noah", "0xFFCCCCCC.blue = " + Color.LTGRAY.colorBlue())
//            Log.e("noah", "0xFFCCCCCC.green = " + Color.LTGRAY.colorGreen())
//            Log.e("noah", "0xFFCCCCCC.alpha = " + Color.LTGRAY.colorAlpha())

            Log.e("noah", "getAppSignatures:"+getAppSignatures())
            Log.e("noah", "cacheDir:"+cacheDir.absolutePath)
            Log.e("noah", "codeCacheDir:"+codeCacheDir.absolutePath)
            Log.e("noah", "externalCacheDir:"+externalCacheDir?.absolutePath.noEmpty(""))
            externalCacheDirs?.forEach {
                Log.e("noah", it.absolutePath)
            }
            Log.e("noah", "filesDir:" + filesDir.absolutePath)
            Log.e("noah", "getExternalFilesDir:" + getExternalFilesDir("")?.absolutePath)
            createTempDirectory()
        }

    }
}