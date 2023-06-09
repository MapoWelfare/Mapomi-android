package io.mapomi.android.model.profile

import android.net.Uri
import io.mapomi.android.constants.API_EDIT_PROFILE_IMAGE
import io.mapomi.android.constants.API_GET_MY_PROFILE
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.response.profile.ProfileResponse
import io.mapomi.android.remote.retrofit.CallImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileModel @Inject constructor() : BaseModel() {


    /*******************************************
     **** 프로필
     ******************************************/

    private val _profile = MutableStateFlow<ProfileResponse.Profile?>(null)
    val profile : StateFlow<ProfileResponse.Profile?> get() = _profile


    fun getMyProfile()
    {
        _profile.value = null

        CallImpl(
            API_GET_MY_PROFILE,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /*******************************************
     **** 이미지 변경
     ******************************************/

    private var multiPart : MultipartBody.Part? = null
    val uploadOnApp = MutableStateFlow(false)

    fun convertUriToMultiPart(uri: Uri)
    {
        CoroutineScope(Dispatchers.IO).launch {
            if (!uri.toString().contains("http"))
            {
                multiPart = uiModel.convertImgToUpload(uri)
                uploadOnApp.value = true
            }

            else uploadOnApp.value = false

        }
    }

    /**
     * 프로필 이미지 변경을 요청합니다
     */
    fun editProfileImage()
    {
        CallImpl(
            API_EDIT_PROFILE_IMAGE,
            multipart = multiPart,
            callback = this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /*******************************************
     **** 응답 처리
     ******************************************/

    private fun onMyProfileResponse(response: ProfileResponse)
    {
        response.data?.let {
            _profile.value = it
        }
    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_GET_MY_PROFILE -> onMyProfileResponse(body as ProfileResponse)
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}