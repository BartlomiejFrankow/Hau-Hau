package com.example.my_pc.hauhau.utils.helpers

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.example.my_pc.hauhau.R
import android.graphics.drawable.InsetDrawable
import android.widget.ImageView


/**
 * Created by my_pc on 19/06/2018.
 */
class CustomDialog {

    fun showDialog(activity: Activity, msg: String, fStart: () -> Unit, fStop: () -> Unit) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.my_dialog)
        dialog.setCancelable(false)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val wmlp = dialog.window.attributes
        wmlp.gravity = Gravity.CENTER
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 20)
        dialog.window.setBackgroundDrawable(inset)

        val text = dialog.findViewById(R.id.text_dialog) as TextView
        text.text = msg

        val dialogButton = dialog.findViewById(R.id.btn_dialog) as Button
        dialogButton.setOnClickListener {
            dialog.dismiss()
            fStop()
        }

        val dialogMicImage = dialog.findViewById(R.id.iv_mic) as ImageView
        dialogMicImage.setOnClickListener {
            fStart()

        }

        dialog.show()
    }
}