package io.mapomi.android.remote.dataclass.response.auth

import com.google.gson.annotations.SerializedName
import io.mapomi.android.enums.Type

data class Token(
    @SerializedName("grantType") val grantType : String? = null,
    @SerializedName("accessToken") val accessToken : String? = null,
    @SerializedName("refreshToken") val refreshToken : String? = null,
    @SerializedName("accessTokenExpireDate") val accessTokenExpireDate : String? = null,
    @SerializedName("joined") val joined : Boolean? = null,
    @SerializedName("role") val type : Type? = null
)
