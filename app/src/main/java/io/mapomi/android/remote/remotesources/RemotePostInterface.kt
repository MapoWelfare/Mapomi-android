package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.post.PostBuildRequest
import io.mapomi.android.remote.dataclass.response.post.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RemotePostInterface {

    @POST("post/build")
    fun buildPost(@HeaderMap header : HashMap<String,String>, @Body body : PostBuildRequest) : Call<CResponse>

    @POST("post/{id}/edit")
    fun editPost(@HeaderMap header: HashMap<String, String>, @Path("id") id : String, @Body body: PostBuildRequest) : Call<CResponse>

    @POST("post/{id}/delete")
    fun deletePost(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<CResponse>

    @POST("post/{id}")
    fun readPostDetail(@HeaderMap header: HashMap<String, String>, @Path("id") id: String) : Call<CResponse>

    @GET("posts")
    fun getAllPosts(@HeaderMap header: HashMap<String, String>, @Query("search") search : String, @Query("page") page : Int, @Query("size") size : Int) : Call<PostResponse>

}