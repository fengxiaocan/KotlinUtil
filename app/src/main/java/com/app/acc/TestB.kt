package com.app.acc

import android.util.Log

class TestB {
    private val value = "TestB final value!"

    private fun Log() {
        Log.e("noah", "TestB log")
    }

    companion object {
        private const val value = "TestB companion static value!"
        private fun toast() {
            Log.e("noah", "TestB toast")
        }
    }
}