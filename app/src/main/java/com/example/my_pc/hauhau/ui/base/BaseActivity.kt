package com.example.my_pc.hauhau.ui.base

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.commons.TransactionAnim
import com.example.my_pc.hauhau.utils.extensions.snack

/**
 * Created by my_pc on 11/06/2018.
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {

    private lateinit var viewDataBinding: T
    lateinit var viewModel: V
    private lateinit var dialog: AlertDialog

    protected abstract fun provideViewModel(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    internal var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        createProgressDialog()
        performDataBinding()
    }

    private fun performDataBinding() {
        viewModel = provideViewModel()
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setLifecycleOwner(this)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    private fun createProgressDialog() {
        val builder = AlertDialog.Builder(this)
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        builder.setView(progressBar)
        dialog = builder.create()
        dialog.setCancelable(false)
        val window = dialog.window
        if (window != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

//    fun addVariable(model: Any?) {
//        viewDataBinding.setVariable(BR.obj, model)
//    }

    open fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, animation: TransactionAnim? = null, id: Int = R.id.fragmentContainer) {
        currentFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        if (animation != null) {
            transaction.setCustomAnimations(animation.animOpenFragmentWithFadeIn,animation.noAnimOpenFragment, animation.animCloseFragmentWithFadeIn, animation.noAnimCloseFragment)
        }
        if (addToBackStack)
            transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.replace(id, fragment)
        transaction.commit()
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean, animation: TransactionAnim? = null, id: Int = R.id.fragmentContainer) {
        currentFragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        if (animation != null) {
            transaction.setCustomAnimations(animation.animOpenFragmentWithFadeIn,animation.noAnimOpenFragment, animation.animCloseFragmentWithFadeIn, animation.noAnimCloseFragment)
        }
        transaction.add(id, fragment)
        if (addToBackStack)
            transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
        supportFragmentManager.executePendingTransactions()
    }

    fun setAutoBlockUi(boolean: Boolean) {
        if (boolean) {
            dialog.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            dialog.window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    fun showMessage(message: Message) {
        findViewById<View>(android.R.id.content).snack(message.getMessage(this)!!)
    }

    fun showProgress() {
        dialog.show()
    }

    fun hideProgress() {
        dialog.hide()
    }

    fun hideKeyboard() {
        val inputMethodManager = getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun finish() {
        dialog.dismiss()
        super.finish()
    }
}