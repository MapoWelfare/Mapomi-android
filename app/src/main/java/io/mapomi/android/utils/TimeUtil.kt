package io.mapomi.android.utils

import io.mapomi.android.remote.dataclass.local.PostDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtil {

    private val now = LocalDateTime.now()

    fun getDateTime(pattern : String) : String
    {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return now.format(formatter)
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

    const val MONTH_FORMAT = "MM"
    const val DAY_FORMAT = "dd"
    const val HOUR_FORMAT = "hh"
    const val MINUTE_FORMAT = "mm"
    const val DAY_OF_WEEK_FORMAT = "EE"
    const val POST_DATE_FORMAT = "yyyy-MM-dd"
}