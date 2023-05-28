package io.mapomi.android.ui.main.post.select

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.base.BaseViewModel
import io.mapomi.android.ui.main.post.adapter.PostSelectAdapter
import javax.inject.Inject

@HiltViewModel
class PostSelectViewModel @Inject constructor() : BaseViewModel() {

    val adapter = PostSelectAdapter()

}