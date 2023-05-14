package io.mapomi.android.ui.auth.certify

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CertifyViewModel @Inject constructor(
    val connect: AuthConnect
) : BaseViewModel() {

    val registerType get() = signModel.registerType

    val isUpload get() = signModel.uploadOnApp
    val imgFileName = MutableStateFlow("")

    private fun limitFileName(name : String) : String
    {
        return if (name.length>=25) name.substring(0,25) + "..." else name
    }

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    fun pickOrDeleteFile()
    {
        if (!isUpload.value)
        {
            uiModel.loadImage {
                imgFileName.value = limitFileName(uiModel.getImgRealName(uri = it))
                signModel.convertUriToMultiPart(it)
            }
        }

        else
        {
            imgFileName.value = ""
            signModel.uploadOnApp.value = false
        }
    }

    fun register()
    {
        signModel.requestRegister()
        useFlag(signModel.registerSuccessFlag)
        {
            uiModel.showToast("회원가입 성공")
            connect.finishPage()
        }
    }
}