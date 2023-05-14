package io.mapomi.android.ui.auth

import android.content.Context
import android.content.Intent
import io.mapomi.android.enums.AuthPage
import io.mapomi.android.model.navigate.AuthNavigation
import io.mapomi.android.ui.main.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthConnect @Inject constructor() {

    private lateinit var context : Context
    private lateinit var navigation : AuthNavigation

    fun registerActivity(activityContext: Context, navigation: AuthNavigation){
        context = activityContext
        this.navigation = navigation
    }

    /**
     * 가입 유형 페이지로
     */
    fun gotoTypePage() {
        navigation.changePage(AuthPage.REGISTER_TYPE)
    }

    /**
     * 아이디/비밀번호 페이지로
     */
    fun goAuthPage() {
        navigation.changePage(AuthPage.REGISTER_AUTH)
    }

    /**
     * 정보 입력 페이지로
     */
    fun goInfoPage() {
        navigation.changePage(AuthPage.REGISTER_INFO)
    }

    /**
     * 신분증/장애인증 인증 페이지로
     */
    fun goCertifyPage() {
        navigation.changePage(AuthPage.REGISTER_CERTIFY)
    }

    /**
     * 뒤로 이동
     */
    fun moveBackPage() {
        navigation.revealHistory()
    }

    /**
     * 로그인 혹은 가입 완료
     */
    fun finishPage(){
        context.startActivity(
            Intent(context,MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        )
    }
}