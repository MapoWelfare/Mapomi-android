package io.mapomi.android.ui.auth.info

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    val connect: AuthConnect
) : BaseViewModel() {

}