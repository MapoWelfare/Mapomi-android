package io.mapomi.android.model.context

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.user.UserApiClient
import io.mapomi.android.constants.*
import io.mapomi.android.enums.Type
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.auth.JoinRequest
import io.mapomi.android.remote.dataclass.request.TokenRequest
import io.mapomi.android.remote.dataclass.response.auth.*
import io.mapomi.android.remote.retrofit.CallImpl
import io.mapomi.android.system.App.Companion.prefs
import io.mapomi.android.system.LogError
import io.mapomi.android.system.LogInfo
import io.mapomi.android.ui.auth.AuthActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignModel @Inject constructor(
) : BaseModel(){

    private val _isLogin = MutableStateFlow(false)
    val isLogin : StateFlow<Boolean> get() = _isLogin

    private fun initSignStatus()
    {
        _errorString.value = ""
        setIsLogin(false)
    }

    fun logout()
    {
        _isLogin.value = false
        saveToken(Token(accessToken = "", refreshToken = ""))
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

            user?.kakaoAccount?.let{
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

    val registerType = MutableStateFlow(Type.DISABLED)
    val nickNameValid = MutableStateFlow(false)
    var nickname = ""

    /**
     * 가입 유형을 변경합니다
     */
    fun changeRegisterType(type: Type)
    {
        registerType.value = type
    }

    /**
     * 닉네임 중복 검사합니다
     */
    fun checkNicknameValid(nickname : String)
    {
        this.nickname = nickname
        CallImpl(
            API_CHECK_NICKNAME,
            this,
            paramStr0 = nickname.trim()
        ).apply {
            remote.sendRequestApi(this)
        }
    }



    val registerSuccessFlag = MutableStateFlow(false)

    /**
     * 회원가입을 요청합니다
     */
    fun requestRegister(phone : String)
    {
        val request = JoinRequest(nickname = nickname.trim(), phoneNum = phone.trim(), accessToken = oAuthTokenTaken)
        CallImpl(
            API_JOIN_ACCOUNT,
            this,
            paramStr0 = registerType.value.serverName,
            requestBody = request
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    /*******************************************
     **** REISSUE
     ******************************************/

    fun checkToken()
    {
        if (_isLogin.value) return

        setIsLogin(false)

        CallImpl(
            API_REFRESH_TOKEN,
            this,
            TokenRequest(
                accessToken = prefs.accessToken,
                refreshToken = prefs.refreshToken
            )
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /*******************************************
     **** 응답 처리
     ******************************************/

    /**
     * 로그인 정보를 설정합니다
     */
    private fun setIsLogin(status : Boolean)
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

    /**
     * 가입 유형을 저장합니다
     */
    private fun saveRegisterType(type: Type)
    {
        registerType.value = type
        LogInfo(javaClass.name,"가입유형: ${type.name}")
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

            saveToken(token)

            token.joined?.let {
                if (it) { //이미 유저인 경우
                    setIsLogin(true)
                    loginSuccessFlag.value = true
                    saveRegisterType(token.type ?: Type.DISABLED)
                }
                else {//새로운 유저인 경우
                    needJoinFlag.value = true
                }
            }
        }
    }

    private fun onTokenResponse(response: TokenResponse)
    {
        response.data?.let {
            saveToken(it)
            saveRegisterType(it.type!!)
            setIsLogin(it.accessToken!=null && it.refreshToken!=null)
        }
    }

    private fun onJoinResponse(response: TokenResponse)
    {
        response.success?.let {
            registerSuccessFlag.value = it
            setIsLogin(it)
            if (it) prefs.setString(REGISTER_TYPE,registerType.value.serverName)
            saveToken(response.data)
        }
    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {

            API_REFRESH_TOKEN -> {
                onTokenResponse(body as TokenResponse)
            }

            API_LOGIN_ACCOUNT -> {
                onResponseLogin(body as LoginResponse)
            }

            API_POST_OAUTH_TOKEN -> {
                onOAuthResponse(body as OAuthGetResponse)
            }

            API_CHECK_NICKNAME -> {
                nickNameValid.value = body.success!!
            }

            API_JOIN_ACCOUNT -> {
                onJoinResponse(body as TokenResponse)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}