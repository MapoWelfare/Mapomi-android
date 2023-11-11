package io.mapomi.android.ui.main.profile

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.R
import io.mapomi.android.enums.Page
import io.mapomi.android.enums.Type
import io.mapomi.android.model.profile.ProfileModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.profile.adapter.MatchHistoryAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val model : ProfileModel
) : BaseViewModel() {

    val itemEmpty = MutableStateFlow(false)

    val adapter = MatchHistoryAdapter(::onItemClick)

    val type get() = signModel.registerType

    val profile get() = model.profile

    init {
        itemEmpty.value = adapter.itemCount == 0
    }

    fun typeString(type: Type) : String {
        return when(type) {
            Type.DISABLED -> valueModel.getString(R.string.str_disabled)
            Type.COMPANION -> valueModel.getString(R.string.str_companion)
            Type.RELATED -> valueModel.getString(R.string.str_related)
        }
    }

    /*******************************************
     **** 데이터를 요청합니다
     ******************************************/

    fun getRemoteMyProfile() = model.getMyProfile()



    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 요청이력 더보기를 누릅니다
     */
    fun onShowMoreHistory()
    {
        navigation.changePage(Page.PROFILE_HISTORY)
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

    /**
     * 로그아웃을 누릅니다
     */
    fun onLogOut()
    {
        signModel.logout()
        navigation.changePage(Page.ACCOMPANY)
    }
}