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
     * 추가 정보 입력 페이지로
     */
    fun goRegister() {
        navigation.changePage(AuthPage.REGISTER)
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
        navigation.clearHistory()
    }
}