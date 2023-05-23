package io.mapomi.android.remote.dataclass.request

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CRequest

data class TokenRequest(
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String
) : CRequest()
