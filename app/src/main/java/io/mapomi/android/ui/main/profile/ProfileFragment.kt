package io.mapomi.android.ui.main.profile

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.*

import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var bind : FragmentProfileBinding
    val viewModel by activityViewModels<ProfileViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentProfileBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        inflateChild()
        viewModel.getRemoteMyProfile()
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
        inflateAppbar()
        inflateMy()
        inflateHistory()
        inflateList()
        inflateAuth()
    }

    private fun inflateAppbar()
    {
        DataBindingUtil.inflate<ViewAccompanyAppbarBinding>(layoutInflater, R.layout.view_accompany_appbar,null,false).apply {
            needSearch = false
            lifecycleOwner = viewLifecycleOwner
            bind.flAppbar.addView(root)
        }
    }

    private fun inflateMy()
    {
        ViewProfileMyBinding.inflate(layoutInflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }

    private fun inflateList()
    {
        ViewProfileListBinding.inflate(layoutInflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }

    private fun inflateHistory()
    {
        ViewProfileHistoryBinding.inflate(layoutInflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }

    private fun inflateAuth()
    {
        ViewProfileAuthBinding.inflate(layoutInflater).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.llProfile.addView(root)
        }
    }
}