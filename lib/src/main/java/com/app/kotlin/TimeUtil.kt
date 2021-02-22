package com.app.kotlin

import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 大于当前时间值
 */
inline fun Long?.greaterThanCurrTime():Boolean {
    return this?.let {it > System.currentTimeMillis()} ?: false
}

/**
 * 小于当前时间值
 */
inline fun Long?.lessThanCurrTime():Boolean {
    return this?.let {it < System.currentTimeMillis()} ?: true
}

/**
 * 判断当前时间是否在两个数值之间
 */
inline fun betweenTime(time1:Long, time2:Long):Boolean {
    return System.currentTimeMillis().between(time1, time2)
}

/**
 * 获取当前时间的秒数
 */
inline fun currentTimeSecs():Long {
    return System.currentTimeMillis() / 1000
}

/**
 * 获取运行方法的时长,以毫秒为单位
 */
inline fun runTimeMillis(block:() -> Unit):Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

/**
 * 获取运行方法的时长,以微秒为单位
 * 1 纳秒=0.001 微秒
 */
inline fun runTimeMicros(block:() -> Unit):Long {
    return runTimeNanos(block) / 1000
}

/**
 * 获取运行方法的时长,以纳秒为单位
 * 1 纳秒=0.000001 毫秒
 */
inline fun runTimeNanos(block:() -> Unit):Long {
    val start = System.nanoTime()
    block()
    return System.nanoTime() - start
}

/**
 * 格式化Long时间的公元年份
 *
 * @return int Date是哪一年
 */
inline fun Long?.getYear():Int {
    return this?.let {Date(it).year + 1900} ?: 1970
}

/**
 * 格式化Long时间是一年内第几个月
 * @return int 一年内第几个月 0为第一个月
 */
inline fun Long?.getMonth():Int {
    return this?.let {Date(it).month + 0} ?: 0
}

/**
 * 得到现在是一个月内的第几天
 */
inline fun Long?.getDay():Int {
    return this?.let {Date(this).date} ?: 0
}

/**
 * 得到现在是一个星期内的第几天
 */
inline fun Long?.getWeek():Int {
    return this?.let {Date(this).day} ?: 0
}

/**
 * 格式化为小时
 */
inline fun Long?.getHour():Int {
    return this?.let {Date(this).hours} ?: 0
}

/**
 * 格式化为分钟数
 */
inline fun Long?.getMinutes():Int {
    return this?.let {Date(this).minutes} ?: 0
}

/**
 * 格式化为秒数
 */
inline fun Long?.getSeconds():Int {
    return this?.let {Date(this).seconds} ?: 0
}

/**
 * 判断输入的long时间是否是今年
 */
inline fun Long?.isThisYear():Boolean {
    return this?.let {getYear() == System.currentTimeMillis().getYear()} ?: false
}

/**
 * 根据传入的格式来格式化时间
 * @param type 日期格式
 * @return 传入格式类型的时间字符串
 */
inline fun Long?.formatTime(type:String):String {
    return SimpleDateFormat(type).format(Date(this ?: 0))
}

/**
 * 传入对应的时间字符串和对应的时间格式,转化为long类型的时间毫秒值
 * @param date 例如:2016-02-23
 * @param type 时间格式:yyyy-MM-dd
 */
inline fun String?.formatTime(type:String):Long {
    try {
        if(!this.isNullOrBlank()) {
            return SimpleDateFormat(type).parse(this).time
        }
    } catch(e:ParseException) {
    }
    return 0
}

/**
 * 对比两个时间是否是同一天
 */
inline fun Long.isSameDate(date:Long):Boolean {
    val cal1 = Date(this)
    val cal2 = Date(date)
    val isSameYear = cal1.year == cal2.year
    val isSameMonth = isSameYear && cal1.month == cal2.month
    return isSameMonth && cal1.date == cal2.date
}

/**
 * 是否是今天
 */
inline fun Long.isToday():Boolean {
    return this.isSameDate(System.currentTimeMillis())
}

/**
 * 是否是今天
 */
inline fun Long.isYesterday():Boolean {
    return System.currentTimeMillis().isSameDate(this + 24 * 60 * 60 * 1000)
}

inline fun getBeiJinTime():Long {
    return try {
        val url = URL("http://www.bjtime.cn")
        val uc = url.openConnection() // 生成连接对象
        uc.connect() // 发出连接
        uc.date // 取得网站日期时间
    } catch(e:Exception) {
        return System.currentTimeMillis()
    }
}
