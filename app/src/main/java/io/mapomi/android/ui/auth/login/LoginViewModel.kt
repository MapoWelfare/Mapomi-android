package io.mapomi.android.ui.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Type
import io.mapomi.android.model.context.SignModel
import io.mapomi.android.system.App.Companion.prefs
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val connect : AuthConnect
) : BaseViewModel() {

    val type = MutableStateFlow(Type.DISABLED)
    val typedId = MutableStateFlow("")
    val typedPassword = MutableStateFlow("")

    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeId(text : CharSequence){
        typedId.value = text.toString()
    }

    fun typePassword(text : CharSequence){
        typedPassword.value = text.toString()
    }

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    fun changeType(type : Type){
        this.type.value = type
    }

    fun goRegister() {
        connect.gotoTypePage()
    }

    fun login(){
        signModel.requestLogin(typedId.value,typedPassword.value)
        useFlag(signModel.loginSuccessFlag)
        {
            connect.finishPage()
            showToast(prefs.accessToken)
        }

    }

}