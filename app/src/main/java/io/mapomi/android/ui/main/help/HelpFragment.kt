package io.mapomi.android.ui.main.help

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.*
import io.mapomi.android.ui.base.BaseFragment

@AndroidEntryPoint
class HelpFragment : BaseFragment() {

    lateinit var bind : FragmentHelpBinding
    val viewModel by activityViewModels<HelpViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentHelpBinding.inflate(layoutInflater)
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return bind.root
    }

    override fun onFragmentCreated() {
        inflateChild()
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigationOnBackPressed() {
        if (viewModel.searchPageOn.value) viewModel.closeSearch()
    }

    private fun inflateChild()
    {
        inflateAppbar()
        inflateSearchbar()
        inflateOneClick()
        inflateList()
    }

    private fun inflateAppbar()
    {
        DataBindingUtil.inflate<ViewHelpAppbarBinding>(layoutInflater, R.layout.view_help_appbar,null,false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flAppbar.addView(root)
        }
    }

    private fun inflateSearchbar()
    {
        DataBindingUtil.inflate<ViewHelpSearchbarBinding>(layoutInflater,
            R.layout.view_help_searchbar,null,false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flSearchbar.addView(root)
        }
    }

    private fun inflateOneClick()
    {
        DataBindingUtil.inflate<ViewOneClickYellowBinding>(layoutInflater,
            R.layout.view_one_click_yellow,null,false).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.flOneClick.addView(root)
        }
    }

    private fun inflateList()
    {
        DataBindingUtil.inflate<ViewHelpListBinding>(layoutInflater,
            R.layout.view_help_list,null,false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flList.addView(root)
        }
    }
}