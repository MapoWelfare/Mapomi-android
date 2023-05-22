package io.mapomi.android.ui.auth.login

import android.app.Application
import android.content.ContentValues
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject
import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.mapomi.android.ui.auth.login.AuthResponse
import io.mapomi.android.ui.auth.login.AuthRequest
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


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
        KakaoSdk.init(application, "69fa956201f8bb443af7682a52b376c7")

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(ContentValues.TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Log.e(ContentValues.TAG, "에러 발생 $error")
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(
                            context, callback = mCallback
                        ) // 카카오 이메일 로그인
                    }
                } else if (token != null) {
                    Log.e(ContentValues.TAG, "로그인 성공, 토큰값 : ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        showToast(context, "${user?.kakaoAccount?.profile?.nickname}님 반갑습니다.")
                    }
                    val data = AuthRequest(token.accessToken)
                    connect.goRegister()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = mCallback) // 카카오 이메일 로그인
        }

        val userApiClient = UserApiClient.instance
        if (userApiClient.isKakaoTalkLoginAvailable(context)) {
            userApiClient.loginWithKakaoTalk(context, callback = mCallback)
        } else {
            userApiClient.loginWithKakaoAccount(context, callback = mCallback)
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    private val context = application.applicationContext

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                    showToast(context, "사용자 취소")
                }
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    showToast(context, "접근이 거부 됨(동의 취소)")
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    showToast(context, "유효하지 않은 앱")
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    showToast(context, "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    showToast(context, "요청 파라미터 오류")
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    showToast(context, "유효하지 않은 scope ID")
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    showToast(context, "설정이 올바르지 않음(android key hash)")
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    showToast(context, "서버 내부 에러")
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    showToast(context, "앱이 요청 권한이 없음")
                }
                else -> { // Unknown
                    showToast(context, "기타 에러")
                    val intent = Intent(context, LoginViewModel::class.java)
                    startActivity(context, intent, null)
                    finish(context)
                }

            }
        } else if (token != null) {
            Log.e(ContentValues.TAG, "로그인 성공, 토큰값 : ${token.accessToken}")
            UserApiClient.instance.me { user, error ->
                showToast(context, "${user?.kakaoAccount?.profile?.nickname}님 반갑습니다.")
            }
            connect.goRegister()
        }
    }
    private fun startActivity(context: Context, intent: Intent, options: Bundle?) {
        // Your startActivity implementation
        context.startActivity(intent, options)
    }

    private fun finish(context: Context) {
        // Your finish implementation
        // Note: ViewModel itself cannot finish an activity directly.
        // You can create a LiveData<Boolean> flag in the ViewModel and observe it in the Activity to finish it.
        // Alternatively, you can use an event-based communication mechanism such as LiveData<Event<Unit>> or Kotlin Flow.
    }
}