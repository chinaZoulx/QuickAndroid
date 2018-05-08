package org.chris.quick.tools

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
        var calendar = Calendar.getInstance(Locale.CHINA)
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
     * @param l
     * @return
     */
    fun formatToLong(l: Long): Long? = formatToLong(l, formatDefault)

    fun formatToLong(l: Long, patter: String): Long? = try {
        getDateFormat(patter).parse(formatToStr(l, patter)).time
    } catch (e: ParseException) {
        getCurrentTimeInMillis()
    }

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
    fun compareBefore(patter: String, vararg timestamps: String): String {
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
    fun compareBefore(vararg timestamps: Date): Date {
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
    fun compareAfter(vararg timestamps: Long): Long {
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
     * 格式化时间
     *
     * @param view
     * @param timestamp
     */
    fun formatDate(timestamp: Long): String {
        val day = timestamp / DAY
        val hour = (timestamp - DAY * day) / HOURS
        val minute = (timestamp - DAY * day - HOURS * hour) / MINUTE
        val second = (timestamp - DAY * day - HOURS * hour - MINUTE * minute) / SECOND

        val prefix = ""
        return when {
            day > 0 -> String.format("$prefix%s天%s时%s分%s秒", day, hour, minute, second)
            hour > 0 -> String.format("$prefix%s时%s分%s秒", hour, minute, second)
            minute > 0 -> String.format("$prefix%s分%s秒", minute, second)
            else -> String.format("$prefix%s秒", second)
        }
    }

    /**
     * 格式化秒表
     */
    fun formatDateStopwatch(timestamp: Long): String {
        return if (timestamp > 0) {
            val minute = timestamp / MINUTE
            val second = timestamp % MINUTE / SECOND
            val millisecond = timestamp % MINUTE % SECOND / MILLISECOND
            String.format("%s:%s,%s", if (minute in 0..9) "0" + minute else minute.toString(), if (second in 0..9) "0" + second else second.toString(), if (millisecond in 0..9) "0" + millisecond else millisecond.toString())
        } else "00:00,00"
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
