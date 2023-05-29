package io.mapomi.android.ui.main.post.detail

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentPostDetailBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class PostDetailFragment : BaseFragment() {

    private lateinit var bind : FragmentPostDetailBinding
    val viewModel by activityViewModels<PostDetailViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentPostDetailBinding.inflate(layoutInflater)
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