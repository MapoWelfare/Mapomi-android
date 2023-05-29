package io.mapomi.android.model

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import io.mapomi.android.ui.base.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalSystemModel @Inject constructor() {

    private var _activity : BaseActivity<*>? = null

    fun registerActivity(activity: BaseActivity<*>)
    {
        _activity = activity
    }

    fun vibrate()
    {
        val vibrator = _activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(300,100))
    }

}