package io.mapomi.android.model.context

import io.mapomi.android.enums.Type
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignModel @Inject constructor() : BaseModel(){


    /*******************************************
     **** 로그인
     ******************************************/
    fun requestLogin(id : String, password : String)
    {

    }

    /*******************************************
     **** 회원가입
     ******************************************/

    /**
     * 가입 유형
     */
    private val registerType = MutableStateFlow(Type.DISABLED)


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
        this.location = location
        this.age = age
        this.disabilityInfo = disabilityInfo
    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}