package io.mapomi.android.ui.main.accompany

import android.widget.EditText
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Page
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.post.adapter.PostAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AccompanyViewModel @Inject constructor(
    val soft: SoftKeyModel
) : BaseViewModel() {

    val adapter = PostAdapter(::onItemClick)

    val searchPageOn = MutableStateFlow(false)


    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 검색바를 엽니다
     */
    fun onSearch()
    {
        searchPageOn.value = true
    }

    /**
     * 검색바를 닫습니다
     */
    fun closeSearch()
    {
        soft.hideKeyboard()
        searchPageOn.value = false
    }

    /**
     * 검색합니다
     */
    fun searchText()
    {
        soft.hideKeyboard()
    }

    /**
     * 리스트 아이템을 누릅니다
     */
    private fun onItemClick()
    {
        navigation.changePage(Page.POST_DETAIL)
    }

    /**
     * + 버튼을 누릅니다
     */
    fun onAddPost()
    {
        navigation.changePage(Page.POST_WRITE)
    }


}