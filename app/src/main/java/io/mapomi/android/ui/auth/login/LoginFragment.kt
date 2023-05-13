package io.mapomi.android.ui.auth.login

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentLoginBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var bind : FragmentLoginBinding

    override fun getFragmentRoot(): View {
        bind = FragmentLoginBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigationOnBackPressed() {

    }
}