package io.mapomi.android.remote.dataclass.request.auth

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CRequest

data class JoinRequest(
    @SerializedName("nickname") val nickname : String = "",
    @SerializedName("phoneNum") val phoneNum : String = "",
) : CRequest()
