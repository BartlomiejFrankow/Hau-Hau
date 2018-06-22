package com.example.my_pc.hauhau.ui.listen

import android.media.MediaRecorder
import android.os.Environment
import android.os.Handler
import com.example.my_pc.hauhau.ui.base.BaseViewModel

/**
 * Created by my_pc on 13/06/2018.
 */

class ListenViewModel : BaseViewModel<ListenNavigator>(){

    var mRecorder: MediaRecorder? = null
    var runner: Thread? = null
    private var mEMA = 0.0
    private val EMA_FILTER = 0.6
    val mHandler = Handler()

    val updater: Runnable = Runnable { getNavigator().updateTvAndSetVoice() }

    fun runnerSetUp() {
        if (runner == null) {
            runner = object : Thread() {
                override fun run() {
                    while (runner != null) {
                        try { sleep(100) }
                        catch (e: InterruptedException) { }

                        mHandler.post(updater)
                    }
                }
            }
            runner?.start()
        }
    }

    fun startRecorder() {
        if (mRecorder == null) {
            mRecorder = MediaRecorder()
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mRecorder!!.setOutputFile("/dev/null")
            mRecorder!!.prepare()
            mRecorder!!.start()
        }
    }

    fun stopRecorder() {
        if (mRecorder != null) {
            mRecorder!!.stop()
            mRecorder!!.release()
            mRecorder = null
        }
    }

    fun soundDb(ampl: Double): Double {
        return 20 * Math.log10(getAmplitudeEMA() / ampl)
    }

    fun getAmplitude(): Double {
        return if (mRecorder != null) mRecorder!!.getMaxAmplitude().toDouble()
        else 0.0
    }

    fun getAmplitudeEMA(): Double {
        val amp = getAmplitude()
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA
        return mEMA
    }

}