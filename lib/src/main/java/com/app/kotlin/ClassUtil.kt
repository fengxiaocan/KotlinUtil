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
    try {
        return this.getConstructor(*parameterTypes).newInstance(*parameter) as T
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 直接反射调用构造函数
 *
 * @param parameter 参数数组
 * @return
 */
inline fun <T> Class<T>.new(vararg parameter:Any):T? {
    try {
        return if(parameter.isNotEmpty()) {
            this.getConstructor(*arrayOfClass(parameter)).newInstance(*parameter) as T
        } else {
            this.getConstructor().newInstance() as T
        }
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 直接反射调用构造函数
 */
inline fun <T:Any> KClass<T>.new(vararg parameter:Any):T? {
    return this.java.new(parameter)
}

/**
 * 把输入的
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
    try {
        val t = this as Object
        return t.javaClass as Class<T>?
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 调用方法
 *
 * @param methodName
 * @return
 */
inline fun <T, R> T.invokeMethod(methodName:String, vararg args:Any):R? {
    try {
        var method:Method = this.javaClass()!!.getDeclaredMethod(methodName)
        method.isAccessible = true
        return if(args.isNotEmpty()) {
            method.invoke(this, args) as R
        } else {
            method.invoke(this) as R
        }
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 调用java文件的静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T, R> Class<T>.invokeJavaMethod(methodName:String, vararg args:Any):R? {
    try {
        return if(args.isNotEmpty()) {
            var method:Method = this.getDeclaredMethod(methodName, *arrayOfClass(args))
            method.isAccessible = true
            method.invoke(null, args) as R
        } else {
            var method:Method = this.getDeclaredMethod(methodName)
            method.isAccessible = true
            method.invoke(null) as R
        }
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 调用Kotlin文件的静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T, R> Class<T>.invokeKotlinMethod(methodName:String, vararg args:Any):R? {
    val any:Any? = this.getStaticField("Companion")
    any?.let {
        return if(args.isNotEmpty()) {
            any.invokeMethod(methodName, args)
        } else {
            any.invokeMethod(methodName)
        }
    }
    return null
}

/**
 * 调用java静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T:Any, R> KClass<T>.invokeJavaMethod(methodName:String, vararg args:Any):R? {
    return if(args.isNotEmpty()) {
        this.java.invokeJavaMethod(methodName, args)
    } else {
        this.java.invokeJavaMethod(methodName)
    }
}

/**
 * 调用kotlin静态方法
 *
 * @param methodName 方法名
 * @param args 方法参数
 * @return
 */
inline fun <T:Any, R> KClass<T>.invokeKotlinMethod(methodName:String, vararg args:Any):R? {
    return if(args.isNotEmpty()) {
        this.java.invokeKotlinMethod(methodName, args)
    } else {
        this.java.invokeKotlinMethod(methodName)
    }
}

/**
 * 获取成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T, R> T.getField(fieldName:String):R? {
    try {
        val field = this.javaClass()!!.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(this) as R
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 修改成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T> T.setField(fieldName:String, value:Any?) {
    try {
        val field = this.javaClass()!!.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(this, value)
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * 获取静态成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T, R> Class<T>.getStaticField(fieldName:String):R? {
    try {
        val field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(this) as R
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 修改静态成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T> Class<T>.setStaticField(fieldName:String, value:Any?) {
    try {
        val field = this.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(this, value)
    } catch(e:java.lang.Exception) {
        e.printStackTrace()
    }
}

/**
 * 获取静态成员变量的值
 *
 * @param fieldName
 * @return
 */
inline fun <T:Any, R> KClass<T>.getStaticField(fieldName:String):R? {
    return this.java.getStaticField(fieldName)
}

/**
 * 修改静态成员变量的值
 * @param fieldName
 * @return
 */
inline fun <T:Any> KClass<T>.setStaticField(fieldName:String, value:Any?) {
    this.java.setStaticField(fieldName, value)
}
