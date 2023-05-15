package io.mapomi.android.remote.retrofit

import io.mapomi.android.constants.*
import io.mapomi.android.remote.dataclass.CRequest
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.remotesources.RemoteInterface
import io.mapomi.android.remote.remotesources.RemoteListener
import okhttp3.MultipartBody
import retrofit2.Call
import io.mapomi.android.system.App.Companion.prefs

class CallImpl(
    val apiNum : Int,
    val callback : RemoteListener,
    val requestBody : CRequest? = null,
    val paramInt0 : Int? = null,
    val paramInt1 : Int? = null,
    val paramStr0 : String? = null,
    val paramStr1 : String? = null,
    val paramStr2 : String? = null,
    val paramBool0 : Boolean? = null,
    val multipartList : List<MultipartBody.Part>? = null,
    val multipart : MultipartBody.Part? = null,
) {

    private val header : HashMap<String,String> get() =
        HashMap<String,String>().apply {
            this[CONTENT_TYPE] = "application/json"
            this[AUTHORIZATION] = prefs.accessToken
            this[REFRESH_TOKEN] = prefs.refreshToken
        }


    fun getCall(remoteApi : RemoteInterface) : Call<CResponse>
    {
        return when(apiNum)
        {

            API_JOIN_ACCOUNT -> remoteApi.joinAccount(header, paramStr0!!, requestBody as JoinRequest)

            else -> throw NoSuchMethodException()

        } as Call<CResponse>
    }
}