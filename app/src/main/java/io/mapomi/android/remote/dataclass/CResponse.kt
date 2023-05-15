package io.mapomi.android.remote.dataclass

import com.google.gson.annotations.SerializedName

open class CResponse(
    @SerializedName("success") val success : Boolean? = null,
    @SerializedName("message") val message : String? = null,
)