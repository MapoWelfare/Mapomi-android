package io.mapomi.android.ui.auth.type

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Type
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TypeViewModel @Inject constructor(
    val connect: AuthConnect
) : BaseViewModel() {

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    fun selectType(type: Type)
    {
        signModel.changeRegisterType(type)
        uiModel.showToast(type.serverName)
        connect.goAuthPage()
    }


}