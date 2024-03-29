package io.mapomi.android.ui.main.accompany

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.constants.POST_ACCOMPANY
import io.mapomi.android.enums.Page
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.model.post.PostModel
import io.mapomi.android.system.LogDebug
import io.mapomi.android.system.LogInfo
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.post.adapter.PostAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccompanyViewModel @Inject constructor(
    val soft: SoftKeyModel,
    val postModel: PostModel
) : BaseViewModel() {

    val adapter = PostAdapter(::onItemClick)

    val searchPageOn = MutableStateFlow(false)
    val searchKeyword = MutableStateFlow("")

    val type get() = signModel.registerType


    init {
        postModel.posts.onEach { adapter.setPosts(it) }.launchIn(viewModelScope)
    }

    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeKeyword(cs : CharSequence)
    {
        searchKeyword.value = cs.toString()
    }


    /*******************************************
     **** 데이터를 요청합니다
     ******************************************/

    fun requestRemotePostList(refresh : Boolean) = postModel.getRemotePosts(refresh)


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
/*        postModel.searchPosts(searchKeyword.value)*/
        soft.hideKeyboard()
    }

    /**
     * 리스트 아이템을 누릅니다
     */
    private fun onItemClick(id : String)
    {
        postModel.loadPost(id, POST_ACCOMPANY)
        useFlag(postModel.flagLoadSuccess){
            navigation.changePage(Page.POST_DETAIL)
        }
    }

    /**
     * + 버튼을 누릅니다
     */
    fun onAddPost()
    {
        if (!signModel.isLogin.value){
            uiModel.goToLogin()
            return
        }

        postModel.startBuild(POST_ACCOMPANY)
        navigation.changePage(Page.POST_WRITE)
    }

    fun openDialog(manager: FragmentManager)
    {
        uiModel.showCertificationDialog(manager)
    }


}