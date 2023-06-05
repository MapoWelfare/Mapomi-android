package io.mapomi.android.remote.dataclass.post

import com.google.gson.annotations.SerializedName

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
    @SerializedName("content") val content : String? = null
)
