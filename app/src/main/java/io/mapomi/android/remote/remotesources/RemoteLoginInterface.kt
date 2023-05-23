package io.mapomi.android.remote.remotesources

import com.google.gson.JsonObject
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.dataclass.request.LoginRequest
import io.mapomi.android.remote.dataclass.request.TokenRequest
import io.mapomi.android.remote.dataclass.response.login.LoginResponse
import io.mapomi.android.remote.dataclass.response.login.OAuthGetResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteLoginInterface {

    @POST("join")
    fun joinAccount(@Query("type") type : String, @Body body: JoinRequest) : Call<CResponse>

    @POST("login")
    fun loginAccount(@HeaderMap header: HashMap<String, String>, @Body body : LoginRequest) : Call<LoginResponse>

    @POST("oauth/get")
    fun postAccessToken(@Body body : JsonObject) : Call<OAuthGetResponse>

    @POST("check/nickname")
    fun checkRegisterNickname(@Body body: JsonObject) : Call<CResponse>
}