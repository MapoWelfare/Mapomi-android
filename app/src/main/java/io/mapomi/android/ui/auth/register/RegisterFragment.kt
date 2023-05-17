package io.mapomi.android.ui.auth.register

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private lateinit var bind : FragmentRegisterBinding
    val viewModel by activityViewModels<RegisterViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterBinding.inflate(layoutInflater)
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