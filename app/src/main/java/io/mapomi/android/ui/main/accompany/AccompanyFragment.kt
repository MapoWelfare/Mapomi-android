package io.mapomi.android.ui.main.accompany

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.mapomi.android.R
import io.mapomi.android.databinding.FragmentAccompanyBinding
import io.mapomi.android.databinding.ViewAccompanyAppbarBinding
import io.mapomi.android.databinding.ViewAccompanyListBinding
import io.mapomi.android.databinding.ViewAccompanySearchbarBinding
import io.mapomi.android.databinding.ViewOneClickBlueBinding
import io.mapomi.android.ui.base.BaseFragment
import io.mapomi.android.ui.main.post.oneClick.OneClickBottom
import javax.inject.Inject

@AndroidEntryPoint
class AccompanyFragment : BaseFragment() {

    lateinit var bind : FragmentAccompanyBinding
    val viewModel by activityViewModels<AccompanyViewModel>()

    @Inject
    lateinit var recordView : OneClickBottom

    override fun getFragmentRoot(): View {
        bind = FragmentAccompanyBinding.inflate(layoutInflater)
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return bind.root
    }

    override fun onFragmentCreated() {
        inflateChild()
/*        viewModel.requestRemotePostList()*/
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
        DataBindingUtil.inflate<ViewAccompanyAppbarBinding>(layoutInflater, R.layout.view_accompany_appbar,null,false).apply {
            vm = viewModel
            needSearch = true
            fg = this@AccompanyFragment
            lifecycleOwner = viewLifecycleOwner
            bind.flAppbar.addView(root)
        }
    }


    private fun inflateSearchbar()
    {
        DataBindingUtil.inflate<ViewAccompanySearchbarBinding>(layoutInflater,R.layout.view_accompany_searchbar,null,false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flSearchbar.addView(root)
        }
    }

    private fun inflateOneClick()
    {
        DataBindingUtil.inflate<ViewOneClickBlueBinding>(layoutInflater,R.layout.view_one_click_blue,null,false).apply {
            vm = viewModel
            fg = this@AccompanyFragment
            lifecycleOwner = viewLifecycleOwner
            bind.flOneClick.addView(root)
        }
    }

    private fun inflateList()
    {
        DataBindingUtil.inflate<ViewAccompanyListBinding>(layoutInflater,R.layout.view_accompany_list,null,false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flList.addView(root)
        }
    }


    fun showRecordView()
    {
        recordView.show(requireActivity().supportFragmentManager, recordView.tag)
    }
    
    fun showDialog()
    {
        viewModel.openDialog(childFragmentManager)
    }

}