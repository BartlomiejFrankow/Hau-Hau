package com.example.my_pc.hauhau.ui.home

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.RequiresApi
import android.view.View
import com.example.my_pc.hauhau.BR
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.commons.TransactionAnim
import com.example.my_pc.hauhau.databinding.FragmentHomeBinding
import com.example.my_pc.hauhau.ui.base.BaseFragment
import com.example.my_pc.hauhau.ui.listen.ListenFragment
import com.example.my_pc.hauhau.utils.helpers.BuilderManager
import com.example.my_pc.hauhau.utils.helpers.CustomDialog
import com.github.fabtransitionactivity.SheetLayout
import com.nightonke.boommenu.BoomButtons.HamButton
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File


/**
 * Created by my_pc on 11/06/2018.
 */

class HomeFragment : BaseFragment<HomeActivity, FragmentHomeBinding, HomeViewModel>(), HomeNavigator, SheetLayout.OnFabAnimationEndListener {

    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_home

//    private val PICKFILE_REQUEST_CODE = 1

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity().startPostponedEnterTransition()
        viewModel.setNavigator(this)

        bottom_sheet.setFab(fab)
        bottom_sheet.setFabAnimationEndListener(this)

        setUpBoomButton()
    }

    override fun onListenButtonClick() {
        bottom_sheet.expandFab()
    }

    override fun onFabAnimationEnd() {
        getBaseActivity().replaceFragment(ListenFragment.newInstance(), true, TransactionAnim.FADE_OUT_LONG)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        else return
    }

    fun setUpBoomButton() {
        val builder = HamButton.Builder()

        boom.isAutoHide = false
        for (i in 0 until boom.piecePlaceEnum.pieceNumber()) {
            builder.normalImageRes(R.drawable.ic_box_empty)
                    .normalColorRes(R.color.colorPrimaryDark)
                    .listener { index -> checkPermissions(index) }

            boom.addBuilder(builder)
        }
    }

    private fun checkPermissions(index: Int) {
        @RequiresApi(Build.VERSION_CODES.M)
        if (Build.VERSION.SDK_INT > 22) {
            if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                if (activity?.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) recordFile(index)
                else requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 1)
            else requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else recordFile(index)
    }

    private fun recordFile(index: Int) {
        //TODO There is a problem with getting applicationContext for recordDialog line 319 for android 5.0.1 (samsung S4) -- NEED TO FIX --
        val folder = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        if (!folder.exists()) folder.mkdirs()

        val dialog = CustomDialog()
        dialog.showDialog(getBaseActivity(),
                getString(R.string.press_record_icon_to_save_your_voice),
                { viewModel.startRecorder() },
                { viewModel.stopRecorder() },
                {fileRecorded(index)})
    }

//    fun chooseRecordedFile(){
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.type = getString(R.string.intent_application_recorded_file)
//        startActivityForResult(intent, PICKFILE_REQUEST_CODE)
//    }

    fun fileRecorded(pos: Int) {
        val boomButton = boom.getBoomButton(pos) ?: return
        boomButton.imageView?.setImageResource(BuilderManager.getImageResource())
        boomButton.textView?.text = "Sample number added"
        boomButton.subTextView?.text = getString(R.string.your_dog_likes_it)
    }

}
