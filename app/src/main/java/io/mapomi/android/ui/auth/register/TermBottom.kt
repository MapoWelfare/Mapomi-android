package io.mapomi.android.ui.auth.register

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.FragmentComponent
import io.mapomi.android.databinding.BtmRegisterTermBinding
import io.mapomi.android.ui.base.BottomDialog

@AndroidEntryPoint
class TermBottom : BottomDialog() {

    private lateinit var bind : BtmRegisterTermBinding
    private val viewModel : RegisterViewModel by activityViewModels()

    override fun createDialogView(inflater: LayoutInflater, container: ViewGroup?): View {
        bind = BtmRegisterTermBinding.inflate(inflater)
        bind.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return bind.root
    }

    @dagger.Module
    @InstallIn(FragmentComponent::class)
    internal object Module{
        @Provides
        internal fun provideBottomView() : TermBottom{
            return TermBottom()
        }
    }
}