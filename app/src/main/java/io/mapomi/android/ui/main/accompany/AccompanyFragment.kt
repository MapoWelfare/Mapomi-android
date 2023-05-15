package io.mapomi.android.ui.main.accompany

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentAccompanyBinding
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class AccompanyFragment : BaseFragment() {

    lateinit var bind : FragmentAccompanyBinding

    override fun getFragmentRoot(): View {
        bind = FragmentAccompanyBinding.inflate(layoutInflater)
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