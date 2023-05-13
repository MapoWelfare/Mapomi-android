package io.mapomi.android.ui.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Type
import io.mapomi.android.model.context.SignModel
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

    fun changeType(type : Type){
        this.type.value = type
    }

    fun typeId(text : CharSequence){
        typedId.value = text.toString()
    }

    fun typePassword(text : CharSequence){
        typedPassword.value = text.toString()
    }

    fun goRegister() {
        connect.gotoTypePage()
    }

    fun login(){
        signModel.requestLogin(typedId.value,typedPassword.value)
        showToast("로그인 성공")
    }

}