package io.mapomi.android.ui.auth.info

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterInfoBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class InfoFragment : BaseFragment() {

    private lateinit var bind : FragmentRegisterInfoBinding
    val viewModel by activityViewModels<InfoViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterInfoBinding.inflate(layoutInflater)
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