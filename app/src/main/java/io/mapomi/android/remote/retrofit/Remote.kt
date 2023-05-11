package io.mapomi.android.remote.retrofit

import io.mapomi.android.constants.TAG_REMOTE
import io.mapomi.android.di.NetStatus
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.remotesources.RemoteInterface
import io.mapomi.android.system.LogError
import io.mapomi.android.system.LogInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.NotActiveException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Remote @Inject constructor(private val remoteApi : RemoteInterface) : Callback<Result<CResponse>> {

    @Inject
    lateinit var netStatus: NetStatus

    override fun onResponse(call: Call<Result<CResponse>>, response: Response<Result<CResponse>>) {

        netStatus.pendingDown()

        when {
            response.body() is Result.Success -> {
                onResultSuccess(response.body() as Result.Success)
            }

            response.body() is Result.LocalError -> {
                onLocalFailed(response.body() as Result.LocalError)
            }

            response.body() is Result.ServerError -> {
                onRemoteFailed(response.body() as Result.ServerError)
            }
        }
    }

    override fun onFailure(call: Call<Result<CResponse>>, t: Throwable) {
        throw NotActiveException()
    }

    private fun onResultSuccess(result : Result.Success<CResponse>)
    {
        val body = result.body


        LogInfo(TAG_REMOTE,"[RESPONSE] Body를 받았습니다.")
        LogInfo(TAG_REMOTE,"$body")

        result.callback.onConnectionSuccess(result.apiNum,body)
    }

    private fun onRemoteFailed(result : Result.ServerError<CResponse>)
    {
        LogError(TAG_REMOTE,"서버 에러가 발생햇습니다.")
        LogError(TAG_REMOTE,"서버로부터의 메세지 : ")
        LogError(TAG_REMOTE, result.msg)


        result.callback.handleError(result.apiNum, msg = result.msg, t = null)
    }

    private fun onLocalFailed(result : Result.LocalError<CResponse>)
    {
        LogError(TAG_REMOTE,"로컬 클라이언트 에러가 발생했습니다.")
        result.t?.let{
            LogError(it)
        }

        result.callback.handleError(result.apiNum,msg = null, t = result.t)
    }

    fun sendRequestApi(callData : CallImpl) {
        LogInfo(TAG_REMOTE, "통신을 요청합니다.")
        netStatus.pendingUp()
        CallExtent(
            callData.apiNum,
            callData.getCall(remoteApi),
            callData.callback,
            callData
        ).enqueue(this)
    }

}