package io.mapomi.android.system

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import io.mapomi.android.constants.KAKAO_NATIVE
import io.mapomi.android.constants.getHashKey
import io.mapomi.android.model.GlobalValueModel

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(this)

        initKaKaoSdk()

        valueModel = GlobalValueModel(this)
    }

    private fun initKaKaoSdk()
    {
        KakaoSdk.init(this, KAKAO_NATIVE)
        getHashKey(this)
    }


    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var valueModel : GlobalValueModel
    }
}