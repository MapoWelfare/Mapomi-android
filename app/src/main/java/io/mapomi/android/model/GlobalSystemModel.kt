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
import io.mapomi.android.system.LogInfo
import io.mapomi.android.ui.base.BaseActivity
import io.mapomi.android.utils.STTUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
    val voiceResponse = MutableStateFlow("")
    val recordDone = MutableStateFlow(false)

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

    private fun initVoiceStatus()
    {
        voiceList = null
        currentPostVoice.value = null
        voiceResponse.value = ""
        recordDone.value = false
    }

    fun initVoiceList(list : LinkedList<PostVoice>)
    {
        initVoiceStatus()
        voiceList = list
        startOneClickFlow()
    }

    private fun startOneClickFlow()
    {
        speakMsg(voiceList!!.poll()!!)
        waitSpeaking({requestRecord(voiceList!!.poll()!!)},4000)
    }

    private fun requestRecord(postVoice : PostVoice)
    {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            voiceResponse.value = ""
            speakMsg(postVoice)
            waitSpeaking(::startRecord, delay = postVoice.delay)
        }

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
        voiceResponse.value = recordResult
        voiceList?.let { queue->
            queue.peek()?.let {
                val front = queue.poll()!!
                if (front.needInput) requestRecord(front)
                else speakMsg(front)
            } ?: run {
                LogInfo(javaClass.name, "녹음 내용 끝")
            }
        }
    }

    private fun destroySTTEngine()
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
        currentPostVoice.value = postVoice
        tts?.speak(postVoice.voice,TextToSpeech.QUEUE_FLUSH, null, "")
        if (postVoice.id==4) waitSpeaking({recordDone.value=true}, delay = 2500)
    }

    private fun waitSpeaking(callback : ()->Unit, delay : Long)
    {
        CoroutineScope(Dispatchers.Main).launch {
            delay(delay)
            callback()
        }
    }

    fun destroyTTSEngine()
    {
        tts?.let {
            it.stop()
            it.shutdown()
        }
    }

    fun stopRecord()
    {
        initVoiceStatus()
        destroyTTSEngine()
        destroySTTEngine()
    }

    private fun showToast(msg : String) {
        Toast.makeText(_activity ,msg, Toast.LENGTH_SHORT).show()
    }

    fun checkPermission() : Boolean {
        val granted = ContextCompat.checkSelfPermission(_activity!!,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (!granted)
            ActivityCompat.requestPermissions(_activity!!, arrayOf(Manifest.permission.RECORD_AUDIO),0)
        return granted
    }

    companion object {
        const val TTS_ENGINE = "com.google.android.tts"
    }

}