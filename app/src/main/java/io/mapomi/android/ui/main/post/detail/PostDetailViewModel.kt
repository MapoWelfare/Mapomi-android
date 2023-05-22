package io.mapomi.android.ui.main.post.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject
import android.widget.EditText
import io.mapomi.android.model.post.PostModel
import io.mapomi.android.ui.main.post.adapter.PostDateAdapter
import io.mapomi.android.utils.TimeUtil
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PostDetailViewModel @Inject constructor() : BaseViewModel(
) {
}