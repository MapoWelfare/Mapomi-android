package io.mapomi.android.ui.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject
import io.mapomi.android.R


@HiltViewModel
class LoginViewModel @Inject constructor(
    val connect : AuthConnect
) : BaseViewModel() {

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 카카오로 시작하기
     */
    fun login() {

        signModel.loginViaKaKao()
        useFlag(signModel.needJoinFlag){
            connect.goRegister()
            uiModel.showToast("반갑습니다. 가입을 완료해주세요")
        }

        useFlag(signModel.loginSuccessFlag){
            connect.finishPage()
            uiModel.showToast(valueModel.getString(R.string.str_welcome))
        }
    }

}