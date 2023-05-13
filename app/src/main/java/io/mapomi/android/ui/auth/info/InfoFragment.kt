package io.mapomi.android.ui.auth.info

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterInfoBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class InfoFragment : BaseFragment() {

    private lateinit var bind : FragmentRegisterInfoBinding

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterInfoBinding.inflate(layoutInflater)
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