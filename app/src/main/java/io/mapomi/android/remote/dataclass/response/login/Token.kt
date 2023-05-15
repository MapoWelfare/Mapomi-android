package io.mapomi.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("grantType") val grantType : String? = null,
    @SerializedName("accessToken") val accessToken : String? = null,
    @SerializedName("refreshToken") val refreshToken : String? = null,
    @SerializedName("accessTokenExpireDate") val accessTokenExpireDate : String? = null,
    @SerializedName("joined") val joined : Boolean? = null
)
