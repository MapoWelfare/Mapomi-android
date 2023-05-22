package io.mapomi.android.model.post

import io.mapomi.android.constants.POST_ACCOMPANY
import io.mapomi.android.constants.POST_BUILD
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostModel @Inject constructor() : BaseModel() {

    val postType = MutableStateFlow(POST_ACCOMPANY)
    private val postMode = MutableStateFlow(POST_BUILD)

    fun changePostType(type : Boolean)
    {
        postType.value = type
    }

    fun changePostMode(mode : Boolean)
    {
        postMode.value = mode
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}