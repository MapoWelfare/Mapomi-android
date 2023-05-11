package io.mapomi.android.remote.remotesources

import io.mapomi.android.remote.dataclass.CResponse

interface RemoteListener {

    fun onConnectionSuccess(api: Int, body : CResponse)

    fun handleError(api: Int, msg : String?, t: Throwable?)
}