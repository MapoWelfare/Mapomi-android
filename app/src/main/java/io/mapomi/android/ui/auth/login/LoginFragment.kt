package io.mapomi.android.ui.auth.login

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentLoginBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var bind : FragmentLoginBinding
    val viewModel by activityViewModels<LoginViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentLoginBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigationOnBackPressed() {

    }
}