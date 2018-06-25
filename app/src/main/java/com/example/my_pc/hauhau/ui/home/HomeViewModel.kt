package com.example.my_pc.hauhau.ui.home

import android.media.MediaRecorder
import android.os.Environment
import com.example.my_pc.hauhau.ui.base.BaseViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * Created by Bartlomie_Frankow on 11/06/2018.
 */

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    fun onListenButtonClick() {
        getNavigator().onListenButtonClick()
    }

    var mRecorder: MediaRecorder? = null

    fun startRecorder() {
        if (mRecorder == null) {
            val fileNumber = setFileNumber()
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

    private fun setFileNumber(): String {
        val recordsList = getAllFilesFromRecordFolder()
        var fileNumber = ""
        when {
            recordsList.size == 1 -> fileNumber = "1"
            recordsList.size == 2 -> fileNumber = "2"
            recordsList.size == 3 -> fileNumber = "3"
            recordsList.size == 4 -> fileNumber = "4"
            recordsList.size == 5 -> fileNumber = "5"
        }
        return fileNumber
    }

    fun stopRecorder() {
        if (mRecorder != null) {
            mRecorder!!.stop()
            mRecorder!!.release()
            mRecorder = null
        }
    }

    fun getAllFilesFromRecordFolder(): ArrayList<File> {
        val path = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        val files = path.listFiles()
        val recordsList = ArrayList<File>()
        Collections.addAll(recordsList, *files)

        return recordsList
    }

    fun createHauHauFolder() {
        val folder = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        if (!folder.exists()) {
            folder.mkdirs()
            val outputFile = File(folder, "First file.txt")
            val fos = FileOutputStream(outputFile)
            fos.close()
        }
    }

}