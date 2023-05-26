package io.mapomi.android.ui.main.post.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.model.post.PostModel
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    val postModel: PostModel
) : BaseViewModel() {

    val postType get() = postModel.postType

}