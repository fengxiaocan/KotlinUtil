package com.app.acc;

import android.util.Log;

public class TestA {
    private static final String value1 = "TestA java static value!";
    private final String value2 = "kotlin里面的out,in的简单理解";

    private void Log() {
        Log.e("noah", "testA 普通方法");
    }

    private static void toast() {
        Log.e("noah", "testA 静态方法");
    }
}
