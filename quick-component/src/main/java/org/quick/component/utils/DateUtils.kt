package org.quick.component.utils

import android.annotation.SuppressLint
import android.support.annotation.Size
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author chris zou
 * @Date 2018-01-02
 */
object DateUtils {
    val MILLISECOND: Long = 1
    val SECOND = MILLISECOND * 1000
    val MINUTE = SECOND * 60
    val HOURS = MINUTE * 60
    val DAY = HOURS * 24

    val formatDefault = "yyyy-MM-dd HH:mm:ss"
    val formatDefault2 = "yyyyMMddHHmmss"

    var sdf: SimpleDateFormat? = null

    private fun getDateFormat(patter: String): SimpleDateFormat {
        if (sdf == null)
            sdf = SimpleDateFormat(patter, Locale.CHINA)
        sdf!!.applyPattern(patter)
        return sdf!!
    }

    private fun getCalendar() = Calendar.getInstance(Locale.CHINA)

    private fun getCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance(Locale.CHINA)
        calendar.time = date
        return calendar
    }

    /**
     * 获取时间戳长度
     *
     * @param timestamp
     * @return
     */
    private fun getTimestampLength(timestamp: Long): Long {
        val dateLength = timestamp + "".length
        var result: Long = 1
        for (i in 0 until 13 - dateLength) {
            result *= 10
        }
        return result
    }

    fun formatToStr(timestamp: Long): String = formatToStr(timestamp, formatDefault)

    fun formatToStr(timestamp: Long, patter: String): String = getDateFormat(patter).format(timestamp * getTimestampLength(timestamp))

    fun formatToStr(date: Date): String = formatToStr(date, formatDefault)

    fun formatToStr(date: Date, patter: String): String = getDateFormat(patter).format(date)

    /**
     * 默认为24小时制
     *
     * @param l
     * @return
     */
    fun formatToDate(l: Long): Date = formatToDate(l, formatDefault)

    fun formatToDate(l: Long, patter: String): Date = try {
        getDateFormat(patter).parse(formatToStr(l, patter))
    } catch (e: ParseException) {
        Date()
    }

    fun formatToDate(dateStr: String): Date = formatToDate(dateStr, formatDefault)

    fun formatToDate(dateStr: String, patter: String): Date = try {
        getDateFormat(patter).parse(dateStr)
    } catch (e: ParseException) {
        Date()
    }

    /**
     * 默认为24小时制
     *
     * @param date
     * @return
     */
    fun formatToLong(date: Date): Long = date.time

    fun formatToLong(dateStr: String): Long = formatToLong(dateStr, formatDefault)

    fun formatToLong(dateStr: String, patter: String): Long = try {
        getDateFormat(patter).parse(dateStr).time
    } catch (e: ParseException) {
        getCurrentTimeInMillis()
    }

    /**
     * timestamp1 在 timestamp2 之前
     * timestamp1<timestamp2
     * @param timestamp1
     * @param timestamp2
     */
    fun before(timestamp1: Long, timestamp2: Long): Boolean = timestamp1 < timestamp2


    fun before(timestamp1: String, timestamp2: String) = before(timestamp1, timestamp2, formatDefault)
    /**
     * timestamp1 在 timestamp2 之前
     * timestamp1<timestamp2
     * @param timestamp1
     * @param timestamp2
     */
    fun before(timestamp1: String, timestamp2: String, patter: String): Boolean = formatToDate(timestamp1, patter).before(formatToDate(timestamp2, patter))

    /**
     * timestamp1 在 timestamp2 之前
     * timestamp1<timestamp2
     * @param timestamp1
     * @param timestamp2
     */
    fun before(timestamp1: Date, timestamp2: Date): Boolean = timestamp1.before(timestamp2)

    /**
     * timestamp1 在 timestamp2 之后
     * timestamp1>timestamp2
     * @param timestamp1
     * @param timestamp2
     */
    fun after(timestamp1: Long, timestamp2: Long): Boolean = timestamp1 > timestamp2

    fun after(timestamp1: String, timestamp2: String) = after(timestamp1, timestamp2, formatDefault)
    /**
     * timestamp1 在 timestamp2 之后
     * timestamp1>timestamp2
     * @param timestamp1
     * @param timestamp2
     */
    fun after(timestamp1: String, timestamp2: String, patter: String): Boolean = formatToDate(timestamp1, patter).after(formatToDate(timestamp2, patter))

    /**
     * timestamp1 在 timestamp2 之后
     * timestamp1>timestamp2
     * @param timestamp1
     * @param timestamp2
     */
    fun after(timestamp1: Date, timestamp2: Date): Boolean = timestamp1.after(timestamp2)


    /**
     * 比较时间大小
     * @return 最小的时间
     */
    fun compareBefore(vararg timestamps: Long): Long {
        var temp = -1L
        if (timestamps.isNotEmpty()) {
            temp = timestamps[0]
            (1 until timestamps.size - 1)
                    .asSequence()
                    .filter { temp > timestamps[it] }
                    .forEach { temp = timestamps[it] }
        }
        return temp
    }


    /**
     * 比较时间大小
     * @return 最小的时间
     */
    fun compareBefore(patter: String, @Size(min = 1) vararg timestamps: String): String {
        var temp = ""
        if (timestamps.isNotEmpty()) {
            temp = timestamps[0]
            (1 until timestamps.size - 1)
                    .asSequence()
                    .filter { formatToDate(timestamps[it], patter).before(formatToDate(timestamps[it], patter)) }
                    .forEach { temp = timestamps[it] }
        }
        return temp
    }

    /**
     * 比较时间大小
     * @return 最小的时间
     */
    fun compareBefore(@Size(min = 1) vararg timestamps: Date): Date {
        var temp = getCalendar().time
        if (timestamps.isNotEmpty()) {
            temp = timestamps[0]
            (1 until timestamps.size - 1)
                    .asSequence()
                    .filter { temp.after(timestamps[it]) }
                    .forEach { temp = timestamps[it] }
        }
        return temp
    }

    /**
     * 比较时间大小
     * @return 最大的时间
     */
    fun compareAfter(@Size(min = 1) vararg timestamps: Long): Long {
        var temp = -1L
        if (timestamps.isNotEmpty()) {
            temp = timestamps[0]
            (1 until timestamps.size - 1)
                    .asSequence()
                    .filter { temp < timestamps[it] }
                    .forEach { temp = timestamps[it] }
        }
        return temp
    }

    /**
     * 比较时间大小
     * @return 最大的时间
     */
    fun compareAfter(patter: String, vararg timestamps: String): String {
        var temp = ""
        if (timestamps.isNotEmpty()) {
            temp = timestamps[0]
            (1 until timestamps.size - 1)
                    .asSequence()
                    .filter { formatToDate(timestamps[it], patter).before(formatToDate(timestamps[it], patter)) }
                    .forEach { temp = timestamps[it] }
        }
        return temp
    }

    /**
     * 比较时间大小
     * @return 最大的时间
     */
    fun compareAfter(vararg timestamps: Date): Date {
        var temp = getCalendar().time
        if (timestamps.isNotEmpty()) {
            temp = timestamps[0]
            (1 until timestamps.size - 1)
                    .asSequence()
                    .filter { temp.before(timestamps[it]) }
                    .forEach { temp = timestamps[it] }
        }
        return temp
    }

    /**
     * 格式化秒表
     * 有天的 02 11：11：12，534
     * 有时的 11：11：12，534
     * 有分的 11：12，534
     * 有秒的 11：12，534
     * 有毫秒 11：12，534
     * @param timestamp 单位毫秒
     */
    fun formatDateStopwatch(timestamp: Long): String {
        return if (timestamp > 0) {
            val day = timestamp / DateUtils.DAY
            val hours = (timestamp - DateUtils.DAY * day) / DateUtils.HOURS
            val minute = (timestamp - DateUtils.DAY * day - DateUtils.HOURS * hours) / DateUtils.MINUTE
            val second = (timestamp - DateUtils.DAY * day - DateUtils.HOURS * hours - DateUtils.MINUTE * minute) / DateUtils.SECOND
            val millisecond = (timestamp - DateUtils.DAY * day - DateUtils.HOURS * hours - DateUtils.MINUTE * minute - DateUtils.SECOND * second) / DateUtils.MILLISECOND
            val tempDay = if (day in 0..9) "0$day" else day.toString()
            val tempHours = if (hours in 0..9) "0$hours" else hours.toString()
            val tempMinute = if (minute in 0..9) "0$minute" else minute.toString()
            val tempSecond = if (second in 0..9) "0$second" else second.toString()
            val tempMillisecond = if (millisecond in 0..9) "00$millisecond" else if (millisecond in 10..99) "0$millisecond" else millisecond.toString()

            return when {
                day > 0 -> String.format("%s %s:%s:%s,%s", tempDay, tempHours, tempMinute, tempSecond, tempMillisecond)
                hours > 0 -> String.format("%s:%s:%s,%s", tempHours, tempMinute, tempSecond, tempMillisecond)
                else -> String.format("%s:%s,%s", tempMinute, tempSecond, tempMillisecond)
            }
        } else "00:00,00"
    }

    /**
     * 格式化时间差
     *
     * @param timestamp
     * @param postfix 前缀
     * @param postfix 后缀
     */
    fun formatDateDifference(timestamp: Long, prefix: String, postfix: String): String {

        val day = timestamp / DateUtils.DAY
        val hour = (timestamp - DateUtils.DAY * day) / DateUtils.HOURS
        val minute = (timestamp - DateUtils.DAY * day - DateUtils.HOURS * hour) / DateUtils.MINUTE
        val second = (timestamp - DateUtils.DAY * day - DateUtils.HOURS * hour - DateUtils.MINUTE * minute) / DateUtils.SECOND

        return when {
            day > 0 -> String.format("$prefix%s天%s时%s分%s秒$postfix", day, hour, minute, second)
            hour > 0 -> String.format("$prefix%s时%s分%s秒$postfix", hour, minute, second)
            minute > 0 -> String.format("$prefix%s分%s秒$postfix", minute, second)
            else -> String.format("$prefix%s秒$postfix", second)
        }
    }

    fun getCurrentTimeInMillis() = getCalendar().timeInMillis
    fun getCurrentYear() = getCalendar().get(Calendar.YEAR)
    fun getCurrentMonth() = getCalendar().get(Calendar.MONTH) + 1
    fun getCurrentDay() = getCalendar().get(Calendar.DAY_OF_MONTH)
    fun getCurrentHour() = getCalendar().get(Calendar.HOUR_OF_DAY)
    fun getCurrentMinute() = getCalendar().get(Calendar.MINUTE)
    fun getCurrentSecond() = getCalendar().get(Calendar.SECOND)

    fun getTimeInMillis(date: Date) = date.time
    fun getYear(date: Date) = getCalendar(date).get(Calendar.YEAR)
    fun getMonth(date: Date) = getCalendar(date).get(Calendar.MONTH) + 1
    fun getDay(date: Date) = getCalendar(date).get(Calendar.DAY_OF_MONTH)
    fun getHour(date: Date) = getCalendar(date).get(Calendar.HOUR_OF_DAY)
    fun getMinute(date: Date) = getCalendar(date).get(Calendar.MINUTE)
    fun getSecond(date: Date) = getCalendar(date).get(Calendar.SECOND)
}
