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

    fun selectType(type: Type)
    {
        signModel.changeRegisterType(type)
        connect.goAuthPage()
    }


}