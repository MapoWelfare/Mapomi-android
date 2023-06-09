package io.mapomi.android.ui.main.post.select

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentPostSelectBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class PostSelectFragment : BaseFragment() {

    private lateinit var bind : FragmentPostSelectBinding
    val viewModel by activityViewModels<PostSelectViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentPostSelectBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
/*        viewModel.getRemoteVolunteerList()*/
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigationOnBackPressed() {
        viewModel.moveBackPage()
    }
}