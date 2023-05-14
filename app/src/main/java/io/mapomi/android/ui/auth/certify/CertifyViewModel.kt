package io.mapomi.android.ui.auth.certify

import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CertifyViewModel @Inject constructor(
    val connect: AuthConnect
) : BaseViewModel() {
}