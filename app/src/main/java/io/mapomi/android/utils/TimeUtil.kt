package io.mapomi.android.utils

import io.mapomi.android.remote.dataclass.local.PostDate
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtil {

    private var now = LocalDateTime.now()

    private fun getDateTime(pattern : String) : String
    {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.now().format(formatter)
    }

    fun getPostDateList(
        callback : (List<PostDate>) -> Unit
    )
    {
        val endDate = now.plusWeeks(2)

        val dayPattern = DateTimeFormatter.ofPattern(POST_DATE_FORMAT)
        val dayOfWeekPattern = DateTimeFormatter.ofPattern(DAY_OF_WEEK_FORMAT, Locale.KOREA)

        val postDates = mutableListOf<PostDate>()
        var currentDate = now

        while (!currentDate.isAfter(endDate))
        {
            val fullDate = currentDate.format(dayPattern)
            val dayOfWeek = currentDate.format(dayOfWeekPattern)
            val postDate = PostDate(fullDate,dayOfWeek,fullDate.split("-")[2], today = false, select = false)
            postDates.add(postDate)
            currentDate = currentDate.plusDays(1)
        }

        postDates[0].apply {
            this.select = true
            this.today = true
        }

        callback(postDates)

    }

    fun makeRequestSchedule(date : String, timeState : Boolean, hh : String, mm : String?) : String
    {
        val hour = if (timeState) hh.toInt() + 12 else hh.toInt()
        val minute = if (mm.isNullOrEmpty()||mm=="0") "00" else mm
        return "$date $hour:$minute"
    }

    fun makeRequestSchedule() : String
    {
        return "${getDateTime(POST_DATE_FORMAT)} ${getDateTime(TIME_FORMAT)}"
    }

    fun splitSchedule(schedule : String) : List<Any>
    {
        val dateTimeRegex = """\d{4}-(\d{2})-\d{2} (\d{2}):(\d{2})""".toRegex()
        val matchResult = dateTimeRegex.find(schedule)

        return if (matchResult != null && matchResult.groupValues.size >= 4) {
            val hh = matchResult.groupValues[2].toInt()
            val hour = if (hh>12) hh - 12 else hh
            listOf(matchResult.groupValues[1], hh>12, hour , matchResult.groupValues[3])
        }
        else emptyList()

    }

    fun isTimeBeforeCurrent(hh:String,mm:String, isAfternoon:Boolean) : Boolean {
        val hour = if (isAfternoon) hh.toInt()+12 else hh.toInt()
        val minute = if (mm.isNotEmpty()) mm.toInt() else 0
        val compareTime = LocalTime.of(hour,minute)
        return compareTime.isBefore(LocalTime.now())
    }

    fun getTimePassed(date : String) : String {

        val dateTime = SimpleDateFormat(DATE_TIME_FORMAT,Locale.KOREA).parse(date.replace('T', ' '))
        val timeGap = (System.currentTimeMillis() - dateTime!!.time)/ 1000L / 3600

        return when{
            timeGap >= 365 * 24 ->
                "${(timeGap / (365 * 24)).toInt()}년전"
            timeGap >= 24 * 30->
                "${(timeGap / 24 / 30).toInt()}개월 전"
            timeGap >= 24 ->
                "${(timeGap / 24).toInt()}일 전"
            timeGap >= 1 ->
                "${timeGap.toInt()}시간 전"
            else ->
                "방금 전"
        }
    }

    private const val TIME_FORMAT = "HH:mm"
    private const val DAY_OF_WEEK_FORMAT = "EE"
    private const val POST_DATE_FORMAT = "yyyy-MM-dd"
    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
}