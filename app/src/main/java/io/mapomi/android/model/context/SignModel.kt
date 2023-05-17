package io.mapomi.android.model.context

import android.net.Uri
import io.mapomi.android.constants.ACCESS_TOKEN
import io.mapomi.android.constants.API_JOIN_ACCOUNT
import io.mapomi.android.constants.API_LOGIN_ACCOUNT
import io.mapomi.android.constants.REFRESH_TOKEN
import io.mapomi.android.enums.Type
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.dataclass.request.LoginRequest
import io.mapomi.android.remote.dataclass.response.login.JoinResponse
import io.mapomi.android.remote.dataclass.response.login.LoginResponse
import io.mapomi.android.remote.dataclass.response.login.Token
import io.mapomi.android.remote.retrofit.CallImpl
import io.mapomi.android.system.App.Companion.prefs
import io.mapomi.android.system.LogInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignModel @Inject constructor() : BaseModel(){

    private val _isLogin = MutableStateFlow(false)
    val isLogin : StateFlow<Boolean> get() = _isLogin

    /**
     * 로그인 정보를 설정합니다
     */
    fun setIsLogin(status : Boolean)
    {
        LogInfo(javaClass.name, "로그인 상태 변경 : $status")
        _isLogin.value = status
    }

    /**
     * 토큰을 저장합니다
     */
    private fun saveToken(token: Token?)
    {
        token?.let {
            prefs.setString(ACCESS_TOKEN,token.accessToken)
            prefs.setString(REFRESH_TOKEN,token.refreshToken)
        }

    }


    /*******************************************
     **** 로그인
     ******************************************/

    val loginSuccessFlag = MutableStateFlow(false)

    /**
     * 로그인을 요청합니다
     */
    fun requestLogin()
    {


    }

    /*******************************************
     **** 회원가입
     ******************************************/

    val nickNameValid = MutableStateFlow(false)
    var nickname = ""
    var phone = ""
    var term = false

    /**
     * 닉네임 중복 검사합니다
     */
    fun checkNicknameValid(nickname : String)
    {
        this.nickname = nickname.trim()
        nickNameValid.value = true
    }



    val registerSuccessFlag = MutableStateFlow(false)

    /**
     * 회원가입을 요청합니다
     */
    fun requestRegister(phone : String, term : Boolean)
    {
        this.phone = phone.trim()
        this.term = term
        registerSuccessFlag.value = true
    }

    /*    private var multiPart : MultipartBody.Part? = null
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
    }*/

    /*******************************************
     **** 응답 처리
     ******************************************/

    private fun onResponseLogin(response: LoginResponse)
    {
        response.let {

            saveToken(it.token)
            setIsLogin(it.success!!)
            loginSuccessFlag.value = it.success
        }

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_LOGIN_ACCOUNT -> {
                onResponseLogin(body as LoginResponse)
            }

            API_JOIN_ACCOUNT -> {
                body as JoinResponse
                registerSuccessFlag.value = body.success!!
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}