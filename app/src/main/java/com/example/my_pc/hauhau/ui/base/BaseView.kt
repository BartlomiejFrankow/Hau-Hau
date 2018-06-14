package com.example.my_pc.hauhau.ui.base

import android.arch.lifecycle.MutableLiveData
import android.content.Context

/**
 * Created by my_pc on 11/06/2018.
 */

class BaseView: MutableLiveData<BaseView.Values>() {

    init {
        value = Values(false, null, false)
    }

    fun setProgressVisible(visible : Boolean){
        value = Values(visible, null, false)
    }

    fun setMessage(message : Message){
        value = Values(false, message, false)
    }

    fun hideKeyboard() {
        value = Values(false, null, true)
    }

    data class Values(val showProgress: Boolean = false, val message : Message? = null, val hideKeyboard: Boolean)

}

interface Message {
    fun getMessage(context: Context) : String?
}