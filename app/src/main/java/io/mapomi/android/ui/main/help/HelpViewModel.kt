package io.mapomi.android.ui.main.help

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.post.adapter.PostAdapter
import javax.inject.Inject

@HiltViewModel
class HelpViewModel @Inject constructor(
    val soft : SoftKeyModel
) : BaseViewModel() {

    val adapter = PostAdapter(::onItemClick)


    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    private fun onItemClick()
    {

    }

    fun onAddPost()
    {

    }

    fun searchText()
    {
        soft.hideKeyboard()
    }
}