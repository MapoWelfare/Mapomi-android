package io.mapomi.android.ui.main.post.oneClick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import io.mapomi.android.databinding.BtmOnclickRecordBinding
import io.mapomi.android.ui.base.BottomDialog

@AndroidEntryPoint
class OneClickBottom : BottomDialog() {

    private lateinit var bind : BtmOnclickRecordBinding
    val viewModel : OneClickViewModel by activityViewModels()

    override fun createDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        bind = BtmOnclickRecordBinding.inflate(inflater)
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return bind.root
    }

    override fun onCreateDialog() {
        viewModel.startRecord()
    }


    @dagger.Module
    @InstallIn(FragmentComponent::class)
    internal object Module {
        @Provides
        internal fun provideBottomView() : OneClickBottom {
            return OneClickBottom()
        }
    }
}