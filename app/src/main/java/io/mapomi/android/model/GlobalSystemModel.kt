package io.mapomi.android.model

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.mapomi.android.remote.dataclass.local.PostVoice
import io.mapomi.android.system.LogDebug
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.utils.STTUtil
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.LinkedList
import java.util.Locale
import java.util.Queue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalSystemModel @Inject constructor() : STTUtil(), TextToSpeech.OnInitListener {

    private var _activity : BaseActivity<*>? = null


    fun registerActivity(activity: BaseActivity<*>)
    {
        _activity = activity
    }

    /*******************************************
     **** 원클릭 요청 시나리오
     ******************************************/

    private var voiceList : Queue<PostVoice>? = null
    val currentPostVoice = MutableStateFlow<PostVoice?>(null)

    /**
     * 1. 권한 체크를 한다 (음성녹음)
     * 2. 음성 녹음 다이얼로그를 연다
     * 3. 안내 -> 요청 타이틀 -> 진동
     * 4. 타이틀 녹음
     * 5. 안내 -> 출발지 -> 진동
     * 6. 출발지 녹음
     * 7. 안내 -> 목적지 -> 진동
     * 8. 목적지 녹음
     * 9. 녹음이 끝나면 서버로 전송
     * 10. 원클릭 요청 중 뒤로가기 시, 녹음/안내/다이얼로그 종료
     */
    fun initVoiceList(list : LinkedList<PostVoice>)
    {
        voiceList = null
        voiceList = list
        startOneClickFlow()
    }

    private fun startOneClickFlow()
    {
        speakMsg(voiceList!!.poll()!!)
        Handler(Looper.getMainLooper()).postDelayed({
            requestRecord(voiceList!!.poll()!!)
        },4000)
    }

    private fun requestRecord(postVoice : PostVoice)
    {
        currentPostVoice.value = postVoice
        speakMsg(postVoice)
        waitSpeaking(::startRecord,2000)
    }

    /*******************************************
     **** 진동
     ******************************************/

    private fun vibrate()
    {
        val vibrator = _activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(300,100))
    }

    /*******************************************
     **** STT
     ******************************************/

    private var speechRecognizer : SpeechRecognizer? = null

    private fun startRecord()
    {
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, `package`)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN)
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(_activity).apply {
            setRecognitionListener(this@GlobalSystemModel)
            startListening(sttIntent)
        }
    }

    override fun onReady() {
        vibrate()
    }

    override fun onError() {
        LogDebug(javaClass.name, "녹음 에러")
    }

    override fun onResult(result: Bundle?) {
        val recordResult = result?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0]
    }

    fun destroySTTEngine()
    {
        speechRecognizer?.let {
            it.stopListening()
            it.destroy()
        }
    }

    /*******************************************
     **** TTS
     ******************************************/

    private var tts : TextToSpeech? = null

    fun initTTSEngine(context: Context)
    {
        tts = TextToSpeech(context, this, TTS_ENGINE)
    }

    override fun onInit(p0: Int) {

        if (p0 == TextToSpeech.SUCCESS) {

            val result = tts!!.setLanguage(Locale.KOREAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                LogDebug(javaClass.name, "[TTS] 지원 언어 오류")
            }
            else LogDebug(javaClass.name, "[TTS] 성공")
        }
        else {
            LogDebug(javaClass.name, "[TTS] 실패")
        }
    }

    private fun speakMsg(postVoice : PostVoice)
    {
        tts?.speak(postVoice.announce,TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun waitSpeaking(callback : ()->Unit, delay : Long)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            callback()
        },delay)
    }

    fun destroyTTSEngine()
    {
        tts?.let {
            it.stop()
            it.shutdown()
        }
    }

    private fun showToast(msg : String) {
        Toast.makeText(_activity ,msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkPermission() : Boolean {
        val granted = ContextCompat.checkSelfPermission(_activity!!,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (!granted)
            ActivityCompat.requestPermissions(_activity!!, arrayOf(Manifest.permission.RECORD_AUDIO),0)
        return granted
    }

    companion object {
        const val TTS_ENGINE = "com.google.android.tts"
        const val ANNOUNCE_VIBRATE = "진동이 울리면 말해주세요. 녹음을 시작할게요."
        const val ANNOUNCE_TITLE = "요청 사항을 알려주세요."
        const val ANNOUNCE_DEPARTURE = "출발지를 알려주세요."
        const val ANNOUNCE_DESTINATION = "도착지를 알려주세요."

    }

}