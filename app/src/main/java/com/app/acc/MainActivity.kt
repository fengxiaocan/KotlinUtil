package com.app.acc

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.kotlin.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            Log.e("noah", "获取java的静态成员变量的值:" + TestA::class.getStaticField("value1"))
            //修改java的静态成员变量的值
            TestA::class.setStaticField("value1",content)
            //检验修改后的java的静态成员变量的值
            Log.e("noah", "检验修改后的java的静态成员变量的值:" + TestA::class.getStaticField("value1"))
            //调用java的静态方法
            val a1:Unit? = TestA::class.invokeJavaMethod("toast")

            val testA = TestA()
            //获取java的静态成员变量的值
            Log.e("noah", "获取java的普通成员变量的值:" + testA.getField("value2"))
            //修改java的静态成员变量的值
            testA.setField("value2",content)
            //检验修改后的java的静态成员变量的值
            Log.e("noah", "检验修改后的java的普通成员变量的值:" + testA.getField("value2"))
            //调用java的方法
            val a2:Unit? = testA.invokeMethod("Log")


            //获取Kotlin的静态成员变量的值
            Log.e("noah", "获取Kotlin的静态成员变量的值:" + TestB::class.getStaticField("value"))
            //修改java的静态成员变量的值
            TestB::class.setStaticField("value",content)
            //检验修改后的Kotlin的静态成员变量的值
            Log.e("noah", "检验修改后的Kotlin的静态成员变量的值:" + TestB::class.getStaticField("value"))
            //调用Kotlin的静态方法
            val a3:Unit? = TestB::class.invokeKotlinMethod("toast")


            val testB = TestB()
            //获取Kotlin的普通成员变量的值
            Log.e("noah", "获取Kotlin的普通成员变量的值:" + testB.getField("value\$1"))
            //修改Kotlin的普通成员变量的值
            testB.setField("value\$1",content)
            //检验修改后的Kotlin的普通成员变量的值
            Log.e("noah", "检验修改后的Kotlin的普通成员变量的值:" + testB.getField("value\$1"))
            //调用Kotlin的方法
            val a4:Unit? = testB.invokeMethod("Log")

            val declaredFields = TestB::class.java.declaredFields
            declaredFields.forEach {
                it.isAccessible = true
                Log.e("noah",it.name)
            }
        }
    }
}