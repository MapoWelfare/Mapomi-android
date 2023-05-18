package io.mapomi.android.ui.base

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("isSelect")
fun setIsSelect(view: View, status : Boolean){
    view.isSelected = status
}

@BindingAdapter("onActionSearch")
fun onActionSend(view: EditText, listener: View.OnClickListener)
{
    view.setOnEditorActionListener { v, actionId, event ->

        when(actionId){

            EditorInfo.IME_ACTION_SEARCH -> {

                listener.onClick(v)

                val im =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()

                return@setOnEditorActionListener true
            }
        }

        false
    }
}