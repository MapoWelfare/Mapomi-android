package io.mapomi.android.ui.main.accompany

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.accompany.adapter.AccompanyAdapter
import javax.inject.Inject

@HiltViewModel
class AccompanyViewModel @Inject constructor(

) : BaseViewModel() {

    val adapter = AccompanyAdapter(::onItemClick)


    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    private fun onItemClick()
    {

    }

    fun onAddPost()
    {

    }
}