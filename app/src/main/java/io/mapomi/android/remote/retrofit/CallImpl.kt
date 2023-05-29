package io.mapomi.android.remote.retrofit

import com.google.gson.JsonObject
import io.mapomi.android.constants.*
import io.mapomi.android.remote.dataclass.CRequest
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.auth.JoinRequest
import io.mapomi.android.remote.dataclass.request.auth.LoginRequest
import io.mapomi.android.remote.dataclass.request.TokenRequest
import io.mapomi.android.remote.dataclass.request.post.PostBuildRequest
import io.mapomi.android.remote.remotesources.RemoteInterface
import io.mapomi.android.remote.remotesources.RemoteListener
import okhttp3.MultipartBody
import retrofit2.Call
import io.mapomi.android.system.App.Companion.prefs

class CallImpl(
    val apiNum : Int,
    val callback : RemoteListener,
    val requestBody : CRequest? = null,
    val paramInt0 : Int? = null,
    val paramInt1 : Int? = null,
    val paramStr0 : String? = null,
    val paramStr1 : String? = null,
    val paramStr2 : String? = null,
    val paramBool0 : Boolean? = null,
    val multipartList : List<MultipartBody.Part>? = null,
    val multipart : MultipartBody.Part? = null,
) {

    private val header : HashMap<String,String> get() =
        HashMap<String,String>().apply {
            this[CONTENT_TYPE] = "application/json"
            this[AUTHORIZATION] = prefs.accessToken
            this[REFRESH_TOKEN] = prefs.refreshToken
        }


    fun getCall(remoteApi : RemoteInterface) : Call<CResponse>
    {
        return when(apiNum)
        {

            API_REFRESH_TOKEN -> remoteApi.refreshToken(header, requestBody as TokenRequest)

            API_LOGIN_ACCOUNT -> remoteApi.loginAccount(header, requestBody as LoginRequest)

            API_POST_OAUTH_TOKEN -> remoteApi.postAccessToken( JsonObject().apply {
                addProperty("accessToken", paramStr0)
            })

            API_JOIN_ACCOUNT -> remoteApi.joinAccount(header, paramStr0!!, requestBody as JoinRequest)

            API_CHECK_NICKNAME -> remoteApi.checkRegisterNickname(JsonObject().apply {
                addProperty("nickName",paramStr0)
            })



            API_BUILD_ACCOMPANY_POST -> remoteApi.buildAccompanyPost(header, requestBody as PostBuildRequest)

            API_EDIT_ACCOMPANY_POST -> remoteApi.editAccompanyPost(header , paramStr0!!, requestBody as PostBuildRequest)

            API_DELETE_ACCOMPANY_POST -> remoteApi.deleteAccompanyPost(header, paramStr0!!)

            API_POST_ACCOMPANY_DETAIL -> remoteApi.readAccompanyPostDetail(header, paramStr0!!)

            API_POST_ACCOMPANY_LIST -> remoteApi.getAllAccompanyPosts(header, paramStr0!!, paramInt0!!, paramInt1!!)



            API_BUILD_HELP_POST -> remoteApi.buildHelpPost(header, requestBody as PostBuildRequest)

            API_EDIT_HELP_POST -> remoteApi.editHelpPost(header , paramStr0!!, requestBody as PostBuildRequest)

            API_DELETE_HELP_POST -> remoteApi.deleteHelpPost(header, paramStr0!!)

            API_POST_HELP_DETAIL -> remoteApi.readHelpPostDetail(header, paramStr0!!)

            API_POST_HELP_LIST -> remoteApi.getAllHelpPosts(header, paramStr0!!, paramInt0!!, paramInt1!!)


            else -> throw NoSuchMethodException()

        } as Call<CResponse>
    }
}