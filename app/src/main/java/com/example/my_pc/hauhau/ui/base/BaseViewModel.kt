package com.example.my_pc.hauhau.ui.base

import android.arch.lifecycle.ViewModel

/**
 * Created by my_pc on 11/06/2018.
 */

abstract class BaseViewModel<N: Any>: ViewModel() {
    val baseView = BaseView()
    private lateinit var mNavigator: N

    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }

    fun getNavigator(): N = mNavigator
}