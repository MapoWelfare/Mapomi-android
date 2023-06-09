package io.mapomi.android.model.post

import io.mapomi.android.constants.API_ACCEPT_POST_VOLUNTEER
import io.mapomi.android.constants.API_GET_VOLUNTEER_LIST
import io.mapomi.android.constants.API_REQUEST_POST_VOLUNTEER
import io.mapomi.android.model.BaseModel
import io.mapomi.android.remote.dataclass.CResponse
import io.mapomi.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchModel @Inject constructor() : BaseModel() {

    /*******************************************
     **** 매치 신청
     ******************************************/

    val requestMatchSuccessFlag = MutableStateFlow(false)

    fun requestMatch(id : String)
    {
        CallImpl(
            API_REQUEST_POST_VOLUNTEER,
            this,
            paramStr0 = id
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /*******************************************
     **** 신청 리스트
     ******************************************/

    private val _volunteers = MutableStateFlow<String?>(null)
    val volunteer : StateFlow<String?> get() = _volunteers

    fun getVolunteerList(id : String)
    {
        _volunteers.value = null

        CallImpl(
            API_GET_VOLUNTEER_LIST,
            this,
            paramStr0 = id
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    /*******************************************
     **** 신청 수락
     ******************************************/

    val acceptVolunteerSuccessFlag = MutableStateFlow(false)

    fun acceptVolunteer(postId : String, userId : String)
    {
        CallImpl(
            API_ACCEPT_POST_VOLUNTEER,
            this,
            paramStr0 = postId,
            paramStr1 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    /*******************************************
     **** 응답 처리
     ******************************************/



    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}