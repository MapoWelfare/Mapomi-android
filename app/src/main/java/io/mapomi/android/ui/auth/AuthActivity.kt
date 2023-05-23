package io.mapomi.android.ui.auth

import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.ActivityAuthBinding
import io.mapomi.android.enums.AuthPage
import io.mapomi.android.enums.AuthPage.*
import io.mapomi.android.model.context.SignModel
import io.mapomi.android.model.navigate.AuthNavigation
import io.mapomi.android.system.LogDebug
import io.mapomi.android.ui.auth.login.LoginFragment
import io.mapomi.android.ui.auth.register.RegisterFragment
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.ui.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {

    @Inject
    lateinit var navigation : AuthNavigation

    @Inject
    lateinit var signModel: SignModel

    @Inject
    lateinit var authConnect: AuthConnect

    private val viewModel by viewModels<AuthViewModel>()
    private var fragment : BaseFragment? = null

    override fun onActivityCreate() {

        useBind {
            navigator = navigation
            vm = viewModel
            activity = this@AuthActivity
            lifecycleOwner = this@AuthActivity

        }
        LogDebug(javaClass.name, "[AUTH ACTIVITY]")
        viewModel.signModel.registerAuthActivity(this)

        signModel.checkToken()
    }

    override fun onResume() {
        super.onResume()
        authConnect.registerActivity(this,this,navigation)
    }

    fun inflateFragment(page : AuthPage) : Boolean {

        fragment =
            when(page){
                LOGIN -> {
                    navigation.clearHistory()
                    LoginFragment()
                }
                REGISTER -> RegisterFragment()

            }

        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fc_auth,it).commit()
        }

        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navigation.revealHistory()) return
        else super.onBackPressed()
    }

    fun requestKakaoLoginActivity(userClient : UserApiClient, callback : (OAuthToken?, Throwable?)->Unit, forceWeb: Boolean = false)
    {
        if(!userClient.isKakaoTalkLoginAvailable(this) || forceWeb)
            userClient.loginWithKakaoAccount(this, callback = callback)
        else
            userClient.loginWithKakaoTalk(this, callback = callback)
    }
}