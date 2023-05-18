package io.mapomi.android.ui.main.accompany

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentAccompanyBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class AccompanyFragment : BaseFragment() {

    lateinit var bind : FragmentAccompanyBinding
    val viewModel by activityViewModels<AccompanyViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentAccompanyBinding.inflate(layoutInflater)
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