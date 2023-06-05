package io.mapomi.android.ui.main

import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.ActivityMainBinding
import io.mapomi.android.databinding.ViewMainBottomMenuBinding
import io.mapomi.android.enums.Page
import io.mapomi.android.model.navigate.Navigation
import io.mapomi.android.system.LogDebug
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.ui.base.BaseFragment
import io.mapomi.android.utils.RootViewDeferringInsetsCallback
import javax.inject.Inject
import io.mapomi.android.enums.Page.*
import io.mapomi.android.model.context.SignModel
import io.mapomi.android.ui.main.accompany.AccompanyFragment
import io.mapomi.android.ui.main.help.HelpFragment
import io.mapomi.android.ui.main.post.detail.PostDetailFragment
import io.mapomi.android.ui.main.post.select.PostSelectFragment
import io.mapomi.android.ui.main.post.write.PostWriteFragment
import io.mapomi.android.ui.main.profile.ProfileFragment
import io.mapomi.android.ui.main.profile.history.ProfileHistoryFragment
import io.mapomi.android.ui.main.profile.match.ProfileMatchFragment

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    @Inject
    lateinit var navigator : Navigation

    @Inject
    lateinit var signModel: SignModel

    private val mainViewModel : MainViewModel by viewModels()

    private val bottomViewModel : MainBottomViewModel by viewModels()

    private var fragment : BaseFragment? = null

    override fun onActivityCreate() {
        useBind {
            navigation = navigator
            activity = this@MainActivity
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
        }

        signModel.checkToken()
        inflateBottomMenu()
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

    private fun inflateBottomMenu() {

        useBind {
            DataBindingUtil.inflate<ViewMainBottomMenuBinding>(
                layoutInflater,
                R.layout.view_main_bottom_menu,
                null,
                false
            ).apply {
                vm = bottomViewModel
                lifecycleOwner = this@MainActivity
                flBottomMenu.addView(root)
            }
        }
    }

    fun inflateFragment(page: Page) : Boolean {

        fragment =
            when(page) {
                ACCOMPANY -> {
                    navigator.clearHistory()
                    AccompanyFragment()
                }
                HELP -> {
                    navigator.clearHistory()
                    HelpFragment()
                }
                PROFILE -> {
                    navigator.clearHistory()
                    ProfileFragment()
                }
                POST_WRITE -> {
                    PostWriteFragment()
                }
                POST_DETAIL -> {
                    PostDetailFragment()
                }
                POST_SELECT -> {
                    PostSelectFragment()
                }
                PROFILE_MATCH -> {
                    ProfileMatchFragment()
                }
                PROFILE_HISTORY -> {
                    ProfileHistoryFragment()
                }
            }

        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fc_main, it).commit()
            mainViewModel.setBottomMenuVisibility(it.showBottomBar())
            LogDebug(javaClass.name, "BOTTOM VISIBILITY = ${it.showBottomBar()}")
        }

        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (mainViewModel.netStatus.onRemotePending.value)
            return

        fragment?.run {
            this.navigationOnBackPressed()
        } ?: run {
            super.onBackPressed()
        }
    }
}