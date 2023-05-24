package io.mapomi.android.model.post

import io.mapomi.android.constants.API_BUILD_POST
import io.mapomi.android.constants.POST_ACCOMPANY
import io.mapomi.android.constants.POST_BUILD
import io.mapomi.android.constants.POST_EDIT
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.post.PostBuildRequest
import io.mapomi.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostModel @Inject constructor() : BaseModel() {

    /*******************************************
     **** BUILD, EDIT
     ******************************************/

    val flagPrepareBuild = MutableStateFlow(false)
    val flagPrepareEdit = MutableStateFlow(false)
    val flagUploadSuccess = MutableStateFlow(false)

    val postType = MutableStateFlow(POST_ACCOMPANY)
    private val postMode = MutableStateFlow(POST_BUILD)

    fun changePostType(type : Boolean)
    {
        postType.value = type
    }

    private fun changePostMode(mode : Boolean)
    {
        postMode.value = mode
    }

    /*******************************************
     **** 플래그
     ******************************************/

    fun startBuild(type : Boolean)
    {
        flagPrepareBuild.value = false
        changePostType(type)
        changePostMode(POST_BUILD)
        flagPrepareBuild.value = true
    }

    fun startEdit(type : Boolean)
    {
        flagPrepareEdit.value = false
        changePostType(type)
        changePostMode(POST_EDIT)
        flagPrepareEdit.value = true
    }

    fun requestUploadPost(buildRequest: PostBuildRequest)
    {
/*        CallImpl(
            API_BUILD_POST,
            this,
            buildRequest
        ).apply {
            remote.sendRequestApi(this)
        }*/
        flagUploadSuccess.value = true
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}