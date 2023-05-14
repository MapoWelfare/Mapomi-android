package io.mapomi.android.ui.auth.certify

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterCertifyBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class CertifyFragment : BaseFragment() {

    lateinit var bind : FragmentRegisterCertifyBinding

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterCertifyBinding.inflate(layoutInflater)
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