package io.mapomi.android.ui.auth.info

import android.widget.EditText
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Type
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    val connect: AuthConnect
) : BaseViewModel() {

    val registerType get() = signModel.registerType

    var typedNickname = ""
    var typedLocation = ""
    var typedAge = ""
    var typedDisabilityInfo = ""
    val nicknameValid get() = signModel.nicknameValidFlag
    val buttonEnabled = MutableStateFlow(false)

    private val regex = Regex("[^0-9]")

    init {
        updateButtonEnabled(typedLocation,typedAge,typedDisabilityInfo)
    }

    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeNickname(text : CharSequence)
    {
        typedNickname = text.toString()
    }

    fun typeLocation(text : CharSequence)
    {
        text.toString().let {
            typedLocation = it
            updateButtonEnabled(it,typedAge,typedDisabilityInfo)
        }
    }

    fun typeAge(text : CharSequence, editText: EditText)
    {
        val filteredText = text.toString().replace(regex,"")
        if (filteredText != text.toString()) {
            editText.setText(filteredText)
            editText.setSelection(filteredText.length)
        }
        typedAge = filteredText
        updateButtonEnabled(typedLocation,filteredText,typedDisabilityInfo)
    }

    fun typeDisabilityInfo(text : CharSequence)
    {
        text.toString().let {
            typedDisabilityInfo = it
            updateButtonEnabled(typedLocation,typedAge,it)
        }
    }

    /**
     * 버튼 활성화 여부를 업데이트합니다
     */
    private fun updateButtonEnabled(location : String, age : String, disabilityInfo : String)
    {
        when(registerType.value)
        {
            Type.DISABLED -> buttonEnabled.value = nicknameValid.value && location.isNotEmpty() && disabilityInfo.isNotEmpty()
            Type.COMPANION -> buttonEnabled.value = nicknameValid.value && location.isNotEmpty() && age.isNotEmpty()
            Type.RELATED -> buttonEnabled.value = nicknameValid.value
        }
    }

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 닉네임 중복확인을 합니다
     */
    fun checkNickname()
    {
        signModel.requestCheckNickname(typedNickname)
        updateButtonEnabled(typedLocation,typedAge,typedDisabilityInfo)
    }

    fun onNext()
    {
        signModel.setInfo(
            location = typedLocation,
            age = if (typedAge.toIntOrNull() == null) -1 else typedAge.toInt(),
            disabilityInfo = typedDisabilityInfo
        )

        if (registerType.value == Type.RELATED)
        {
            TODO("회원가입 요청, 성공시 메인 화면으로")
            return
        }

        connect.goCertifyPage()

    }

}