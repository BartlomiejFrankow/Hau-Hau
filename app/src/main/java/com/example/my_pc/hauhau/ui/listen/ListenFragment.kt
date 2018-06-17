package com.example.my_pc.hauhau.ui.listen

import android.arch.lifecycle.ViewModelProviders
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.example.my_pc.hauhau.BR
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.databinding.FragmentListeninBinding
import com.example.my_pc.hauhau.ui.base.BaseFragment
import com.example.my_pc.hauhau.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_listenin.*
import kotlin.math.roundToInt


/**
 * Created by my_pc on 13/06/2018.
 */

class ListenFragment : BaseFragment<HomeActivity, FragmentListeninBinding, ListenViewModel>(), ListenNavigator{

    override fun provideViewModel(): ListenViewModel = ViewModelProviders.of(this).get(ListenViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_listenin

    var mRecorder: MediaRecorder? = null
    var runner: Thread? = null
    private var mEMA = 0.0
    private val EMA_FILTER = 0.6

    companion object {
        fun newInstance(): ListenFragment {
            return ListenFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity().startPostponedEnterTransition()
        viewModel.setNavigator(this)

        runnerSetUp()
    }

    val updater: Runnable = Runnable { updateTvAndSetVoice() }
    val mHandler = Handler()

    override fun onResume() {
        super.onResume()
        startRecorder()
    }

    override fun onPause() {
        super.onPause()
        stopRecorder()
    }

    private fun runnerSetUp() {
        if (runner == null) {
            runner = object : Thread() {
                override fun run() {
                    while (runner != null) {
                        try {
                            sleep(500)
                        } catch (e: InterruptedException) {
                        }
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

    fun updateTvAndSetVoice() {
        tv_sound_level?.text = soundDb(1.0).roundToInt().toString() + " dB"
        if (soundDb(1.0) > 75.0) Toast.makeText(getBaseActivity(), "Cisza!", Toast.LENGTH_SHORT).show()
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