package io.mapomi.android.ui.auth.type

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentRegisterTypeBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class TypeFragment : BaseFragment() {

    private lateinit var bind : FragmentRegisterTypeBinding
    val viewModel by activityViewModels<TypeViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterTypeBinding.inflate(layoutInflater)
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
        viewModel.connect.moveBackPage()
    }
}