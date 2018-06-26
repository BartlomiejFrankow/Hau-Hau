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
import com.example.my_pc.hauhau.commons.TransactionAnim
import com.example.my_pc.hauhau.databinding.FragmentListeninBinding
import com.example.my_pc.hauhau.ui.base.BaseFragment
import com.example.my_pc.hauhau.ui.home.HomeActivity
import com.example.my_pc.hauhau.ui.home.HomeFragment
import com.example.my_pc.hauhau.utils.helpers.CustomToast
import kotlinx.android.synthetic.main.fragment_listenin.*
import java.io.File
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Bartlomie_Frankow on 11/06/2018.
 */

class ListenFragment : BaseFragment<HomeActivity, FragmentListeninBinding, ListenViewModel>(), ListenNavigator {

    override fun provideViewModel(): ListenViewModel = ViewModelProviders.of(this).get(ListenViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_listenin

    val customToast = CustomToast()

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

    @SuppressLint("SetTextI18n")
    override fun updateTexts() {
            tv_sound_level?.text = viewModel.soundDb(1.0).roundToInt().toString() + " " + getString(R.string.dB)
            tv_listening?.visibility = View.VISIBLE
    }

    override fun onXClick() {
        viewModel.stopRecorder()
        customToast.showWhiteToast(getBaseActivity(), R.string.listening_stopped)
        getBaseActivity().replaceFragment(HomeFragment.newInstance(), false, TransactionAnim.FADE_OUT_LONG)
    }

}