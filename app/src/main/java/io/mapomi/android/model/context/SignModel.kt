package io.mapomi.android.model.context

import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignModel @Inject constructor() : BaseModel(){

    fun requestLogin(id : String, password : String) {

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}