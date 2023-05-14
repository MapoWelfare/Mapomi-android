package io.mapomi.android.ui.main.home

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentHomeBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var bind : FragmentHomeBinding

    override fun getFragmentRoot(): View {
       bind = FragmentHomeBinding.inflate(layoutInflater)
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

    }
}