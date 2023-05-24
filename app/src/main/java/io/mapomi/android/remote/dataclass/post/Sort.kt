package io.mapomi.android.remote.dataclass.post

import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("empty") val empty : Boolean? = null,
    @SerializedName("sorted") val sorted : Boolean? = null,
    @SerializedName("unsorted") val unsorted : Boolean? = null,
)
