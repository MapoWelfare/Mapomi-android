package io.mapomi.android.model.context

import android.graphics.Bitmap
import android.net.Uri
import io.mapomi.android.enums.Type
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.utils.FileUtil
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

    /**
     * 로그인을 요청합니다
     */
    fun requestLogin(id : String, password : String)
    {

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
        registerSuccessFlag.value = true
    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}