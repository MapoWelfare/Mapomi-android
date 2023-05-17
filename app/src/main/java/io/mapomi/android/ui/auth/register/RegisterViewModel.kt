package io.mapomi.android.ui.auth.register

import android.widget.EditText
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val nickname = MutableStateFlow("")
    val phone = MutableStateFlow("")

    val nickNameValid get() = signModel.nickNameValid

    private val regex = Regex("[^0-9]")

    /*******************************************
     **** 입력을 받습니다
     ******************************************/

    fun typeNickname(text : CharSequence)
    {
        nickname.value = text.toString()
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
     * 닉네임 중복확인
     */
    fun checkNickname()
    {
        signModel.checkNicknameValid(nickname.value)
    }

    /**
     * 다음 클릭
     */
    fun onNext()
    {
        connect.finishPage()
    }
}