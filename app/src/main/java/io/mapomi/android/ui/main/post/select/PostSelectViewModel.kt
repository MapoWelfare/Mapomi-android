package io.mapomi.android.ui.main.post.select

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.model.post.MatchModel
import io.mapomi.android.model.post.PostModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.post.adapter.PostSelectAdapter
import javax.inject.Inject

@HiltViewModel
class PostSelectViewModel @Inject constructor(
    val postModel: PostModel,
    val model: MatchModel
) : BaseViewModel() {

    val adapter = PostSelectAdapter()

    val currentPost get() = postModel.currentPost

    init {
        useFlag(model.acceptVolunteerSuccessFlag){
            navigation.revealHistory()
            uiModel.showToast("매칭이 완료됐어요")
        }
    }

    /*******************************************
     **** 데이터를 요청합니다
     ******************************************/

    fun getRemoteVolunteerList() = model.getVolunteerList(currentPost.value!!.postId)


    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 수락 버튼을 누릅니다
     */
    fun onAccept()
    {
        model.acceptVolunteer(currentPost.value!!.postId,"0")
    }

}