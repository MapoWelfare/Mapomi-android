package io.mapomi.android.ui.auth.auth

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterAuthBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class AuthFragment : BaseFragment() {

    private lateinit var bind : FragmentRegisterAuthBinding

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterAuthBinding.inflate(layoutInflater)
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