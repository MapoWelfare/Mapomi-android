package io.mapomi.android.utils

import io.mapomi.android.remote.dataclass.local.PostDate
import java.time.LocalDateTime
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

        val dayPatter = DateTimeFormatter.ofPattern(POST_DATE_FORMAT)
        val dayOfWeekPattern = DateTimeFormatter.ofPattern(DAY_OF_WEEK_FORMAT, Locale.KOREA)

        val postDates = mutableListOf<PostDate>()
        var currentDate = now

        while (!currentDate.isAfter(endDate))
        {
            val fullDate = currentDate.format(dayPatter)
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
        val hour = if (timeState) hh + 12 else hh
        val minute = if (mm.isNullOrEmpty()) "00" else mm
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

    private const val TIME_FORMAT = "HH:mm"
    private const val DAY_OF_WEEK_FORMAT = "EE"
    const val POST_DATE_FORMAT = "yyyy-MM-dd"
}