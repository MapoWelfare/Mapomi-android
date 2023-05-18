package io.mapomi.android.ui.main.profile

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.databinding.FragmentProfileBinding
import io.mapomi.android.databinding.ViewProfileAuthBinding
import io.mapomi.android.databinding.ViewProfileHistoryBinding
import io.mapomi.android.databinding.ViewProfileListBinding
import io.mapomi.android.databinding.ViewProfileMyBinding

import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    lateinit var bind : FragmentProfileBinding

    override fun getFragmentRoot(): View {
        bind = FragmentProfileBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        inflateChild()
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigationOnBackPressed() {

    }

    /**************v
     *
     * VIEW INFLATING
     *
     ***************/
    private fun inflateChild()
    {
        inflateMy()
        inflateList()
        inflateHistory()
        inflateAuth()
    }

    private fun inflateMy()
    {
        ViewProfileMyBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }

    private fun inflateList()
    {
        ViewProfileListBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }

    private fun inflateHistory()
    {
        ViewProfileHistoryBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }

    private fun inflateAuth()
    {
        ViewProfileAuthBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }
}