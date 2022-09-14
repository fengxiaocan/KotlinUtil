package com.app.kotlin

import java.lang.reflect.Method
import kotlin.reflect.KClass

/**
 * 直接反射调用构造函数
 */
inline fun <T> Class<T>.new():T? {
    val constructors = constructors
    for(constructor in constructors) {
        constructor.isAccessible = true
        val parameterTypes = constructor.parameterTypes
        val objects:Array<Any?>? = arrayOfNulls(parameterTypes.size)
        try {
            return constructor.newInstance(objects) as T
        } catch(e:Exception) {
            e.printStackTrace()
        }
    }
    return null
}

/**
 * 直接反射调用构造函数
 */
inline fun <T:Any> KClass<T>.new():T? {
    return this.java.new()
}

/**
 * 直接反射调用构造函数
 * @param parameterTypes
 * @param parameter
 * @return
 */
inline fun <T> Class<T>.new(parameterTypes:Array<Class<*>?>, parameter:Array<Any?>):T? {
    return kotlin.runCatching {this.getConstructor(*parameterTypes).newInstance(*parameter) as T}.getOrNull()
}

/**
 * 直接反射调用构造函数
 *
 * @param parameter 参数数组
 * @return
 */
inline fun <T> Class<T>.new(vararg parameter:Any):T? {
    return kotlin.runCatching {
        parameter.isNotEmpty()
            .condition(getConstructor(*arrayOfClass(parameter)).newInstance(*parameter) as T, this.getConstructor()
                .newInstance() as T)
    }.getOrNull()
}

/**
 * 直接反射调用构造函数
 */
inline fun <T:Any> KClass<T>.new(vararg parameter:Any):T? {
    return this.java.new(parameter)
}

/**
 * 参数变为Class数组
 */
inline fun arrayOfClass(vararg parameter:Any):Array<Class<Any>?> {
    var parameterTypes:Array<Class<Any>?> = arrayOfNulls(parameter.size)
    for(i in parameter.indices) {
        parameterTypes[i] = parameter[i].javaClass
    }
    return parameterTypes
}

/**
 * 获取Java class
 */
inline fun <T> T.javaClass():Class<T>? {
    return kotlin.runCatching {
        val t = this as Object
        t.javaClass as Class<T>?
    }.getOrNull()
}

/**
 * 获取方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T> T.getKtMethod(methodName:String, vararg args:Any):Method? {
    return this.javaClass()?.getJavaMethod(methodName, args)
}

/**
 * 获取方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T> Class<T>.getJavaMethod(methodName:String, vararg args:Any):Method? {
    return kotlin.runCatching {
        var method:Method = args.isNotEmpty()
            .condition(getDeclaredMethod(methodName, *arrayOfClass(args)), getDeclaredMethod(methodName))
        method.isAccessible = true
        method
    }.getOrNull()
}

/**
 * 调用方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T, R> T.invokeKtMethod(methodName:String, vararg args:Any):R? {
    return this.javaClass()?.invokeJavaMethod(methodName, args)
}

/**
 * 调用java文件的静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T, R> Class<T>.invokeJavaMethod(methodName:String, vararg args:Any):R? {
    return kotlin.runCatching {getJavaMethod(methodName, args)?.invoke(null, args) as R}.getOrNull()
}

/**
 * 调用Kotlin文件的静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T, R> Class<T>.invokeKtMethod(methodName:String, vararg args:Any):R? {
    val any:Any? = getJavaStaticField("Companion")
    return any?.invokeKtMethod(methodName, args)
}

/**
 * 调用java静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T:Any, R> KClass<T>.invokeJavaMethod(methodName:String, vararg args:Any):R? {
    return java.invokeJavaMethod(methodName, args)
}

/**
 * 调用kotlin静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T:Any, R> KClass<T>.invokeKtMethod(methodName:String, vararg args:Any):R? {
    return java.invokeKtMethod(methodName, args)
}

/**
 * 获取成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T, R> T.getKtField(fieldName:String):R? {
    return this.javaClass()?.getJavaField(fieldName)
}

/**
 * 修改成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T> T.setKtField(fieldName:String, value:Any?):Boolean {
    return this.javaClass()?.setJavaField(fieldName,value)?:false
}

/**
 * 获取成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T, R> Class<T>.getJavaField(fieldName:String):R? {
    return kotlin.runCatching {
        val field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        field.get(this) as R
    }.getOrNull()
}

/**
 * 修改成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T> Class<T>.setJavaField(fieldName:String, value:Any?):Boolean {
    return kotlin.runCatching {
        val field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(this, value)
        true
    }.getOrDefault(false)
}
/**
 * 获取静态成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T, R> Class<T>.getJavaStaticField(fieldName:String):R? {
    return kotlin.runCatching {
        val field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        field.get(this) as R
    }.getOrNull()
}

/**
 * 修改静态成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T> Class<T>.setJavaStaticField(fieldName:String, value:Any?):Boolean {
    return kotlin.runCatching {
        val field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(this, value)
        true
    }.getOrDefault(false)
}

/**
 * 获取静态成员变量的值
 *
 * @param fieldName
 * @return
 */
inline fun <T:Any, R> KClass<T>.getStaticField(fieldName:String):R? {
    return this.java.getJavaStaticField(fieldName)
}

/**
 * 修改静态成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T:Any> KClass<T>.setStaticField(fieldName:String, value:Any?):Boolean {
    return this.java.setJavaStaticField(fieldName, value)
}
