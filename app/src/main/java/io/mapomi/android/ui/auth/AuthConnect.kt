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

    private lateinit var activity: AuthActivity
    private lateinit var context : Context
    private lateinit var navigation : AuthNavigation

    fun registerActivity( authActivity: AuthActivity ,activityContext: Context, navigation: AuthNavigation){
        activity = authActivity
        context = activityContext
        this.navigation = navigation
    }

    /**
     * 1. 가입 유형 페이지로
     */
    fun gotoTypePage() {
        navigation.changePage(AuthPage.REGISTER_TYPE)
    }

    /**
     * 2. 아이디/비밀번호 페이지로
     */
    fun goAuthPage() {
        navigation.changePage(AuthPage.REGISTER_AUTH)
    }

    /**
     * 3. 정보 입력 페이지로
     */
    fun goInfoPage() {
        navigation.changePage(AuthPage.REGISTER_INFO)
    }

    /**
     * 4. 신분증/장애인증 인증 페이지로
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
        activity.finish()
    }
}