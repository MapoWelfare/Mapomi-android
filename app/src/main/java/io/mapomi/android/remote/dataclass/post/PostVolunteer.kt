package io.mapomi.android.remote.dataclass.post

data class PostVolunteer(
    val nickname : String,
    val gender : String? = null,
    val age : String? = null,
    var isSelect : Boolean = false
)
