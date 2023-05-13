package io.mapomi.android.ui.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.model.context.SignModel
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    val type = MutableStateFlow(DISABLED)
    val typedId = MutableStateFlow("")
    val typedPassword = MutableStateFlow("")

    fun changeType(type : Int){
        this.type.value = type
    }

    fun typeId(text : CharSequence){
        typedId.value = text.toString()
    }

    fun typePassword(text : CharSequence){
        typedPassword.value = text.toString()
    }

    fun goRegister() {

    }

    fun login(){
        signModel.requestLogin(typedId.value,typedPassword.value)
        showToast("로그인 성공")
    }

    companion object {
        const val DISABLED = 0
        const val COMPANION = 1
        const val RELATED = 2
    }
}