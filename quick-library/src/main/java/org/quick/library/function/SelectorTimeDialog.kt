package org.quick.library.function


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Size
import org.quick.component.QuickDialog
import org.quick.component.utils.DateUtils
import org.quick.component.utils.DevicesUtils
import org.quick.library.R
import org.quick.library.widgets.RollView
import java.util.*

/**
 * 时间选择
 * The class is activity select year and month and day and hour and minute
 */
class SelectorTimeDialog
/**
 * @param context
 * @param resultHandler 完成回调
 * @param startDate     开始时间
 * @param endDate       结束时间
 */
    (
    private val context: Context,
    private val handler: (date: Date, timestamp: Long, dateStr: String) -> Unit,
    startDate: String,
    endDate: String
) {

    private var scrollUnits = SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value
    private val FORMAT_STR = "yyyy-MM-dd HH:mm"

    private lateinit var selectorDialog: Dialog
    private lateinit var year_pv: RollView
    private lateinit var month_pv: RollView
    private lateinit var day_pv: RollView
    private lateinit var hour_pv: RollView
    private lateinit var minute_pv: RollView

    private val MAXMINUTE = 59
    private var MAXHOUR = 23
    private val MINMINUTE = 0
    private var MINHOUR = 0
    private val MAXMONTH = 12

    private var year = arrayListOf<String>()
    private var month = arrayListOf<String>()
    private var day = arrayListOf<String>()
    private var hour = arrayListOf<String>()
    private var minute = arrayListOf<String>()
    private var startYear: Int = 0
    private var startMonth: Int = 0
    private var startDay: Int = 0
    private var startHour: Int = 0
    private var startMininute: Int = 0
    private var endYear: Int = 0
    private var endMonth: Int = 0
    private var endDay: Int = 0
    private var endHour: Int = 0
    private var endMininute: Int = 0
    private var minute_workStart: Int = 0
    private var minute_workEnd: Int = 0
    private var hour_workStart: Int = 0
    private var hour_workEnd: Int = 0
    private var spanYear: Boolean = false
    private var spanMon: Boolean = false
    private var spanDay: Boolean = false
    private var spanHour: Boolean = false
    private var spanMin: Boolean = false
    private val selectedCalender: Calendar
    private val ANIMATORDELAY = 200L
    private val CHANGEDELAY = 90L
    private var workStart_str: String = ""
    private var workEnd_str: String = ""
    private val startCalendar: Calendar
    private val endCalendar: Calendar

    private lateinit var tv_cancle: TextView
    private lateinit var tv_select: TextView
    private lateinit var tv_title: TextView
    private lateinit var year_text: TextView
    private lateinit var month_text: TextView
    private lateinit var day_text: TextView
    private lateinit var hour_text: TextView
    private lateinit var minute_text: TextView


    /**
     * 是否显示动画
     */
    private var isShowAnimator = true
    private var mode: MODE? = null

    enum class SCROLLTYPE private constructor(var value: Int) {

        HOUR(1),
        MINUTE(2)

    }

    enum class MODE constructor(var value: Int) {

        /**
         * 年月
         */
        YM(0),
        /**
         * 年月日
         */
        YMD(1),
        /**
         * 年月日 时分
         */
        YMDHM(2),
        /**
         * 除掉年月日
         */
        DM(3),
        /**
         * 月日
         */
        MD(4)
    }

    init {
        startCalendar = Calendar.getInstance()
        endCalendar = Calendar.getInstance()
        selectedCalender = Calendar.getInstance()
        if (!TextUtils.isEmpty(startDate)) {
            startCalendar.time = DateUtils.toDate(startDate, FORMAT_STR)
        }
        if (!TextUtils.isEmpty(endDate)) {
            endCalendar.time = DateUtils.toDate(endDate, FORMAT_STR)
        }

        selectedCalender.time = Date()
        initDialog()
        initView()
    }


    constructor(
        context: Context,
        resultHandler: (date: Date, timestamp: Long, dateStr: String) -> Unit,
        startDate: String,
        endDate: String,
        workStartTime: String,
        workEndTime: String
    ) : this(context, resultHandler, startDate, endDate) {
        this.workStart_str = workStartTime
        this.workEnd_str = workEndTime
    }

    fun show() {

        if (startCalendar.time.time >= endCalendar.time.time) {
            Toast.makeText(context, "结束时间必须大于开始时间", Toast.LENGTH_LONG).show()
            return
        }

        if (!excuteWorkTime()) return
        initParameter()
        initTimer()
        addListener()
        selectorDialog.show()
    }

    private fun initDialog() {

        selectorDialog = QuickDialog.Builder(context, R.layout.dialog_selector_time)
            .setCanceledOnTouchOutside(true)
            .setGravity(Gravity.BOTTOM)
            .setSize(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            .setAnimStyle(R.style.dialogAnimBottom2Up)
            .createDialog()

    }

    private fun initView() {

        year_pv = selectorDialog.findViewById<View>(R.id.year_pv) as RollView
        month_pv = selectorDialog.findViewById<View>(R.id.month_pv) as RollView
        day_pv = selectorDialog.findViewById<View>(R.id.day_pv) as RollView
        hour_pv = selectorDialog.findViewById<View>(R.id.hour_pv) as RollView
        minute_pv = selectorDialog.findViewById<View>(R.id.minute_pv) as RollView
        tv_cancle = selectorDialog.findViewById<View>(R.id.tv_cancle) as TextView
        tv_select = selectorDialog.findViewById<View>(R.id.tv_select) as TextView
        tv_title = selectorDialog.findViewById<View>(R.id.tv_title) as TextView
        year_text = selectorDialog.findViewById<View>(R.id.year_text) as TextView
        month_text = selectorDialog.findViewById<View>(R.id.month_text) as TextView
        day_text = selectorDialog.findViewById<View>(R.id.day_text) as TextView
        hour_text = selectorDialog.findViewById<View>(R.id.hour_text) as TextView
        minute_text = selectorDialog.findViewById<View>(R.id.minute_text) as TextView

        tv_cancle.setOnClickListener { selectorDialog.dismiss() }
        tv_select.setOnClickListener {
            var date = DateUtils.toStr(selectedCalender.time, FORMAT_STR)
            if (mode != null) {
                when (mode) {
                    SelectorTimeDialog.MODE.YM -> date = DateUtils.toStr(selectedCalender.time, "yyyy-MM")
                    SelectorTimeDialog.MODE.YMD -> date = DateUtils.toStr(selectedCalender.time, "yyyy-MM-dd")
                    SelectorTimeDialog.MODE.MD -> date = DateUtils.toStr(selectedCalender.time, "MM-dd")
                    SelectorTimeDialog.MODE.YMDHM -> date =
                        DateUtils.toStr(selectedCalender.time, "yyyy-MM-dd HH:mm")
                    SelectorTimeDialog.MODE.DM -> date = DateUtils.toStr(selectedCalender.time, "HH:mm")
                }
            }
            handler.invoke(selectedCalender.time, selectedCalender.time.time, date)
            selectorDialog.dismiss()
        }
    }

    private fun initParameter() {

        startYear = startCalendar.get(Calendar.YEAR)
        startMonth = startCalendar.get(Calendar.MONTH) + 1
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH)
        startHour = startCalendar.get(Calendar.HOUR_OF_DAY)
        startMininute = startCalendar.get(Calendar.MINUTE)
        endYear = endCalendar.get(Calendar.YEAR)
        endMonth = endCalendar.get(Calendar.MONTH) + 1
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH)
        endHour = endCalendar.get(Calendar.HOUR_OF_DAY)
        endMininute = endCalendar.get(Calendar.MINUTE)
        spanYear = startYear != endYear
        spanMon = !spanYear && startMonth != endMonth
        spanDay = !spanMon && startDay != endDay
        spanHour = !spanDay && startHour != endHour
        spanMin = !spanHour && startMininute != endMininute
    }

    private fun initTimer() {

        initArrayList()

        if (spanYear) {
            for (i in startYear..endYear) {
                year.add(i.toString())
            }
            for (i in startMonth..MAXMONTH) {
                month.add(fomatTimeUnit(i))
            }
            for (i in startDay..startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(fomatTimeUnit(i))
            }
            if (scrollUnits and SCROLLTYPE.HOUR.value != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour))
            } else {
                for (i in startHour..MAXHOUR) {
                    hour.add(fomatTimeUnit(i))
                }
            }

            if (scrollUnits and SCROLLTYPE.MINUTE.value != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute))
            } else {
                for (i in startMininute..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            }

        } else if (spanMon) {
            year.add(startYear.toString())
            for (i in startMonth..endMonth) {
                month.add(fomatTimeUnit(i))
            }
            for (i in startDay..startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(fomatTimeUnit(i))
            }
            if (scrollUnits and SCROLLTYPE.HOUR.value != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour))
            } else {
                for (i in startHour..MAXHOUR) {
                    hour.add(fomatTimeUnit(i))
                }
            }

            if (scrollUnits and SCROLLTYPE.MINUTE.value != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute))
            } else {
                for (i in startMininute..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            }
        } else if (spanDay) {
            year.add(startYear.toString())
            month.add(fomatTimeUnit(startMonth))
            for (i in startDay..endDay) {
                day.add(fomatTimeUnit(i))
            }
            if (scrollUnits and SCROLLTYPE.HOUR.value != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour))
            } else {
                for (i in startHour..MAXHOUR) {
                    hour.add(fomatTimeUnit(i))
                }
            }

            if (scrollUnits and SCROLLTYPE.MINUTE.value != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute))
            } else {
                for (i in startMininute..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            }

        } else if (spanHour) {
            year.add(startYear.toString())
            month.add(fomatTimeUnit(startMonth))
            day.add(fomatTimeUnit(startDay))

            if (scrollUnits and SCROLLTYPE.HOUR.value != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour))
            } else {
                for (i in startHour..endHour) {
                    hour.add(fomatTimeUnit(i))
                }

            }

            if (scrollUnits and SCROLLTYPE.MINUTE.value != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute))
            } else {
                for (i in startMininute..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            }


        } else if (spanMin) {
            year.add(startYear.toString())
            month.add(fomatTimeUnit(startMonth))
            day.add(fomatTimeUnit(startDay))
            hour.add(fomatTimeUnit(startHour))


            if (scrollUnits and SCROLLTYPE.MINUTE.value != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute))
            } else {
                for (i in startMininute..endMininute) {
                    minute.add(fomatTimeUnit(i))
                }
            }
        }

        loadComponent()

    }

    private fun excuteWorkTime(): Boolean {

        val res = true
        if (!TextUtils.isEmpty(workStart_str) && !TextUtils.isEmpty(workEnd_str)) {
            val start = workStart_str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val end = workEnd_str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            hour_workStart = Integer.parseInt(start[0])
            minute_workStart = Integer.parseInt(start[1])
            hour_workEnd = Integer.parseInt(end[0])
            minute_workEnd = Integer.parseInt(end[1])
            val workStartCalendar = Calendar.getInstance()
            val workEndCalendar = Calendar.getInstance()
            workStartCalendar.time = startCalendar.time
            workEndCalendar.time = endCalendar.time
            workStartCalendar.set(Calendar.HOUR_OF_DAY, hour_workStart)
            workStartCalendar.set(Calendar.MINUTE, minute_workStart)
            workEndCalendar.set(Calendar.HOUR_OF_DAY, hour_workEnd)
            workEndCalendar.set(Calendar.MINUTE, minute_workEnd)


            val startTime = Calendar.getInstance()
            val endTime = Calendar.getInstance()
            val startWorkTime = Calendar.getInstance()
            val endWorkTime = Calendar.getInstance()

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY))
            startTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE))
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY))
            endTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE))

            startWorkTime
                .set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY))
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE))
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY))
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE))


            if (startTime.time.time == endTime.time.time || startWorkTime.time.time < startTime.time.time && endWorkTime.time.time < startTime.time.time) {
                Toast.makeText(context, "Wrong parames!", Toast.LENGTH_LONG).show()
                return false
            }
            startCalendar.time = if (startCalendar.time.time < workStartCalendar.time
                    .time
            )
                workStartCalendar
                    .time
            else
                startCalendar.time
            endCalendar.time = if (endCalendar.time.time > workEndCalendar.time.time)
                workEndCalendar
                    .time
            else
                endCalendar.time
            MINHOUR = workStartCalendar.get(Calendar.HOUR_OF_DAY)
            MAXHOUR = workEndCalendar.get(Calendar.HOUR_OF_DAY)

        }
        return res


    }

    private fun fomatTimeUnit(unit: Int): String {

        return if (unit < 10) "0$unit" else unit.toString()
    }

    private fun initArrayList() {

        if (year == null) year = ArrayList()
        if (month == null) month = ArrayList()
        if (day == null) day = ArrayList()
        if (hour == null) hour = ArrayList()
        if (minute == null) minute = ArrayList()
        year.clear()
        month.clear()
        day.clear()
        hour.clear()
        minute.clear()
    }


    private fun addListener() {

        year_pv.setOnSelectListener { text ->
            selectedCalender.set(Calendar.YEAR, Integer.parseInt(text))
            monthChange()
        }
        month_pv.setOnSelectListener { text ->
            selectedCalender.set(Calendar.DAY_OF_MONTH, 1)
            selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1)
            dayChange()
        }
        day_pv.setOnSelectListener { text ->
            selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text))
            hourChange()
        }
        hour_pv.setOnSelectListener { text ->
            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text))
            //                if(mode.value!=MODE.DM.value){
            minuteChange()
            //                }
        }
        minute_pv.setOnSelectListener { text -> selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text)) }

    }

    private fun loadComponent() {

        year_pv.setData(year)
        month_pv.setData(month)
        day_pv.setData(day)
        hour_pv.setData(hour)
        minute_pv.setData(minute)

        year_pv.selected = selectedCalender.get(Calendar.YEAR).toString() + ""
        month_pv.selected = if (selectedCalender.get(Calendar.MONTH) > 9)
            selectedCalender.get(Calendar.MONTH).toString() + ""
        else
            "0" + (selectedCalender.get(Calendar.MONTH) + 1)
        day_pv.selected = if (selectedCalender.get(Calendar.DAY_OF_MONTH) > 9)
            selectedCalender.get(Calendar.DAY_OF_MONTH).toString() + ""
        else
            "0" + selectedCalender.get(Calendar.DAY_OF_MONTH)
        hour_pv.selected = if (selectedCalender.get(Calendar.HOUR_OF_DAY) > 9)
            selectedCalender.get(Calendar.HOUR_OF_DAY).toString() + ""
        else
            "0" + selectedCalender.get(Calendar.HOUR_OF_DAY)
        minute_pv.selected = if (selectedCalender.get(Calendar.MINUTE) > 9)
            selectedCalender.get(Calendar.MINUTE).toString() + ""
        else
            "0" + selectedCalender.get(Calendar.MINUTE)
        excuteScroll()
    }

    private fun excuteScroll() {
        year_pv.setCanScroll(year.size > 1)
        month_pv.setCanScroll(month.size > 1)
        day_pv.setCanScroll(day.size > 1)
        hour_pv.setCanScroll(hour.size > 1 && scrollUnits and SCROLLTYPE.HOUR.value == SCROLLTYPE.HOUR.value)
        minute_pv.setCanScroll(minute.size > 1 && scrollUnits and SCROLLTYPE.MINUTE.value == SCROLLTYPE.MINUTE.value)
    }

    private fun monthChange() {

        month.clear()
        val selectedYear = selectedCalender.get(Calendar.YEAR)
        if (selectedYear == startYear) {
            for (i in startMonth..MAXMONTH) {
                month.add(fomatTimeUnit(i))
            }
        } else if (selectedYear == endYear) {
            for (i in 1..endMonth) {
                month.add(fomatTimeUnit(i))
            }
        } else {
            for (i in 1..MAXMONTH) {
                month.add(fomatTimeUnit(i))
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month[0]) - 1)
        month_pv.setData(month)
        month_pv.setSelected(0)
        excuteAnimator(ANIMATORDELAY, month_pv)

        month_pv.postDelayed({ dayChange() }, CHANGEDELAY)

    }

    private fun dayChange() {

        day.clear()
        val selectedYear = selectedCalender.get(Calendar.YEAR)
        val selectedMonth = selectedCalender.get(Calendar.MONTH) + 1
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (i in startDay..selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(fomatTimeUnit(i))
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (i in 1..endDay) {
                day.add(fomatTimeUnit(i))
            }
        } else {
            for (i in 1..selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day.add(fomatTimeUnit(i))
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day[0]))
        day_pv.setData(day)
        day_pv.setSelected(0)
        excuteAnimator(ANIMATORDELAY, day_pv)

        day_pv.postDelayed({ hourChange() }, CHANGEDELAY)
    }

    private fun hourChange() {

        if (scrollUnits and SCROLLTYPE.HOUR.value == SCROLLTYPE.HOUR.value) {
            hour.clear()
            val selectedYear = selectedCalender.get(Calendar.YEAR)
            val selectedMonth = selectedCalender.get(Calendar.MONTH) + 1
            val selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH)

            if (selectedYear == startYear && selectedMonth == startMonth &&
                selectedDay == startDay
            ) {
                for (i in startHour..MAXHOUR) {
                    hour.add(fomatTimeUnit(i))
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth &&
                selectedDay == endDay
            ) {
                for (i in MINHOUR..endHour) {
                    hour.add(fomatTimeUnit(i))
                }
            } else {

                for (i in MINHOUR..MAXHOUR) {
                    hour.add(fomatTimeUnit(i))
                }

            }
            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour[0]))
            hour_pv.setData(hour)
            hour_pv.setSelected(0)
            excuteAnimator(ANIMATORDELAY, hour_pv)
        }
        hour_pv.postDelayed({ minuteChange() }, CHANGEDELAY)

    }

    private fun minuteChange() {

        if (scrollUnits and SCROLLTYPE.MINUTE.value == SCROLLTYPE.MINUTE.value) {
            minute.clear()
            val selectedYear = selectedCalender.get(Calendar.YEAR)
            val selectedMonth = selectedCalender.get(Calendar.MONTH) + 1
            val selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH)
            val selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY)

            if (selectedYear == startYear && selectedMonth == startMonth &&
                selectedDay == startDay && selectedHour == startHour
            ) {
                for (i in startMininute..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth &&
                selectedDay == endDay && selectedHour == endHour
            ) {
                for (i in MINMINUTE..endMininute) {
                    minute.add(fomatTimeUnit(i))
                }
            } else if (selectedHour == hour_workStart) {
                for (i in minute_workStart..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            } else if (selectedHour == hour_workEnd) {
                for (i in MINMINUTE..minute_workEnd) {
                    minute.add(fomatTimeUnit(i))
                }
            } else {
                for (i in MINMINUTE..MAXMINUTE) {
                    minute.add(fomatTimeUnit(i))
                }
            }
            selectedCalender.set(Calendar.MINUTE, Integer.parseInt(minute[0]))
            minute_pv.setData(minute)
            minute_pv.setSelected(0)
            excuteAnimator(ANIMATORDELAY, minute_pv)

        }
        excuteScroll()
    }

    private fun excuteAnimator(ANIMATORDELAY: Long, view: View) {

        if (isShowAnimator) {
            val pvhX = PropertyValuesHolder.ofFloat(
                "alpha", 1f,
                0f, 1f
            )
            val pvhY = PropertyValuesHolder.ofFloat(
                "scaleX", 1f,
                1.3f, 1f
            )
            val pvhZ = PropertyValuesHolder.ofFloat(
                "scaleY", 1f,
                1.3f, 1f
            )
            ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ)
                .setDuration(ANIMATORDELAY).start()
        }
    }


    fun setNextBtTip(str: String) {

        tv_select.text = str
    }

    fun setTitle(str: String) {

        tv_title.text = str
    }

    fun disScrollUnit(@Size(min = 1) vararg scrolltypes: SCROLLTYPE): Int {

        if (scrolltypes.isEmpty()) {
            scrollUnits = SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value
        }
        for (scrolltype in scrolltypes) {
            scrollUnits = scrollUnits xor scrolltype.value
        }
        return scrollUnits
    }

    /**
     * @param mode The mode is activity display mode for control hour and minute
     * @see .mode
     */
    fun setMode(mode: MODE) {

        this.mode = mode
        when (mode) {
            SelectorTimeDialog.MODE.YM -> {
                disScrollUnit()
                hour_pv.visibility = View.VISIBLE
                minute_pv.visibility = View.VISIBLE
                day_pv.visibility = View.GONE
                day_text.visibility = View.GONE
                hour_pv.visibility = View.GONE
                minute_pv.visibility = View.GONE
                hour_text.visibility = View.GONE
                minute_text.visibility = View.GONE
            }
            SelectorTimeDialog.MODE.YMD -> {
                disScrollUnit(SCROLLTYPE.HOUR, SCROLLTYPE.MINUTE)
                hour_pv.visibility = View.GONE
                minute_pv.visibility = View.GONE
                hour_text.visibility = View.GONE
                minute_text.visibility = View.GONE
            }
            SelectorTimeDialog.MODE.MD -> {
                disScrollUnit(SCROLLTYPE.HOUR, SCROLLTYPE.MINUTE)
                year_pv.visibility = View.GONE
                year_text.visibility = View.GONE

                hour_pv.visibility = View.GONE
                hour_text.visibility = View.GONE

                minute_pv.visibility = View.GONE
                minute_text.visibility = View.GONE
            }
            SelectorTimeDialog.MODE.YMDHM -> {
                disScrollUnit()
                hour_pv.visibility = View.VISIBLE
                minute_pv.visibility = View.VISIBLE
                hour_text.visibility = View.VISIBLE
                minute_text.visibility = View.VISIBLE
            }
            SelectorTimeDialog.MODE.DM -> {
                disScrollUnit()
                year_pv.visibility = View.GONE
                month_pv.visibility = View.GONE
                day_pv.visibility = View.GONE

                year_text.visibility = View.GONE
                month_text.visibility = View.GONE
                day_text.visibility = View.GONE
            }
        }
    }

    fun setIsLoop(isLoop: Boolean) {

        this.year_pv.setIsLoop(isLoop)
        this.month_pv.setIsLoop(isLoop)
        this.day_pv.setIsLoop(isLoop)
        this.hour_pv.setIsLoop(isLoop)
        this.minute_pv.setIsLoop(isLoop)
    }

    fun setSelectDate(dateStr: String) {

        selectedCalender.time = DateUtils.toDate(dateStr, FORMAT_STR)
    }

    fun setSelectDate(date: Date) {

        selectedCalender.time = date
    }

    fun setShowAnimator(isShowAnimator: Boolean) {

        this.isShowAnimator = isShowAnimator
    }

    class Builder(var context: Context, var listener: (date: Date, timestamp: Long, dateStr: String) -> Unit) {
        private var mode: MODE? = null
        private var startTime = ""
        private var endTime = ""
        private var isLoop = true

        fun setMode(mode: MODE): Builder {
            this.mode = mode
            return this
        }

        fun setStartTime(startTime: String): Builder {
            this.startTime = startTime
            return this
        }

        fun setEndTime(endTime: String): Builder {
            this.endTime = endTime
            return this
        }

        fun isLoop(isLoop: Boolean): Builder {
            this.isLoop = isLoop
            return this
        }

        fun build(): SelectorTimeDialog {

            if (TextUtils.isEmpty(startTime))
                startTime = DateUtils.currentToStr(DateUtils.YMDHM)
            if (TextUtils.isEmpty(endTime))
                endTime = DateUtils.beforeYearToStr(1)
            if (mode == null)
                mode = MODE.YMDHM

            val dialog = SelectorTimeDialog(context, listener, startTime, endTime)
            dialog.setMode(mode!!)
            dialog.setIsLoop(isLoop)
            return dialog
        }
    }

}
