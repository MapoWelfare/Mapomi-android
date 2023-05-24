package io.mapomi.android.remote.dataclass.request.auth

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CRequest

data class LoginRequest(
    @SerializedName("id") val id : String,
    @SerializedName("password") val password : String
) : CRequest()
