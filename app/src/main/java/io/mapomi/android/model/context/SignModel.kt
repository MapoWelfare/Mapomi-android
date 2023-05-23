package io.mapomi.android.model.context

import android.net.Uri
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import io.mapomi.android.constants.*
import io.mapomi.android.enums.Type
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.dataclass.request.LoginRequest
import io.mapomi.android.remote.dataclass.response.login.JoinResponse
import io.mapomi.android.remote.dataclass.response.login.LoginResponse
import io.mapomi.android.remote.dataclass.response.login.OAuthGetResponse
import io.mapomi.android.remote.dataclass.response.login.Token
import io.mapomi.android.remote.retrofit.CallImpl
import io.mapomi.android.system.App.Companion.prefs
import io.mapomi.android.system.LogDebug
import io.mapomi.android.system.LogError
import io.mapomi.android.system.LogInfo
import io.mapomi.android.ui.auth.AuthActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class SignModel @Inject constructor() : BaseModel(){

    private val _isLogin = MutableStateFlow(false)
    val isLogin : StateFlow<Boolean> get() = _isLogin

    private fun initSignStatus()
    {
        _errorString.value = ""
        setIsLogin(false)
    }

    /*******************************************
     **** 카카오 로그인
     ******************************************/

    val loginSuccessFlag = MutableStateFlow(false)
    val needJoinFlag = MutableStateFlow(false)

    private lateinit var authIntentActivity : AuthActivity
    private lateinit var loginKaKao : UserApiClient

    private var oAuthTokenTaken = ""
    private val _errorString = MutableStateFlow("")

    fun registerAuthActivity(activity: AuthActivity)
    {
        authIntentActivity = activity
    }

    private fun initKaKaoSdk()
    {
        if (!::loginKaKao.isInitialized)
            loginKaKao = UserApiClient()
    }

    /**
     * 카카오 로그인을 요청합니다
     */
    fun loginViaKaKao()
    {
        initSignStatus()
        initKaKaoSdk()
        authIntentActivity.requestKakaoLoginActivity(loginKaKao,::onLoginKaKao)
    }

    /**
     * 토큰을 취득하여, 카카오 계정 정보 취득을 합니다.
     */
    private fun onLoginKaKao(token : OAuthToken?, error : Throwable?)
    {
        error?.let{
            if(error is AuthError){
                if(error.statusCode == 302){
                    authIntentActivity.requestKakaoLoginActivity(loginKaKao,::onLoginKaKao, forceWeb = true)
                    return
                }
            }
            _errorString.value = it.toString()
            LogError(error)
            return
        }
        token?.let{
            oAuthTokenTaken = token.accessToken
            LogInfo(javaClass.name,"카카오 로그인 성공 : $token")
            getKaKaoEmail(token.accessToken)

        }?:run{
            _errorString.value = "토큰 존재하지 않음"
            LogError(javaClass.name,"토큰이 존재하지 않습니다.")
        }
    }

    private fun getKaKaoEmail(accessToken : String){
        loginKaKao.me { user, error ->

            error?.let{
                LogError(error)

            }

            user?.kakaoAccount?.let{/*
                loginEmail = it.email.toString()
                username = it.name.toString()*/
                requestOAuth(accessToken)
                return@me
            }


        }

    }

    private fun requestOAuth(accessToken : String)
    {
        CallImpl(
            API_POST_OAUTH_TOKEN,
            this,
            paramStr0 = accessToken
        ).apply {
            remote.sendRequestApi(this)
        }
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

    private fun onResponseLogin(response: LoginResponse)
    {
        response.let {

            saveToken(it.token)
            setIsLogin(it.success!!)
            loginSuccessFlag.value = it.success
        }

    }

    private fun onOAuthResponse(response: OAuthGetResponse)
    {
        response.data?.let { token ->
            token.joined?.let {
                if (it) { //이미 유저인 경우
                    saveToken(token)
                    setIsLogin(true)
                    loginSuccessFlag.value = true
                    LogDebug(javaClass.name,"이미 회원가입")
                }
                else {//새로운 유저인 경우
                    needJoinFlag.value = true
                    LogDebug(javaClass.name,"회원가입 필요 ${loginSuccessFlag.value}")
                }
            }
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

            API_POST_OAUTH_TOKEN -> {
                onOAuthResponse(body as OAuthGetResponse)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}