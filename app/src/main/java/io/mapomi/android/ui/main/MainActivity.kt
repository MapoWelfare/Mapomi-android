package io.mapomi.android.ui.main

import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.ActivityMainBinding
import io.mapomi.android.enums.Page
import io.mapomi.android.model.navigate.Navigation
import io.mapomi.android.system.LogDebug
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.ui.base.BaseFragment
import io.mapomi.android.utils.RootViewDeferringInsetsCallback
import javax.inject.Inject
import io.mapomi.android.enums.Page.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject lateinit var navigator : Navigation

    private val viewModel : MainViewModel by viewModels()

    private var fragment : BaseFragment? = null

    override fun onActivityCreate() {
        useBind {
            navigation = navigator
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewModel = viewModel
        }

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

    fun inflateFragment(page: Page) : Boolean {

/*        fragment =
            when(page) {
                HOME ->
            }*/

        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fc_main, it).commit()
            viewModel.setBottomMenuVisibility(it.showBottomBar())
            LogDebug(javaClass.name, "BOTTOM MENU VISIBILITY = ${it.showBottomBar()}")
        }

        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        fragment?.run {
            this.navigationOnBackPressed()
        } ?: run {
            super.onBackPressed()
        }
    }
}