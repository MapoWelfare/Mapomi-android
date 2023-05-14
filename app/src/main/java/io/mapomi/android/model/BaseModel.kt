package io.mapomi.android.model

import io.mapomi.android.di.NetStatus
import io.mapomi.android.remote.remotesources.RemoteListener
import io.mapomi.android.remote.retrofit.Remote
import javax.inject.Inject

abstract class BaseModel : RemoteListener {

    @Inject
    lateinit var netStatus: NetStatus

    @Inject
    lateinit var remote : Remote

    @Inject
    lateinit var uiModel: GlobalUiModel
}