package io.mapomi.android.ui.main.help

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentHelpBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class HelpFragment : BaseFragment() {

    lateinit var bind : FragmentHelpBinding
    val viewModel by activityViewModels<HelpViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentHelpBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigationOnBackPressed() {

    }
}