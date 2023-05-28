package io.mapomi.android.ui.main.profile.match

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentProfileMatchBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileMatchFragment : BaseFragment() {

    private lateinit var bind : FragmentProfileMatchBinding
    val viewModel by activityViewModels<ProfileMatchViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentProfileMatchBinding.inflate(layoutInflater)
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