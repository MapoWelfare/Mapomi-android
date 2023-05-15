package io.mapomi.android.remote.retrofit

import com.google.gson.Gson
import io.mapomi.android.constants.*
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.remotesources.RemoteListener
import io.mapomi.android.system.LogInfo
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class CallExtent<T : CResponse>(
    private val apiNum : Int,
    private val call : Call<T>,
    private val remoteCallback : RemoteListener,
    val callImpl : CallImpl
) : Call<Result<T>> {
    override fun clone(): Call<Result<T>>
        = CallExtent(apiNum, call, remoteCallback, callImpl)


    override fun execute(): Response<Result<T>> {
        throw java.lang.UnsupportedOperationException("No execute Method for custom call")
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        LogInfo(TAG, "콜을 실행합니다.")

        call.enqueue(object : Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                when(response.code())
                {
                    200 -> response.body()?.let { body ->
                        callback.onResponse(
                            this@CallExtent,
                            Response.success(Result.Success(apiNum,remoteCallback,body))
                        )
                    }

                    400,500 -> {
                        response.errorBody()?.let { errorBody ->
                            val body = try {
                                Gson().fromJson(errorBody.string(), CResponse::class.java)
                            } catch (e:Exception){
                                CResponse(message = errorBody.string())
                            }
                            callback.onResponse(
                                this@CallExtent,
                                Response.success(
                                    Result.ServerError(apiNum,remoteCallback,body.message.toString())
                                )
                            )
                        } ?: kotlin.run {
                            callback.onResponse(
                                this@CallExtent,
                                Response.success(Result.ServerError(apiNum,remoteCallback,response.message()))
                            )
                        }
                    }

                    401 -> {
                        val res = Gson().fromJson<CResponse>(response.errorBody()?.string()?:"",CResponse::class.java)
                        if(callImpl.apiNum == API_REFRESH_TOKEN)
                            callback.onResponse(
                                this@CallExtent,
                                Response.success(
                                    Result.ServerError(apiNum,remoteCallback,res.message?:"")
                                )
                            )
                    }
                    else ->
                        callback.onResponse(
                            this@CallExtent,
                            Response.success(Result.LocalError(apiNum,remoteCallback,null))
                        )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(
                    this@CallExtent,
                    Response.success(Result.LocalError(apiNum,remoteCallback,t))
                )
            }

        })
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    companion object{
        const val TAG = "REMOTE SOURCE DEBUG"
    }
}