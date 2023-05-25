package io.mapomi.android.ui.auth.login

import android.content.Context
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject
import android.app.Application


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val application: Application,
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
            UserApiClient.instance.me { user, error ->
                showToast(context, "${user?.kakaoAccount?.profile?.nickname}님 반갑습니다.")
            }
            connect.goRegister()
        }

        useFlag(signModel.loginSuccessFlag){
            connect.finishPage()
        }
    }
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    private val context = application.applicationContext
}