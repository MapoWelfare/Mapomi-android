package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.TokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RemoteInterface : RemoteLoginInterface, RemotePostInterface {


    @POST("reissue")
    fun refreshToken(@HeaderMap header: HashMap<String, String>, @Body body: TokenRequest) : Call<CResponse>
}