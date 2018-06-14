package com.example.my_pc.hauhau.ui.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by my_pc on 11/06/2018.
 */

abstract class
BaseFragment<out B : BaseActivity<*, *>, out C : ViewDataBinding, V : BaseViewModel<*>> : Fragment(), BaseNavigator {

    private lateinit var viewDataBinding: C
    lateinit var viewModel: V
    private lateinit var mRootView: View
    private lateinit var mActivity: B

    abstract fun provideViewModel(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected fun getBaseActivity(): B = mActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        viewModel = provideViewModel()
        mRootView = viewDataBinding.root
        viewDataBinding.setLifecycleOwner(this)
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
        return mRootView
    }

//    fun addVariable(model: Any?) {
//        viewDataBinding.setVariable(BR.obj, model)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
    }

    private fun handleView() {
        viewModel.baseView.observe(this, android.arch.lifecycle.Observer {
            BaseViewHandler.handleView(it, mActivity)
        })
    }

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as B
    }
}