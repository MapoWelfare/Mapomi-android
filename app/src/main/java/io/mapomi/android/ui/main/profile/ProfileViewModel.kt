package io.mapomi.android.ui.main.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Page
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel() {

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    fun onMoveMatchPage()
    {
        navigation.changePage(Page.PROFILE_MATCH)
    }
}