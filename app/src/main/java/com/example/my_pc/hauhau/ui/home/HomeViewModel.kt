package com.example.my_pc.hauhau.ui.home

import android.media.MediaRecorder
import android.os.Environment
import com.example.my_pc.hauhau.ui.base.BaseViewModel

/**
 * Created by my_pc on 11/06/2018.
 */

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    fun onListenButtonClick(){
        getNavigator().onListenButtonClick()
    }

    var mRecorder: MediaRecorder? = null

    fun startRecorder() {
        if (mRecorder == null) {
            var mFileName = Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records"
            mFileName += "/recorded_file_3.wav"
            mRecorder = MediaRecorder()
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mRecorder!!.setOutputFile(mFileName)
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

}