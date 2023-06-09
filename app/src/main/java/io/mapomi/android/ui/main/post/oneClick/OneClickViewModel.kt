package io.mapomi.android.ui.main.post.oneClick

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.remote.dataclass.local.PostVoice
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OneClickViewModel @Inject constructor() : BaseViewModel() {

    val currentPostVoice get() = systemModel.currentPostVoice
    val voiceResponse get() = systemModel.voiceResponse

    fun startRecord()
    {
        systemModel.initVoiceList(list = valueModel.accompanyVoiceList)
    }

}