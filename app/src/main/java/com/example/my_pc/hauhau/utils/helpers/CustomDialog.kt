package com.example.my_pc.hauhau.utils.helpers

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.my_pc.hauhau.R

/**
 * Created by Bartlomie_Frankow on 19/06/2018.
 */

class CustomDialog {

    fun showDialog(activity: Activity, msg: String, startRecording: () -> Unit, stopRecording: () -> Unit, setCheckForItemAtList: () -> Unit) {
        val dialog = Dialog(activity)
        val attribiute = dialog.window.attributes

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.my_dialog)
        dialog.setCancelable(false)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        attribiute.gravity = Gravity.CENTER
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 20)
        dialog.window.setBackgroundDrawable(inset)

        val text = dialog.findViewById(R.id.text_dialog) as TextView
        text.text = msg

        val btnSave = dialog.findViewById(R.id.btn_save) as Button
        btnSave.visibility = View.GONE
        btnSave.setOnClickListener {
            dialog.dismiss()
            stopRecording()
            setCheckForItemAtList()
        }

        val btnCancel = dialog.findViewById(R.id.btn_cancel) as Button
        btnCancel.visibility = View.VISIBLE
        btnCancel.setOnClickListener { dialog.dismiss() }

        val dialogMicImage = dialog.findViewById(R.id.iv_mic) as ImageView
        dialogMicImage.setImageResource(R.drawable.ic_mic)
        dialogMicImage.setOnClickListener {
            btnCancel.visibility = View.GONE
            startRecording()
            dialogMicImage.setImageResource(R.drawable.ic_mic_recording)
            btnSave.visibility = View.VISIBLE
        }

        dialog.show()
    }
}