package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.dataclass.response.JoinResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteLoginInterface {

    @POST("join")
    fun joinAccount(@HeaderMap header : HashMap<String,String>, @Query("type") type : String, @Body body: JoinRequest) : Call<JoinResponse>
}