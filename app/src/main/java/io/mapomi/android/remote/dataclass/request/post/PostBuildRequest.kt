package io.mapomi.android.remote.dataclass.request.post

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CRequest

data class PostBuildRequest(
    @SerializedName("title") val title : String,
    @SerializedName("schedule") val schedule : String ="",
    @SerializedName("departure") val departure : String ="",
    @SerializedName("destination") val destination : String ="",
    @SerializedName("content") val content : String = ""
) : CRequest()
