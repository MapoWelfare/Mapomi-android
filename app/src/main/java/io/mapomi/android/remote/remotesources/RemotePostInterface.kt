package io.mapomi.android.remote.remotesources

import com.google.gson.JsonObject
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.post.PostBuildRequest
import io.mapomi.android.remote.dataclass.response.post.PostDetailResponse
import io.mapomi.android.remote.dataclass.response.post.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RemotePostInterface {

    @POST("accompany/build")
    fun buildAccompanyPost(@HeaderMap header : HashMap<String,String>, @Body body : PostBuildRequest) : Call<CResponse>

    @POST("accompany/{id}/edit")
    fun editAccompanyPost(@HeaderMap header: HashMap<String, String>, @Path("id") id : String, @Body body: PostBuildRequest) : Call<CResponse>

    @POST("accompany/{id}/delete")
    fun deleteAccompanyPost(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<CResponse>

    @POST("accompany/{id}")
    fun readAccompanyPostDetail(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<PostDetailResponse>

    @GET("accompanies")
    fun getAllAccompanyPosts(@HeaderMap header: HashMap<String, String>, @Query("search") search : String, @Query("page") page : Int, @Query("size") size : Int) : Call<PostResponse>

    @POST("help/build")
    fun buildHelpPost(@HeaderMap header : HashMap<String,String>, @Body body : PostBuildRequest) : Call<CResponse>

    @POST("help/{id}/edit")
    fun editHelpPost(@HeaderMap header: HashMap<String, String>, @Path("id") id : String, @Body body: PostBuildRequest) : Call<CResponse>

    @POST("help/{id}/delete")
    fun deleteHelpPost(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<CResponse>

    @POST("help/{id}")
    fun readHelpPostDetail(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<CResponse>

    @GET("helps")
    fun getAllHelpPosts(@HeaderMap header: HashMap<String, String>, @Query("search") search : String, @Query("page") page : Int, @Query("size") size : Int) : Call<PostResponse>


    @POST("posts/{id}/match")
    fun acceptVolunteer(@HeaderMap header: HashMap<String, String>, @Path("id") id: String, @Body body: JsonObject) : Call<CResponse>

    @GET("posts/{id}/match-request")
    fun getVolunteerList(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<CResponse>

    @POST("posts/{id}/match-request")
    fun requestVolunteer(@HeaderMap header: HashMap<String, String>, @Path("id") id : String) : Call<CResponse>


}