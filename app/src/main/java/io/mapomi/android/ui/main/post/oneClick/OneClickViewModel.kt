package io.mapomi.android.ui.main.post.oneClick

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.remote.dataclass.local.PostVoice
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OneClickViewModel @Inject constructor() : BaseViewModel() {

    val currentMsg = MutableStateFlow("")
    val currentPostVoice get() = systemModel.currentPostVoice

    fun startRecord()
    {
        systemModel.initVoiceList(list = LinkedList(listOf(
            PostVoice(0,"원클릭 요청","안녕하세요.\n원클릭 요청입니다."),
            PostVoice(1,"요청 타이틀",""),
            PostVoice(2,"출발지",""),
            PostVoice(3,"목적지",""),
            PostVoice(4,"원클릭 요청","감사합니다.\n동행 요청이 완료되었습니다."),
                )
            )
        )
    }

}