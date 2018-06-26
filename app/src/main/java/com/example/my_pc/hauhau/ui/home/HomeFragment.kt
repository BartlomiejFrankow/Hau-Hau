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
import com.example.my_pc.hauhau.utils.helpers.CustomDialog
import com.example.my_pc.hauhau.utils.helpers.CustomToast
import com.github.fabtransitionactivity.SheetLayout
import com.nightonke.boommenu.BoomButtons.BoomButton
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.OnBoomListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * Created by Bartlomie_Frankow on 11/06/2018.
 */

class HomeFragment : BaseFragment<HomeActivity, FragmentHomeBinding, HomeViewModel>(), HomeNavigator, SheetLayout.OnFabAnimationEndListener {

    override fun provideViewModel(): HomeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    override fun getBindingVariable(): Int = BR.obj
    override fun getLayoutId(): Int = R.layout.fragment_home

    private val customToast = CustomToast()

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
        viewModel.createHauHauFolder()
        setUpBoomButton()
    }

    override fun onListenButtonClick() {
        val recordsList = viewModel.getAllFilesFromRecordFolder()
        if (recordsList.size == 1) { customToast.showWhiteToast(getBaseActivity(), R.string.add_records) }
        else bottom_sheet.expandFab()
    }

    override fun onFabAnimationEnd() {
        getBaseActivity().replaceFragment(ListenFragment.newInstance(), false, TransactionAnim.FADE_OUT_LONG)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 0)
        else return
    }

    private fun normalBoomChild() = HamButton.Builder().normalColorRes(R.color.colorPrimaryDark).normalImageRes(R.drawable.ic_box_empty).normalText(getString(R.string.record_new_file))
    private fun deleteBoomChild() = HamButton.Builder().normalColorRes(R.color.red).normalImageRes(R.drawable.ic_delete).normalText(getString(R.string.delete_files))

    private fun setUpBoomButton() {
        boom.isAutoHide = false
        boom.addBuilder(normalBoomChild().listener { checkPermissions(0) })
        boom.addBuilder(normalBoomChild().listener { checkPermissions(1) })
        boom.addBuilder(normalBoomChild().listener { checkPermissions(2) })
        boom.addBuilder(normalBoomChild().listener { checkPermissions(3) })
        boom.addBuilder(normalBoomChild().listener { checkPermissions(4) })
        boom.addBuilder(deleteBoomChild().listener { deleteFIlesWithInformation() })

        boom.onBoomListener = object : OnBoomListener {
            override fun onBoomDidShow() { checkHowManyRecordedFilesUserHave() }
            override fun onClicked(index: Int, boomButton: BoomButton?) {}
            override fun onBackgroundClick() {}
            override fun onBoomDidHide() {}
            override fun onBoomWillHide() {}
            override fun onBoomWillShow() {}
        }
    }

    private fun deleteFIlesWithInformation() {
        val path = File(Environment.getExternalStorageDirectory().absolutePath + "/HauHau Records")
        customToast.showWhiteToast(getBaseActivity(), R.string.files_deleted)
        FileUtils.deleteDirectory(path)
        fileDeleted()
        viewModel.createHauHauFolder()
    }

    private fun checkHowManyRecordedFilesUserHave() {
        val recordsList = viewModel.getAllFilesFromRecordFolder()
        //First file is file with name "First file.txt"
        recordsList.indices.forEach { if (it > 0) fileRecorded(it - 1) }
    }

    private fun checkPermissions(index: Int) {
        @RequiresApi(Build.VERSION_CODES.M)
        if (Build.VERSION.SDK_INT > 22) {
            if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                if (activity?.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) openRecordDialog(index)
                else requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 1)
            else requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else openRecordDialog(index)
    }

    private fun openRecordDialog(index: Int) {
        val dialog = CustomDialog()
        dialog.showDialog(getBaseActivity(),
                getString(R.string.press_record_icon_to_save_your_voice),
                { viewModel.startRecorder() },
                { viewModel.stopRecorder() },
                { fileRecorded(index) })
    }

    private fun fileRecorded(pos: Int) {
        val boomButton = boom.getBoomButton(pos) ?: return
        boomButton.imageView?.setImageResource(R.drawable.ic_box_full)
        boomButton.textView?.text = getString(R.string.sample_added)
        boomButton.subTextView?.text = getString(R.string.your_dog_likes_it)
        boomButton.isClickable = false
    }

    private fun fileDeleted() {
        for (i in 0 until boom.piecePlaceEnum.pieceNumber() - 1) {
            val boomButton = boom.getBoomButton(i) ?: return
            boomButton.imageView?.setImageResource(R.drawable.ic_box_empty)
            boomButton.textView?.text = getString(R.string.record_new_file)
            boomButton.subTextView?.text = ""
            boomButton.isClickable = true
        }
    }

}