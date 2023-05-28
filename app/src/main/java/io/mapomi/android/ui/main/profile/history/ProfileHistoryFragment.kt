package io.mapomi.android.ui.main.profile.history

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentProfileHistoryBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileHistoryFragment : BaseFragment() {

    private lateinit var bind : FragmentProfileHistoryBinding
    val viewModel by activityViewModels<ProfileHistoryViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentProfileHistoryBinding.inflate(layoutInflater)
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