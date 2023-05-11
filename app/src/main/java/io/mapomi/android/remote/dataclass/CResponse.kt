package io.mapomi.android.remote.dataclass

import com.google.gson.annotations.SerializedName

open class CResponse(
    @SerializedName("message") val message : String? = null
)