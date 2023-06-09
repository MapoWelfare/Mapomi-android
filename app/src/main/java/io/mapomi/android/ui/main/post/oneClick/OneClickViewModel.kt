package io.mapomi.android.ui.main.post.oneClick

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.model.GlobalSystemModel
import io.mapomi.android.system.LogInfo
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OneClickViewModel @Inject constructor(
    val connect: OneClickConnect,
    val globalSystemModel: GlobalSystemModel
) : BaseViewModel() {

    val currentPostVoice get() = systemModel.currentPostVoice
    val voiceResponse get() = systemModel.voiceResponse

    fun startRecord()
    {
        systemModel.initVoiceList(list = valueModel.getVoiceList())
    }

    init {
        useFlag(globalSystemModel.recordDone){
            connect.dismissDialog()
        }
    }


}