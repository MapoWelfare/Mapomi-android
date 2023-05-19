package io.mapomi.android.ui.main.post.write

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentPostWriteBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class PostWriteFragment : BaseFragment() {

    private lateinit var bind : FragmentPostWriteBinding
    val viewModel by activityViewModels<PostWriteViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentPostWriteBinding.inflate(layoutInflater)
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
        viewModel.moveBackPage()
    }
}