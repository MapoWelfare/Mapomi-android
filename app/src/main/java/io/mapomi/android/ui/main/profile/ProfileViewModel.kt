package io.mapomi.android.ui.main.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Page
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.profile.adapter.MatchHistoryAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel() {

    val itemEmpty = MutableStateFlow(false)

    val adapter = MatchHistoryAdapter(::onItemClick)


    init {
        itemEmpty.value = adapter.itemCount == 0
    }

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 요청이력 더보기를 누릅니다
     */
    fun onShowMoreHistory()
    {

    }


    /**
     * 요청 내역 페이지로 이동합니다
     */
    fun onMoveMatchPage()
    {
        navigation.changePage(Page.PROFILE_MATCH)
    }

    /**
     * 요청 내역 아이템을 누룹니다
     */
    fun onItemClick()
    {

    }
}