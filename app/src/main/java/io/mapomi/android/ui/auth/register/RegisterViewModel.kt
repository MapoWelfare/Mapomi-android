package io.mapomi.android.ui.auth.register

import android.widget.EditText
import dagger.hilt.android.lifecycle.HiltViewModel
import io.mapomi.android.enums.Type
import io.mapomi.android.model.insets.SoftKeyModel
import io.mapomi.android.ui.auth.AuthConnect
import io.mapomi.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val connect : AuthConnect,
    val soft : SoftKeyModel
) : BaseViewModel() {

    val type get() = signModel.registerType
    val nickname = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val terms = MutableStateFlow(false)

    val nickNameValid get() = signModel.nickNameValid
    val nickNameStatus = MutableStateFlow(INIT)

    private val regex = Regex("[^0-9]")


    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeNickname(text : CharSequence)
    {
        text.toString().let {
            if (it != nickname.value)
            {
                nickname.value = it
                nickNameStatus.value = INIT
            }
        }
    }

    fun typePhone(text: CharSequence, editText: EditText)
    {
        val filteredText = text.toString().replace(regex,"")
        if (filteredText != text.toString()) {
            editText.setText(filteredText)
            editText.setSelection(filteredText.length)
        }
        phone.value = filteredText
    }

    /*******************************************
     **** 버튼을 누릅니다
     ******************************************/

    /**
     * 가입 유형을 변경합니다
     */
    fun changeType(type: Type)
    {
        signModel.changeRegisterType(type)
    }

    /**
     * 닉네임 중복확인
     */
    fun checkNickname()
    {
        signModel.checkNicknameValid(nickname.value)
        invokeBooleanFlow(signModel.nickNameValid,
            {
                nickNameStatus.value = INVALID
            },
            {
                nickNameStatus.value = VALID
            }
        )
    }

    /**
     * 약관 동의 클릭
     */
    fun onCheck()
    {
        terms.value = !terms.value
    }


    /**
     * 완료 클릭
     */
    fun onComplete()
    {
        signModel.requestRegister(phone.value)
        useFlag(signModel.registerSuccessFlag){
            connect.finishPage()
        }
    }

    companion object {
        const val INIT = 0
        const val VALID = 1
        const val INVALID = 2
    }

}