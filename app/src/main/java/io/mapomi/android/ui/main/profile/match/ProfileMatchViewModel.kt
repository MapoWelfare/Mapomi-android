package io.mapomi.android.ui.main.profile.match

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.PostProgress
import io.mapomi.android.enums.PostProgress.*
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileMatchViewModel @Inject constructor() : BaseViewModel() {

    private val matchProgress = MutableStateFlow(MATCH)
    val progressStatus = MutableStateFlow<List<Int>>(listOf())

    init {
        checkProgressStatus(matchProgress.value)
    }

    private fun checkProgressStatus(progress: PostProgress)
    {
        when(progress)
        {
            READY -> progressStatus.value = listOf(CURRENT,REMAIN,REMAIN,REMAIN)
            MATCH -> progressStatus.value = listOf(PAST,CURRENT,REMAIN,REMAIN)
            PROGRESS -> progressStatus.value = listOf(PAST,PAST,CURRENT,REMAIN)
            DONE -> progressStatus.value = listOf(PAST,PAST,PAST,REMAIN)
        }
    }

    companion object {
        const val CURRENT = 0
        const val PAST = 1
        const val REMAIN = 2
    }
}