package io.mapomi.android.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.mapomi.android.remote.dataclass.local.PostVoice
import java.util.LinkedList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalValueModel @Inject constructor(@ApplicationContext val context: Context) {

    fun getString(resourceId : Int) = context.getString(resourceId)

    private val accompanyVoiceList = LinkedList(
        mutableListOf(
            PostVoice(0,"원클릭 요청","안녕하세요.\n원클릭 요청입니다.", voice = "안녕하세요. 진동이 울리면 녹음을 시작하세요."),
            PostVoice(1,"요청 타이틀", needInput = true, voice = "요청사항을 말해주세요.", delay = 2000),
            PostVoice(2,"출발지", needInput = true, voice = "출발지를 말해주세요.", delay = 2000),
            PostVoice(3,"목적지", needInput = true, voice = "목적지를 말해주세요.", delay = 2000),
            PostVoice(4,"원클릭 요청","감사합니다.\n동행 요청이 완료되었습니다.", voice = "감사합니다. 요청이 완료됐어요.")
        )
    )

    fun getVoiceList(): LinkedList<PostVoice> {
        return LinkedList(accompanyVoiceList)
    }
}