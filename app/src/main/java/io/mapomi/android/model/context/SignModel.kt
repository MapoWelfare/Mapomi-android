package io.mapomi.android.model.context

import android.net.Uri
import io.mapomi.android.constants.API_JOIN_ACCOUNT
import io.mapomi.android.enums.Type
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.request.JoinRequest
import io.mapomi.android.remote.dataclass.response.JoinResponse
import io.mapomi.android.remote.retrofit.CallImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignModel @Inject constructor() : BaseModel(){


    /*******************************************
     **** 로그인
     ******************************************/

    val loginSuccessFlag = MutableStateFlow(false)

    /**
     * 로그인을 요청합니다
     */
    fun requestLogin(id : String, password : String)
    {
        loginSuccessFlag.value = true
    }

    /*******************************************
     **** 회원가입
     ******************************************/

    /**
     * 가입 유형
     */
    val registerType = MutableStateFlow(Type.DISABLED)


    fun changeRegisterType(type: Type)
    {
        registerType.value = type
    }

    private var id = ""
    private var password =""
    val idValidFlag = MutableStateFlow(false)

    private var nickname = ""
    private var location = ""
    private var age = -1
    private var disabilityInfo = ""
    val nicknameValidFlag = MutableStateFlow(false)

    fun setPassword(password: String)
    {
        this.password = password.trim()
    }

    fun requestCheckId(id : String)
    {
        this.id = id.trim()
        idValidFlag.value = true
    }

    fun requestCheckNickname(nickname : String)
    {
        this.nickname = nickname.trim()
        nicknameValidFlag.value = true
    }

    fun setInfo(location : String, age : Int, disabilityInfo : String)
    {
        this.location = location.trim()
        this.age = age
        this.disabilityInfo = disabilityInfo.trim()
    }

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

    val registerSuccessFlag = MutableStateFlow(false)

    /**
     * 회원가입을 요청합니다
     */
    fun requestRegister()
    {
        val request = JoinRequest(
            id = id,
            password = password,
            name = "장애인안승우",
            phoneNum = "01025903605",
            term = true,
            nickname = nickname,
            residence = if (registerType.value == Type.DISABLED) location else "",
            age = if (registerType.value == Type.COMPANION) age else -1,
            type = if (registerType.value == Type.DISABLED) disabilityInfo else ""
        )

        CallImpl(
            API_JOIN_ACCOUNT,
            this,
            request,
            paramStr0 = registerType.value.serverName
        ).apply {
            remote.sendRequestApi(this)
        }

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_JOIN_ACCOUNT -> {
                body as JoinResponse
                registerSuccessFlag.value = body.success!!
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}