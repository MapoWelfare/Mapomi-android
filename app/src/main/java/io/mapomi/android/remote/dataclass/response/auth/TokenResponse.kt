package io.mapomi.android.remote.dataclass.response.auth

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CResponse

data class TokenResponse(
    @SerializedName("data") val data : Token? = null
) : CResponse()
