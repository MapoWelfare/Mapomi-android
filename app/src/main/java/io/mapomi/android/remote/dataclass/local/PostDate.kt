package io.mapomi.android.remote.dataclass.local

data class PostDate(
    val fullDate : String,
    val dayOfWeek : String,
    val day : String,
    var today : Boolean,
    var select : Boolean
)
