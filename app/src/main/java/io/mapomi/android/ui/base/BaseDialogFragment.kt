package io.mapomi.android.ui.base

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<T: ViewDataBinding>(private val layoutId : Int) : DialogFragment() {

    protected var _bind : T? = null
    val bind: T
        get() = _bind ?: throw IllegalStateException("[Dialog Fragment] Binding Fail")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = DataBindingUtil.inflate(inflater,layoutId,null,false)
        bind.lifecycleOwner = viewLifecycleOwner
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(bind)
        val param = dialog?.window?.attributes
        val dp = Resources.getSystem().displayMetrics.density
        param?.width = (Resources.getSystem().configuration.screenWidthDp*dp - 16*dp).toInt()
        dialog?.window?.attributes = param
        super.onViewCreated(view, savedInstanceState)

    }

    abstract fun initView(bind : T)
}