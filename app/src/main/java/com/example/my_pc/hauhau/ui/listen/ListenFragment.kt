package com.example.my_pc.hauhau.ui.listen

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.example.my_pc.hauhau.BR
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.databinding.FragmentListeninBinding
import com.example.my_pc.hauhau.ui.base.BaseFragment
import com.example.my_pc.hauhau.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_listenin.*
import java.io.File
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by my_pc on 13/06/2018.
 */

class ListenFragment : BaseFragment<HomeActivity, FragmentListeninBinding, ListenViewModel>(), ListenNavigator{

    override fun provideViewModel(): ListenViewModel = ViewModelProviders.of(this).get(ListenViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_listenin

    var mp =  MediaPlayer()

    companion object {
        fun newInstance(): ListenFragment {
            return ListenFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity().startPostponedEnterTransition()
        viewModel.setNavigator(this)
        viewModel.runnerSetUp()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startRecorder()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopRecorder()
        Toast.makeText(getBaseActivity(), getString(R.string.listening_stopped), Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    override fun updateTvAndSetVoice() {
        val recordsList = recordFilesList()
        if(viewModel.soundDb(1.0).toInt() > 0.0) tv_sound_level?.text = viewModel.soundDb(1.0).roundToInt().toString() + " " + getString(R.string.dB)
        if(viewModel.soundDb(1.0).toInt() > 70) playAudio("", recordsList[Random().nextInt(recordsList.size)].toString())
    }

    private fun recordFilesList(): ArrayList<File> {
        val path = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        val files = path.listFiles()
        val recordsList = ArrayList<File>()
        Collections.addAll(recordsList, *files)
        return recordsList
    }

    fun playAudio(path : String, fileName : String){
        if(mp.isPlaying) return
        mp = MediaPlayer()
        mp.setDataSource(path + File.separator + fileName)
        mp.prepare()
        mp.start()
    }

}