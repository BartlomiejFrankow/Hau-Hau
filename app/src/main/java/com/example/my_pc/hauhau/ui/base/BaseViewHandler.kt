package com.example.my_pc.hauhau.ui.base

/**
 * Created by my_pc on 11/06/2018.
 */

object BaseViewHandler {

    fun handleView(values: BaseView.Values?, baseActivity: BaseActivity<*, *>){
        if (values != null){
            handleProgress(values, baseActivity)
            handleMessage(values, baseActivity)
            handleKeyboard(values, baseActivity)
        }
    }

    private fun handleMessage(baseView: BaseView.Values, baseActivity: BaseActivity<*, *>) {
        baseView.message?.let { baseActivity.showMessage(it) }
    }

    private fun handleProgress(it: BaseView.Values, baseActivity: BaseActivity<*, *>) {
        if (it.showProgress) {
            baseActivity.showProgress()
        } else {
            baseActivity.hideProgress()
        }
    }

    private fun handleKeyboard(it: BaseView.Values, baseActivity: BaseActivity<*, *>) {
        if (it.hideKeyboard){
            baseActivity.hideKeyboard()
        }
    }
}