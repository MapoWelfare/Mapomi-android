package io.mapomi.android.ui.auth.certify

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterCertifyBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class CertifyFragment : BaseFragment() {

    lateinit var bind : FragmentRegisterCertifyBinding
    val viewModel by activityViewModels<CertifyViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterCertifyBinding.inflate(layoutInflater)
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
        viewModel.moveBackPage()
    }
}