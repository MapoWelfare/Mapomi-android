package io.mapomi.android.utils

import android.os.Bundle
import android.speech.RecognitionListener

abstract class STTUtil : RecognitionListener {

    abstract fun onReady()
    abstract fun onError()
    abstract fun onResult(result: Bundle?)

    override fun onReadyForSpeech(p0: Bundle?) {
        onReady()
    }

    override fun onBeginningOfSpeech() {

    }

    override fun onRmsChanged(p0: Float) {

    }

    override fun onBufferReceived(p0: ByteArray?) {

    }

    override fun onEndOfSpeech() {

    }

    override fun onError(p0: Int) {
        onError()
    }

    override fun onResults(p0: Bundle?) {
        onResult(p0)
    }

    override fun onPartialResults(p0: Bundle?) {

    }

    override fun onEvent(p0: Int, p1: Bundle?) {

    }
}