package io.mapomi.android.ui.main.help

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentHelpBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class HelpFragment : BaseFragment() {

    lateinit var bind : FragmentHelpBinding

    override fun getFragmentRoot(): View {
        bind = FragmentHelpBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {

    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigationOnBackPressed() {

    }
}