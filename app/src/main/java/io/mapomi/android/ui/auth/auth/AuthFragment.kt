package io.mapomi.android.ui.auth.auth

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterAuthBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class AuthFragment : BaseFragment() {

    private lateinit var bind : FragmentRegisterAuthBinding
    val viewModel by activityViewModels<AuthViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterAuthBinding.inflate(layoutInflater)
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
        viewModel.connect.moveBackPage()
    }
}