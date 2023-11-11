package io.mapomi.android.remote.dataclass.response.post

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.post.Post

data class PostDetailResponse(
    @SerializedName("data") val data : Post? = null
) : CResponse()
