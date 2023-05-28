package io.mapomi.android.ui.main.profile.history

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.profile.adapter.MatchHistoryAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileHistoryViewModel @Inject constructor() : BaseViewModel() {

    val itemEmpty = MutableStateFlow(false)

    val adapter = MatchHistoryAdapter(::onItemClick)


    init {
        itemEmpty.value = adapter.itemCount == 0
    }

    /**
     * 요청 내역 아이템을 누룹니다
     */
    fun onItemClick()
    {

    }

}