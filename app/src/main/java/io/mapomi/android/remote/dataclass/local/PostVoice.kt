package io.mapomi.android.remote.dataclass.local

data class PostVoice(
    val id : Int,
    val title : String = "",
    val announce : String? = null,
    val voice : String = "",
    val needInput : Boolean = false,
    val delay : Long = 0
)
