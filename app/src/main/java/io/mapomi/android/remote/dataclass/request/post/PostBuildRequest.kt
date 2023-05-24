package io.mapomi.android.remote.dataclass.request.post

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CRequest

data class PostBuildRequest(
    @SerializedName("title") val title : String,
    @SerializedName("schedule") val schedule : String,
    @SerializedName("duration") val duration : String? = null,
    @SerializedName("departure") val departure : String? =null,
    @SerializedName("destination") val destination : String? = null,
    @SerializedName("content") val content : String? = null
) : CRequest()
