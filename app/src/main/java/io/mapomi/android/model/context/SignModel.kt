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

    private val registerType = MutableStateFlow(Type.DISABLED)

    fun changeRegisterType(type: Type)
    {
        registerType.value = type
    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

}