package io.mapomi.android.ui.auth.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val connect: AuthConnect
) : BaseViewModel() {

    var typedId = ""
    val typedPassword = MutableStateFlow("")
    val typedRePassword = MutableStateFlow("")
    val idValid get() = signModel.idValidFlag
    val passwordValid = MutableStateFlow(false)
    val passwordSame = MutableStateFlow(false)

    fun typeId(text : CharSequence)
    {
        typedId = text.toString()
    }

    fun typePassword(text: CharSequence)
    {
        text.toString().let {
            typedPassword.value = it
            passwordValid.value = it.length in 2..12
            passwordSame.value = it == typedRePassword.value
        }
    }

    fun typeRePassword(text: CharSequence)
    {
        text.toString().let {
            typedRePassword.value = it
            passwordSame.value = it == typedPassword.value
        }

    }

    /**
     * 아이디 중복확인을 합니다
     */
    fun checkIdValid()
    {
        signModel.requestCheckId(typedId)
    }

    /**
     * 다음 페이지로 이동합니다
     */
    fun moveNext()
    {
        signModel.setPassword(typedPassword.value)
        connect.goInfoPage()
    }
}