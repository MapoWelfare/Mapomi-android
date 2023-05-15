package io.mapomi.android.remote.dataclass.request

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CRequest

data class JoinRequest(
    @SerializedName("id") val id : String = "",
    @SerializedName("password") val password : String = "",
    @SerializedName("name") val name : String = "",
    @SerializedName("phoneNum") val phoneNum : String = "",
    @SerializedName("term") val term : Boolean = false,
    @SerializedName("nickname") val nickname : String = "",
    @SerializedName("residence") val residence : String = "",
    @SerializedName("age") val age : Int = 0,   //장애 정보
    @SerializedName("type") val type : String = "",
) : CRequest()
