package io.mapomi.android.system

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.mapomi.android.model.GlobalValueModel

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(this)
        valueModel = GlobalValueModel(this)
    }


    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var valueModel : GlobalValueModel
    }
}