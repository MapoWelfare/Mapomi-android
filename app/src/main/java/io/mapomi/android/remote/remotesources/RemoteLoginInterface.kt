package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.dataclass.request.LoginRequest
import io.mapomi.android.remote.dataclass.response.login.JoinResponse
import io.mapomi.android.remote.dataclass.response.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteLoginInterface {

    @POST("join")
    fun joinAccount(@HeaderMap header : HashMap<String,String>, @Query("type") type : String, @Body body: JoinRequest) : Call<JoinResponse>

    @POST("login")
    fun loginAccount(@HeaderMap header: HashMap<String, String>, @Body body : LoginRequest) : Call<LoginResponse>
}