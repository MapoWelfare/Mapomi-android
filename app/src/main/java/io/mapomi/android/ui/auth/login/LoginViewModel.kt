package io.mapomi.android.ui.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject


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
        }

        useFlag(signModel.loginSuccessFlag){
            connect.finishPage()
        }
    }


}