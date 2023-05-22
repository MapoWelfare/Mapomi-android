package io.mapomi.android.ui.auth.login

import io.mapomi.android.ui.auth.login.AuthResponse
import io.mapomi.android.ui.auth.login.AuthRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {
    @POST("oauth/get")
    @Headers("accept: application/json", "content-type: application/json")
    suspend fun post_users(@Body jsonparams: AuthRequest): Response<AuthResponse>
}