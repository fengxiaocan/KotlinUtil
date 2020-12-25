package com.app.acc

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.kotlin.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    var content: String? = "MD5的典型应用是对一段信息（Message）产生信息摘要（Message-Digest）"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            Log.e("noah","sha1Hex:"+content.sha1Hex())
            Log.e("noah","sha256Hex:"+content.sha256Hex())
            Log.e("noah","sha512Hex:"+content.sha512Hex())
            Log.e("noah","md5Hex:"+content.md5Hex())
            Log.e("noah","encodeBase64:"+content.encodeBase64())
            Log.e("noah","decodeBase64:"+content.encodeBase64().decodeBase64())
            Log.e("noah","encodeURL:"+content.encodeURL())
            Log.e("noah","decodeURL:"+content.encodeURL().decodeURL())
        }
    }
}