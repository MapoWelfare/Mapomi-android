package io.mapomi.android.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mapomi.android.model.GlobalUiModel
import io.mapomi.android.model.navigate.Navigation
import io.mapomi.android.system.App
import io.mapomi.android.system.LogDebug
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject lateinit var navigation : Navigation

    @Inject lateinit var uiModel : GlobalUiModel

    val valueModel = App.valueModel

    fun showToast(msg : String)
    {
        uiModel.showToast(msg)
    }

    fun <T> invokeStateFlow(state : StateFlow<T>, collect : (T)->Unit)
    {
        viewModelScope.launch {

            state.collect{
                it?.let {
                    LogDebug(it::class.java.name, "$it")
                }

                collect(it)
            }
        }
    }

    fun invokeBooleanFlow(state: StateFlow<Boolean>, onFalse : ()->Unit, onTrue : ()->Unit){
        invokeStateFlow(state){
            if (it)
                onTrue()
            else
                onFalse()
        }
    }

    fun useFlag(state : MutableStateFlow<Boolean>, onFlag : ()->Unit){
        invokeStateFlow(state){
            onFlag()
            state.value = false
        }
    }

    fun moveBackPage() {
        navigation.revealHistory()
    }
}