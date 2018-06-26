package com.example.my_pc.hauhau.utils.helpers

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.widget.TextView
import android.widget.Toast
import com.example.my_pc.hauhau.R


/**
 * Created by Bartlomiej Frankow on 26/06/2018.
 */

class CustomToast {

    fun showWhiteToast(activity: Activity, text: Int) {
        val toast = Toast.makeText(activity, text, Toast.LENGTH_LONG)
        val view = toast.view
        val textView = view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark))
        view.setBackgroundResource(R.drawable.custom_toast)
        view.background.alpha = 215
        toast.show()
    }

}