package com.example.my_pc.hauhau.ui.home

import android.media.MediaRecorder
import android.os.Environment
import com.example.my_pc.hauhau.ui.base.BaseViewModel
import java.io.File
import java.util.*

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
            var fileNumber = setRecordedFileNumber()

            var mFileName = Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records"
            mFileName += "/recorded_file_$fileNumber.wav"
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

    private fun getAllFilesFromRecordFolder(): ArrayList<File> {
        val path = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        val files = path.listFiles()
        val recordsList = ArrayList<File>()
        Collections.addAll(recordsList, *files)
        return recordsList
    }

    private fun setRecordedFileNumber(): String {
        var recordsList = getAllFilesFromRecordFolder()
        var fileNumber = ""
        if (recordsList.size == 0) fileNumber = "1"
        else if (recordsList.size == 1) fileNumber = "2"
        else if (recordsList.size == 2) fileNumber = "3"
        else if (recordsList.size == 3) fileNumber = "4"
        else if (recordsList.size == 4) fileNumber = "5"
        return fileNumber
    }

}