package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.request.TokenRequest
import io.mapomi.android.remote.dataclass.response.auth.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RemoteInterface : RemoteLoginInterface, RemotePostInterface, RemoteProfileInterface {


    @POST("reissue")
    fun refreshToken(@HeaderMap header: HashMap<String, String>, @Body body: TokenRequest) : Call<TokenResponse>
}