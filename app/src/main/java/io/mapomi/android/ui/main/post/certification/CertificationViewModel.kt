package io.mapomi.android.ui.main.post.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CertificationViewModel @Inject constructor() : BaseViewModel() {

    val fileName = MutableStateFlow("")
    val fileUploaded = MutableStateFlow(false)

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    fun onLoadFile(){
        uiModel.isMoveGallery.value = true
        uiModel.loadImage {
            fileName.value = uiModel.getImgRealName(it)
        }
    }
}
