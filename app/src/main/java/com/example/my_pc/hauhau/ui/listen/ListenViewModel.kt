package com.example.my_pc.hauhau.ui.listen

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Environment
import android.os.Handler
import com.example.my_pc.hauhau.ui.base.BaseViewModel
import java.io.File
import java.util.*

/**
 * Created by Bartlomie_Frankow on 11/06/2018.
 */

class ListenViewModel : BaseViewModel<ListenNavigator>() {

    private var recorder: MediaRecorder? = null
    var runner: Thread? = null
    private var ema = 0.0
    private val emaFilter = 0.6
    private var mp = MediaPlayer()
    val handler = Handler()
    val updater: Runnable = Runnable { voiceReaction() }

    fun runnerSetUp() {
        if (runner == null) {
            runner = object : Thread() {
                override fun run() {
                    while (runner != null) {
                        try {
                            sleep(100)
                        } catch (e: InterruptedException) { }
                        handler.post(updater)
                    }
                }
            }
            runner?.start()
        }
    }

    fun startRecorder() {
        if (recorder == null) {
            recorder = MediaRecorder()
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder!!.setOutputFile("/dev/null")
            recorder!!.prepare()
            recorder!!.start()
        }
    }

    fun stopRecorder() {
        recorder?.let {
            recorder!!.stop()
            recorder!!.release()
            recorder = null
        }
    }

    fun soundDb(amplitude: Double): Double {
        return 20 * Math.log10(getAmplitudeEMA() / amplitude)
    }

    private fun getAmplitude(): Double {
        return if (recorder != null) recorder!!.maxAmplitude.toDouble()
        else 0.0
    }

    private fun getAmplitudeEMA(): Double {
        val amplitude = getAmplitude()
        ema = emaFilter * amplitude + (1.0 - emaFilter) * ema
        return ema
    }

    @SuppressLint("SetTextI18n")
    private fun voiceReaction() {
        val recordsList = recordFilesList()
        if (soundDb(1.0).toInt() > 0.0) getNavigator().updateTexts()
        if (soundDb(1.0).toInt() > 70) playAudio("", recordsList[Random().nextInt(recordsList.size - 1) + 1].toString())
    }

    private fun recordFilesList(): ArrayList<File> {
        val path = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        val files = path.listFiles()
        val recordsList = ArrayList<File>()
        Collections.addAll(recordsList, *files)

        return recordsList
    }

    private fun playAudio(path: String, fileName: String) {
        if (mp.isPlaying) return
        mp = MediaPlayer()
        mp.setDataSource(path + File.separator + fileName)
        mp.prepare()
        mp.start()
    }

    fun onXClick(){ getNavigator().onXClick() }

}