package io.mapomi.android.remote.dataclass.post

import com.google.gson.annotations.SerializedName
import io.mapomi.android.utils.TimeUtil

data class Post(
    @SerializedName("postId") val postId : String,
    @SerializedName("title") val title : String? = null,
    @SerializedName("schedule") val schedule : String? = null,
    @SerializedName("departure") val departure : String? = null,
    @SerializedName("destination") val destination : String? = null,
    @SerializedName("picture") val picture : String? = null,
    @SerializedName("date") val date : String? = null,
    @SerializedName("complete") val complete : Boolean? = null,
    @SerializedName("duration") val duration : String? = null,
    @SerializedName("content") val content : String? = null,
    @SerializedName("userId") val userId : String,
    @SerializedName("author") val author : String? = null,
    @SerializedName("views") val views : Int? = null,
    @SerializedName("type") val type : String? = null
) {

    fun getYear() : String? {
        schedule?.let {
            val yearStr = it.split(" ")[0].split("-")
            return "${yearStr[0]}년 ${yearStr[1]}월 ${yearStr[2]}일"
        }
        return null
    }

    fun getTime() : String? {
        schedule?.let {
            val timeStr = it.split(" ")[1].split(":")
            val afternoon = timeStr[0].toInt()>12
            val timeStatus = if (afternoon) "오후" else "오전"
            val hh = if (afternoon) timeStr[0].toInt()-12 else timeStr[0]
            val mm = timeStr[1]
            return "$timeStatus ${hh}:${mm}"
        }
        return null
    }

    fun getTimePassed() : String? {
        date?.let {
            return TimeUtil.getTimePassed(it)
        }
        return null
    }

    fun getDurationStr() : String? {
        duration?.let {
            return "${it}분"
        }
        return null
    }

}
