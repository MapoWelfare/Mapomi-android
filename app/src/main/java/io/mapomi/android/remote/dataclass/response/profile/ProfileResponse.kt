package io.mapomi.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.mapomi.android.enums.Type
import io.mapomi.android.remote.dataclass.CResponse

data class ProfileResponse(
    @SerializedName("data") val data : Profile? = null
) : CResponse() {
    data class Profile(
        @SerializedName("nickName") val nickName : String,
        @SerializedName("picture") val picture : String? = null,
        @SerializedName("role") val role : Type? = null
    )
}
