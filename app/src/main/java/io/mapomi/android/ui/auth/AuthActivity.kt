package io.mapomi.android.ui.auth

import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.ActivityAuthBinding
import io.mapomi.android.enums.AuthPage
import io.mapomi.android.enums.AuthPage.*
import io.mapomi.android.model.navigate.AuthNavigation
import io.mapomi.android.system.LogDebug
import io.mapomi.android.ui.auth.login.LoginFragment
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.ui.base.BaseFragment
import io.mapomi.android.utils.RootViewDeferringInsetsCallback
import javax.inject.Inject


@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {

    @Inject
    lateinit var navigation : AuthNavigation

    private val viewModel by viewModels<AuthViewModel>()
    private var fragment : BaseFragment? = null

    override fun onActivityCreate() {

        useBind {
            navigator = navigation
            vm = viewModel
            activity = this@AuthActivity
            lifecycleOwner = this@AuthActivity

        }
        LogDebug(javaClass.name, "[LOGIN ACTIVITY]")
        attachInsetsCallback()
    }

    private fun attachInsetsCallback() {
        RootViewDeferringInsetsCallback(
            WindowInsetsCompat.Type.systemBars(),
            WindowInsetsCompat.Type.ime()
        ).apply {
            useBind {
                ViewCompat.setOnApplyWindowInsetsListener(root, this@apply)
                ViewCompat.setWindowInsetsAnimationCallback(root,this@apply)
            }
        }
    }

    fun inflateFragment(page : AuthPage) : Boolean {

        fragment =
            when(page){
                LOGIN -> {
                    navigation.clearHistory()
                    LoginFragment()
                }
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
}