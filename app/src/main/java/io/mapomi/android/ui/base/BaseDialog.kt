package io.mapomi.android.ui.base

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseDialog<T : ViewDataBinding>(context: Context,private val layoutId : Int,private val gravity : Int) : Dialog(context) {
    override fun show() {
        val bind = DataBindingUtil.inflate<T>(layoutInflater, layoutId,null,false)

        initBind(bind)
        setCancelable(true)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(bind.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(gravity)
        super.show()
        val param = window?.attributes
        val dp = Resources.getSystem().displayMetrics.density
        param?.width = (Resources.getSystem().configuration.screenWidthDp*dp - 16*dp).toInt()
        window?.attributes = param
    }

    abstract fun initBind(bind : T)
}