package io.mapomi.android.ui.auth.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Type
import io.mapomi.android.model.context.SignModel
import io.mapomi.android.system.App.Companion.prefs
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val connect : AuthConnect
) : BaseViewModel() {


    fun login(){
        connect.finishPage()
    }

}