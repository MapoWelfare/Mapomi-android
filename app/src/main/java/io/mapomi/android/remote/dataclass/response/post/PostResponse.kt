package io.mapomi.android.remote.dataclass.response.post

import com.google.gson.annotations.SerializedName
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.post.Pageable
import io.mapomi.android.remote.dataclass.post.Post
import io.mapomi.android.remote.dataclass.post.Sort

data class PostResponse(
    @SerializedName("content") val content : List<Post>? = null,
    @SerializedName("empty") val empty : Boolean? = null,
    @SerializedName("first") val first : Boolean? = null,
    @SerializedName("last") val last : Boolean? = null,
    @SerializedName("number") val number : Int? = null,
    @SerializedName("numberOfElements") val numberOfElements : Int? = null,
    @SerializedName("pageable") val pageable : Pageable? = null,
    @SerializedName("size") val size : Int? = null,
    @SerializedName("sort") val sort : Sort? = null,
    @SerializedName("totalElements") val totalElements : Int? = null,
    @SerializedName("totalPages") val totalPages : Int? = null
) : CResponse()
