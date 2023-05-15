package io.mapomi.android.ui.main.profile

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentProfileBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    lateinit var bind : FragmentProfileBinding

    override fun getFragmentRoot(): View {
        bind = FragmentProfileBinding.inflate(layoutInflater)
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