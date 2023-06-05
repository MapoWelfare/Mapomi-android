package io.mapomi.android.ui.main.post.oneclick

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OneClickViewModel @Inject constructor() : BaseViewModel() {

    fun startRecord()
    {
        systemModel.startOneClickFlow()
    }

}