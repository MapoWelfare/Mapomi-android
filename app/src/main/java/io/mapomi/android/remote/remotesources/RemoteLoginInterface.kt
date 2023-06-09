package io.mapomi.android.remote.remotesources

import com.google.gson.JsonObject
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.auth.JoinRequest
import io.mapomi.android.remote.dataclass.request.auth.LoginRequest
import io.mapomi.android.remote.dataclass.response.auth.LoginResponse
import io.mapomi.android.remote.dataclass.response.auth.OAuthGetResponse
import io.mapomi.android.remote.dataclass.response.auth.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteLoginInterface {

    @POST("join")
    fun joinAccount(@HeaderMap header: HashMap<String, String>, @Query("type") type : String, @Body body: JoinRequest) : Call<TokenResponse>

    @POST("login")
    fun loginAccount(@HeaderMap header: HashMap<String, String>, @Body body : LoginRequest) : Call<LoginResponse>

    @POST("oauth/get")
    fun postAccessToken(@Body body : JsonObject) : Call<OAuthGetResponse>

    @POST("check/nickname")
    fun checkRegisterNickname(@Body body: JsonObject) : Call<CResponse>
}