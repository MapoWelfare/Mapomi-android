package io.mapomi.android.remote.retrofit

import io.mapomi.android.constants.API_REFRESH_TOKEN
import io.mapomi.android.remote.dataclass.CRequest
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.remotesources.RemoteInterface
import io.mapomi.android.remote.remotesources.RemoteListener
import okhttp3.MultipartBody
import retrofit2.Call

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

        }


    fun getCall(remoteApi : RemoteInterface) : Call<CResponse>
    {
        return when(apiNum){
            API_REFRESH_TOKEN -> remoteApi
            else -> throw NoSuchMethodException()
        } as Call<CResponse>
    }
}