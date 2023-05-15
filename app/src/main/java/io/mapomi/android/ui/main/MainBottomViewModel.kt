package io.mapomi.android.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Page
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainBottomViewModel @Inject constructor() : BaseViewModel() {

    private val _currentButton = MutableStateFlow(0)
    val currentButton : StateFlow<Int> get() = _currentButton

    init {

        val pages = listOf(
            Page.ACCOMPANY,
            Page.HELP,
            Page.PROFILE
        )

        CoroutineScope(Dispatchers.Main).launch {
            invokeStateFlow(navigation.topPage){
                if (it in pages){
                    if (currentButton.value != pages.indexOf(it)){
                        _currentButton.value = pages.indexOf(it)
                    }
                }
            }
        }
    }

    fun setCurrentButton(button : Int){

        if (button == PAGE_PROFILE){
            if (!signModel.isLogin.value){
                uiModel.goToLogin()
                return
            }
        }

        when(button)
        {
            PAGE_ACCOMPANY -> navigation.changePage(Page.ACCOMPANY)
            PAGE_HELP -> navigation.changePage(Page.HELP)
            PAGE_PROFILE -> navigation.changePage(Page.PROFILE)
        }
    }

    companion object
    {
        const val PAGE_ACCOMPANY = 0
        const val PAGE_HELP = 1
        const val PAGE_PROFILE = 2
    }

}