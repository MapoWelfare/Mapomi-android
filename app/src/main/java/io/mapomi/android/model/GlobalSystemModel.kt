package io.mapomi.android.model

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.mapomi.android.system.LogDebug
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.utils.STTUtil
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalSystemModel @Inject constructor() : STTUtil() {

    private var _activity : BaseActivity<*>? = null

    fun registerActivity(activity: BaseActivity<*>)
    {
        _activity = activity
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(_activity!!,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(_activity!!, arrayOf(Manifest.permission.RECORD_AUDIO),0)
    }

    fun vibrate()
    {
        val vibrator = _activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(300,100))
    }

    fun startRecord()
    {
        checkPermission()

        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, `package`)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN)
        }

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(_activity).apply {
            setRecognitionListener(this@GlobalSystemModel)
            startListening(sttIntent)
        }
    }

    override fun onReady() {
        LogDebug(javaClass.name, "녹음 시작")
    }

    override fun onError() {
        LogDebug(javaClass.name, "녹음 에러")
    }

    override fun onResult(result: Bundle?) {
        val results = result?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0]
        showToast(results)
    }

    private fun showToast(msg : String) {
        Toast.makeText(_activity ,msg, Toast.LENGTH_SHORT).show()
    }

}