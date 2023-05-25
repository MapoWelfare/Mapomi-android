package io.mapomi.android.model.post

import io.mapomi.android.constants.*
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.dataclass.post.Post
import io.mapomi.android.remote.dataclass.request.post.PostBuildRequest
import io.mapomi.android.remote.dataclass.response.post.PostResponse
import io.mapomi.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostModel @Inject constructor() : BaseModel() {

    /*******************************************
     **** BUILD, EDIT
     ******************************************/

    val flagPrepareBuild = MutableStateFlow(false)
    val flagPrepareEdit = MutableStateFlow(false)
    val flagUploadSuccess = MutableStateFlow(false)

    private var editRequest : PostBuildRequest? = null

    val postType = MutableStateFlow(POST_ACCOMPANY)
    private val postMode = MutableStateFlow(POST_BUILD)

    fun changePostType(type : Boolean)
    {
        postType.value = type
    }

    private fun changePostMode(mode : Boolean)
    {
        postMode.value = mode
    }

    fun getRequestForEdit() : PostBuildRequest
    {
        return editRequest!!
    }

    fun startBuild(type : Boolean)
    {
        flagPrepareBuild.value = false
        changePostType(type)
        changePostMode(POST_BUILD)
        flagPrepareBuild.value = true
    }

    fun startEdit(type : Boolean, buildRequest: PostBuildRequest)
    {
        editRequest = buildRequest
        flagPrepareEdit.value = false
        changePostType(type)
        changePostMode(POST_EDIT)
        flagPrepareEdit.value = true
    }

    fun requestUploadPost(buildRequest: PostBuildRequest)
    {
        CallImpl(
            if (postType.value == POST_ACCOMPANY) API_BUILD_ACCOMPANY_POST else API_BUILD_HELP_POST,
            this,
            buildRequest
        ).apply {
            remote.sendRequestApi(this)
        }
        flagUploadSuccess.value = true
    }

    /*******************************************
     **** DELETE
     ******************************************/

    val flagDeleteSuccess = MutableStateFlow(false)

    fun requestDeletePost(id : String)
    {
        CallImpl(
            if (postType.value == POST_ACCOMPANY) API_DELETE_ACCOMPANY_POST else API_DELETE_HELP_POST,
            this,
            paramStr0 = id
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /*******************************************
     **** LIST
     ******************************************/

    private val _posts = MutableStateFlow(mutableListOf<Post>())
    val post : StateFlow<List<Post>> get() = _posts

    private var currentPage = 0
    private var maxPage = 9999
    private var searchKeyword = ""

    fun getRemotePosts(refresh : Boolean = false)
    {
        getRemotePosts(refresh,"")
    }

    fun getRemotePosts(refresh : Boolean = false, search : String = "")
    {
        val pageSize = 10

        val page = if (refresh) {
            _posts.value = mutableListOf()
            maxPage = 9999
            searchKeyword = search
            0
        }
        else currentPage + 1

        if (page > maxPage) return

        CallImpl(
            if (postType.value == POST_ACCOMPANY) API_POST_ACCOMPANY_LIST else API_POST_HELP_LIST,
            this,
            paramInt0 = page,
            paramInt1 = pageSize,
            paramStr0 = searchKeyword
        )
    }

    /*******************************************
     **** DETAIL
     ******************************************/

    val flagLoadSuccess = MutableStateFlow(false)

    fun loadPost(id : String, type : Boolean)
    {
        changePostType(type)
        CallImpl(
            if (postType.value == POST_ACCOMPANY) API_POST_ACCOMPANY_DETAIL else API_POST_HELP_DETAIL,
            this,
            paramStr0 = id
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    /*******************************************
     **** 응답 처리
     ******************************************/

    private fun onPostListResponse(response : PostResponse)
    {
        response.pageable?.pageNumber?.let {
            currentPage = it
        }

        response.totalPages?.let {
            maxPage = it
        }

        response.content?.let { postList ->
            val list = mutableListOf<Post>()
            list.addAll(_posts.value)
            list.addAll(postList)
            _posts.value = list.toMutableList()
        }
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {

        when(api)
        {

            API_POST_ACCOMPANY_LIST,
            API_POST_HELP_LIST-> {
                onPostListResponse(body as PostResponse)
            }

        }

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}