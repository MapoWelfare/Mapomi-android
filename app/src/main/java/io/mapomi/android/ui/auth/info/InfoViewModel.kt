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

    val typedNickname = MutableStateFlow("")
    val typedLocation = MutableStateFlow("")
    val typedAge = MutableStateFlow("")
    val typedDisabilityInfo = MutableStateFlow("")
    val nicknameValid get() = signModel.nicknameValidFlag
    val buttonEnabled = MutableStateFlow(false)

    private val regex = Regex("[^0-9]")

    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeNickname(text : CharSequence)
    {
        typedNickname.value = text.toString()
    }

    fun typeLocation(text : CharSequence)
    {
        text.toString().let {
            typedLocation.value = it
            updateButtonEnabled(it,typedAge.value,typedDisabilityInfo.value)
        }
    }

    fun typeAge(text : CharSequence, editText: EditText)
    {
        val filteredText = text.toString().replace(regex,"")
        if (filteredText != text.toString()) {
            editText.setText(filteredText)
            editText.setSelection(filteredText.length)
        }
        typedAge.value = filteredText
        updateButtonEnabled(typedLocation.value,filteredText,typedDisabilityInfo.value)
    }

    fun typeDisabilityInfo(text : CharSequence)
    {
        text.toString().let {
            typedDisabilityInfo.value = it
            updateButtonEnabled(typedLocation.value,typedAge.value,it)
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
        signModel.requestCheckNickname(typedNickname.value)
        updateButtonEnabled(typedLocation.value,typedAge.value,typedDisabilityInfo.value)
    }

    fun onNext()
    {
        signModel.setInfo(
            location = typedLocation.value,
            age = if (typedAge.value.toIntOrNull() == null) -1 else typedAge.value.toInt(),
            disabilityInfo = typedDisabilityInfo.value
        )

        if (registerType.value == Type.RELATED)
        {
            TODO("회원가입 요청, 성공시 메인 화면으로")
            return
        }

        connect.goCertifyPage()

    }

    /*******************************************
     **** 초기화 합니다
     ******************************************/

    fun initStatus()
    {
        signModel.nicknameValidFlag.value = false
        typedNickname.value = ""
        typedLocation.value = ""
        typedAge.value = ""
        typedDisabilityInfo.value = ""
        buttonEnabled.value = false
    }
}