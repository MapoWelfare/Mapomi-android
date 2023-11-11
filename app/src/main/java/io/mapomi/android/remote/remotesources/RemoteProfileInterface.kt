package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.response.profile.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Part

interface RemoteProfileInterface {

    @POST("users/my-profile")
    fun getMyProfile(@HeaderMap header: HashMap<String,String>) : Call<ProfileResponse>

    @POST("users/edit/image")
    fun editProfileImage(@HeaderMap header: HashMap<String, String>, @Part part: MultipartBody.Part) : Call<CResponse>
}