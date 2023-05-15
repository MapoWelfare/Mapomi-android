package io.mapomi.android.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.di.NetStatus
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val netStatus: NetStatus
) : BaseViewModel() {

    private val _showBottomMenu = MutableStateFlow(true)
    val showBottomMenu : StateFlow<Boolean> get() = _showBottomMenu

    fun setBottomMenuVisibility(boolean: Boolean)
    {
        _showBottomMenu.value = boolean
    }

}