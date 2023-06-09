package io.mapomi.android.ui.main.post.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Page
import io.mapomi.android.enums.Type.*
import io.mapomi.android.model.post.MatchModel
import io.mapomi.android.model.post.PostModel
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    val postModel: PostModel,
    val model: MatchModel
) : BaseViewModel() {

    val postType get() = postModel.postType
    val post get() = postModel.currentPost
    val type get() = signModel.registerType

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 지원자 목록 페이지로 이동합니다 - 장애인/연관자
     */
    private fun onMoveSelect()
    {
        navigation.changePage(Page.POST_SELECT)
    }

    /**
     * 해당 글에 지원합니다 - 동행자
     */
    private fun onVolunteer()
    {
        model.requestMatch(post.value!!.postId)
        useFlag(model.requestMatchSuccessFlag){
            uiModel.showToast("함께하기 요청을 보냈어요")
        }
    }

    /**
     * 하단 버튼을 누릅니다
     */
    fun onClickButton()
    {
        if (!signModel.isLogin.value){
            uiModel.goToLogin()
            return
        }

        when(type.value){
            DISABLED -> onMoveSelect()
            COMPANION -> onVolunteer()
            RELATED -> onMoveSelect()
        }
    }

}